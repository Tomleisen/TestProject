package com.tom.commonframework.common.http;

import com.tom.commonframework.common.http.interfaces.IUrlBuilder;
import com.tom.commonframework.common.http.constant.Constants;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Tomleisen on 2018/1/19 0007 14:30
 * Email: xy162162a@163.com
 * Description:网络请求前组装URL
 */
public class UrlBuilder implements IUrlBuilder {

    private Map<String, String> hostMap = new HashMap<>();

    public UrlBuilder() {
        initUrl();
    }

    /**
     * 根据环境和请求类型拼接对应的 url
     */
    @Override
    public String getUrl(String oldUri, Constants.REQUEST_TYPE requestType) {
        StringBuilder url = new StringBuilder();
        initUrl();//根据当前环境构建域名

        // 拼接请求协议
        switch (requestType) {
            case HTTP:
                url.append(Constants.HOST_HTTP);
                break;
            case HTTPS:
                url.append(Constants.HOST_HTTPS);
                break;
            default:
                break;
        }
        url.append(oldUri);//拼接目标地址

        return url.toString();
    }

    /**
     * 根据环境返回对应的 端口
     */
    @Override
    public int getPort() {
        initUrl();//根据当前环境构建域名
        return Constants.PORT;
    }

    /**
     * 根据环境返回对应的 主机地址
     */
    @Override
    public String getHost() {
        initUrl();//根据当前环境构建域名
        return Constants.HOST;
    }

    /**
     * 根据当前环境构建域名
     */
    public void initUrl() {
        switch (LocalInfoControlCenter.INSTANCES.getEnv()) {
            // 正式环境
            case Constants.Http.Environment.Command.NORMAL_ENV:
                Constants.HOST_HTTP = "https://itunes.apple.com/hk/";
                Constants.HOST_HTTPS = "";
                Constants.HOST_WAP = "";
                Constants.HOST = "";
                Constants.PORT = 8889;
                break;
            // 开发环境
            case Constants.Http.Environment.Command.DEV_HOST:
                Constants.HOST_HTTP = "https://itunes.apple.com/hk/";
                Constants.HOST_HTTPS = "";
                Constants.HOST_WAP = "";
                Constants.HOST = "";
                Constants.PORT = 8888;
                 break;
            default:
                break;
        }

        hostMap.put(Constants.HOST_HTTP_KEY, Constants.HOST_HTTP);
        hostMap.put(Constants.HOST_HTTPS_KEY, Constants.HOST_HTTPS);
        hostMap.put(Constants.HOST_WAP_KEY, Constants.HOST_WAP);

    }


}
