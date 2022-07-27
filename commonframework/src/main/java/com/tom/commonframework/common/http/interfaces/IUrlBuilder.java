package com.tom.commonframework.common.http.interfaces;


import com.tom.commonframework.common.http.constant.Constants;

/***
 *  Created by TomLeisen  2016/5/5 0005 10:11
 *  Email: xy162162a@163.com
 *  Description: URL 组装器
 */
public interface IUrlBuilder {
    /**
     * 组装目标请求url
     *
     * @param url  目标url
     * @param type 网络请求类型
     * @return 组装后url
     */
    String getUrl(String url, Constants.REQUEST_TYPE type);

    /**
     * 获取端口
     */
    int getPort();

    /**
     * 获取主机地址
     */
    String getHost();

}
