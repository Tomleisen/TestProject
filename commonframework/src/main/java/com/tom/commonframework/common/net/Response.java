package com.tom.commonframework.common.net;


import com.google.gson.annotations.SerializedName;
import com.lkn.net.response.IResponse;

import org.json.JSONObject;

/**
 * Created by TomLeisen on 2017/7/25.
 */

public class Response<T> implements IResponse<T> {

    private String CODE = "code";
    private String MESSAGE = "message";
    private String DATA = "data";


    @SerializedName("message")
    public String msg = "系统繁忙,请稍后重试!";
    public int code = 10000;
    private T data;


    public T getContent() {
        return data;
    }

    public void setContent(T data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return code == 10000;
    }

    public String getMessage() {
        return msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    @Override
    @SuppressWarnings("unchecked")
    public Response<T> parserObject(String json) {
        try {
            JSONObject root = new JSONObject(json);
            String codeStr = root.optString(CODE);
            this.msg = root.optString(MESSAGE);
            this.code = Integer.valueOf(codeStr);
            this.data = (T) root.opt(DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public Response<T> parserObject2JsonString(String json) {
        try {
            JSONObject root = new JSONObject(json);
            this.data = (T) root;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Response<T> parserObject2String(String json) {
        try {
            JSONObject root = new JSONObject(json);
            String codeStr = root.optString(CODE);
            this.msg = root.optString(MESSAGE);
            this.code = Integer.valueOf(codeStr);
            if (root.opt(DATA) instanceof String) {
                this.data = (T) root.opt(DATA);
            } else {
                if (root.opt(DATA) == null) {
                    this.data = (T) "";
                } else {
                    this.data = (T) String.valueOf(root.opt(DATA));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Response<T> parserObject2Integer(String jsonString) {
        try {
            JSONObject root = new JSONObject(jsonString);
            String codeStr = root.optString(CODE);
            this.msg = root.optString(MESSAGE);
            this.code = Integer.valueOf(codeStr);
            if ((root.opt(DATA) instanceof Integer)) {
                this.data = (T) root.opt(DATA);
            }
        } catch (Exception e) {
            e.printStackTrace();


        }
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Response<T> parserObject2Boolean(String jsonString) {
        try {
            JSONObject root = new JSONObject(jsonString);
            String codeStr = root.optString(CODE);
            this.msg = root.optString(MESSAGE);
            this.code = Integer.valueOf(codeStr);
            if ((root.opt(DATA) instanceof Boolean)) {
                this.data = (T) root.opt(DATA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Response<T> parserObject2Double(String jsonString) {
        try {
            JSONObject root = new JSONObject(jsonString);
            String codeStr = root.optString(CODE);
            this.msg = root.optString(MESSAGE);
            this.code = Integer.valueOf(codeStr);
            if ((root.opt(DATA) instanceof Double)) {
                this.data = (T) root.opt(DATA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Response<T> parserObject2Long(String jsonString) {
        try {
            JSONObject root = new JSONObject(jsonString);
            String codeStr = root.optString(CODE);
            this.msg = root.optString(MESSAGE);
            this.code = Integer.valueOf(codeStr);
            if ((root.opt(DATA) instanceof Long)) {
                this.data = (T) root.opt(DATA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
