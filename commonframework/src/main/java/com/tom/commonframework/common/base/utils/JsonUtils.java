package com.tom.commonframework.common.base.utils;

import android.text.TextUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class JsonUtils {

    public static <T> T parseJson(String jsonData, Class<T> clazz) {
        T obj = null;
        try {
            if (!TextUtils.isEmpty(jsonData) || clazz != null) {
                Gson gson = new Gson();
                obj = gson.fromJson(jsonData, clazz);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static <T> T parseJson(String jsonData, Class<T> clazz, ExclusionStrategy strategy) {
        T obj = null;
        try {
            if (!TextUtils.isEmpty(jsonData) || clazz != null) {
                Gson gson = new GsonBuilder()
                        .setExclusionStrategies(strategy)
                        .serializeNulls()
                        .create();
                obj = gson.fromJson(jsonData, clazz);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static <T> T parseJsonList(String jsonData, Type type)
            throws JsonSyntaxException {
        T tList = null;
        try {
            Gson gson = new Gson();
            tList = gson.fromJson(jsonData, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return tList;
    }

    public static <T> T parseJsonList(String jsonData, Type type, Class<T> clazz)
            throws JsonSyntaxException {
        T tList = null;
        try {
            Gson gson = new Gson();
            tList = gson.fromJson(jsonData, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return tList;
    }

    public static String toJson(Object obj) {
        String json = null;
        if (obj != null) {
            Gson gson = new Gson();
            json = gson.toJson(obj);
        }
        return json;
    }

    public static String toJsonNamingPolicy(Object obj) {
        String json = null;
        if (obj != null) {
            // 将java对象的属性转换成指定的json名字
            Gson gson = new GsonBuilder().setFieldNamingPolicy(
                    FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            json = gson.toJson(obj);
        }

        return json;
    }

    public static String getValueByName(String json, String name)
            throws JSONException {
        String result = "";
        if (!TextUtils.isEmpty(json) && !TextUtils.isEmpty(name)) {
            JSONObject jObj = new JSONObject(json);
            if (jObj.has(name)) {
                result = jObj.optString(name);
            }
        }
        return result;
    }

}
