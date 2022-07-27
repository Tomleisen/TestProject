package com.tom.commonframework.common.http.constant;

/**
 * Created by Tomleisen on 2016/9/18 0018.
 */
public class Constants {

    public static String HOST_HTTP;
    public static String HOST_HTTPS;
    public static String HOST_WAP;
    public static int PORT;//端口
    public static String HOST;//主机地址

    public static String HOST_HTTP_KEY = "HOST_HTTP_KEY";
    public static String HOST_HTTPS_KEY = "HOST_HTTPS_KEY";
    public static String HOST_WAP_KEY = "HOST_WAP_KEY";


    /**
     * 请求类型
     */
    public enum REQUEST_TYPE{
        HTTP,HTTPS
    }

    public static final class Http {

        public static final class ContentType {
            public static final String CONTENT_TYPE_JSON = "application/json";
            public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
            public static final String[] CONTENT_TYPE_MULTI_MEDIA = {
                    "application/pdf", "image/png", ",image/jpg", "image/gif",
                    "image/jpeg", "image/bmp", "image/ico", "image/x-png",
                    "image/pjpeg", "audio/mpeg", "audio/x-wav", "audio/wav",
                    "audio/mp3", "application/x-mpegurl", "video/mp2t"};
        }

        public static final class Environment {
            public static final class Command {
                public static final int TEST_ENV = 0;//测试环境
                public static final int DEV_HOST = 1;//开发环境
                public static final int NORMAL_ENV = 2;//正式环境
                public static final int PREPARED_ENV = 3;//预发布环境
            }
        }
    }

}
