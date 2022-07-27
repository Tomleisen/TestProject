package com.tom.commonframework.common.net;

import com.tom.commonframework.common.http.LocalInfoControlCenter;
import com.tom.commonframework.common.http.constant.Constants;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by TomLeisen on 2019-07-12 10:08
 * Email: xy162162a@163.com
 * Description: 日志拦截
 */
public final class HttpLogInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    //正式环境,预发布环境,测试环境 需加日志文件
    //正式环境 日志文件不加请求参数
    private int EVN = LocalInfoControlCenter.INSTANCES.getEnv();
    private int RELEASE = Constants.Http.Environment.Command.NORMAL_ENV;//正式环境
    private int PRE = Constants.Http.Environment.Command.PREPARED_ENV;//预发布环境
    private int TEST = Constants.Http.Environment.Command.TEST_ENV;//测试环境
    private int DEBUG = Constants.Http.Environment.Command.DEV_HOST;//开发环境

    public enum Level {
        /**
         * 不记录
         */
        NONE,
        /**
         * 请求/响应行
         */
        BASIC,
        /**
         * 请求/响应行 + 头
         */
        HEADERS,
        /**
         * 请求/响应行 + 头 + 体
         */
        BODY
    }


    public interface Logger {
        void log(String message);

        HttpLogInterceptor.Logger DEFAULT = new HttpLogInterceptor.Logger() {
            @Override
            public void log(String message) {
            }
        };
    }

    public HttpLogInterceptor() {
        this(HttpLogInterceptor.Logger.DEFAULT);
    }

    public HttpLogInterceptor(HttpLogInterceptor.Logger logger) {
        this.logger = logger;
    }

    private final HttpLogInterceptor.Logger logger;

    private volatile HttpLogInterceptor.Level level = HttpLogInterceptor.Level.NONE;

    public HttpLogInterceptor setLevel(HttpLogInterceptor.Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public HttpLogInterceptor.Level getLevel() {
        return level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpLogInterceptor.Level level = this.level;

        Request request = chain.request();
        if (level == HttpLogInterceptor.Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == HttpLogInterceptor.Level.BODY;
        boolean logHeaders = logBody || level == HttpLogInterceptor.Level.HEADERS;
        boolean isWriteRequest = EVN == PRE || EVN == TEST;
        boolean isWriteResults = EVN != DEBUG;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        //请求地址
        String requestStartMessage = request.method() + ' ' + request.url() + ' ';
        String requestStr = "请求地址:  " + requestStartMessage;
        if (isWriteResults) {
            logger.log(requestStr);
        }

        if (logHeaders) {

            if (!logBody || !hasRequestBody) {
                logger.log("--> END " + request.method());
            } else if (bodyEncoded(request.headers())) {
                logger.log("--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (isPlaintext(buffer)) {

                    //请求参数
                    String readString = "   请求参数: " + buffer.readString(charset) + "\n";
                    if (isWriteRequest) {
                        logger.log(readString);
                    }

                } else {
                    logger.log("--> END " + request.method() + " (binary " + requestBody.contentLength() + "-byte body omitted)");
                }
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            logger.log("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();

        //回调地址
        String responseStr = "回调地址: " + response.code() + ' ' + response.message() + ' ' + response.request().url() + " (" + tookMs + "ms)";
        if (isWriteResults) {
            logger.log(responseStr);
        }

        if (logHeaders) {

            if (!logBody || !HttpHeaders.hasBody(response)) {
                logger.log("<-- END HTTP");
            } else if (bodyEncoded(response.headers())) {
                logger.log("<-- END HTTP (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        logger.log("");
                        logger.log("Couldn't decode the response body; charset is likely malformed.");
                        logger.log("<-- END HTTP");

                        return response;
                    }
                }

                if (!isPlaintext(buffer)) {
                    logger.log("");
                    logger.log("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    return response;
                }

                if (contentLength != 0) {
                    //回调参数
                    String readString = "  回调参数: " + buffer.clone().readString(charset) + "\n\n\n\n\n";
                    if (isWriteResults) {
                        logger.log(readString);
                    }
                }
            }
        }

        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}

