package com.tom.commonframework.common.net;

import com.tom.commonframework.common.http.constant.Constants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by TomLeisen on 2018/7/9 上午10:03
 * Email: xy162162a@163.com
 * Description: RequestBody转换拦截器
 */
public class ConversionRequestBody {

    private static String TAG = ConversionRequestBody.class.getName() + "TAG~~~~";
    private static ConversionRequestBody INSTANCE;

    public ConversionRequestBody() {
    }

    public static ConversionRequestBody getInstance() {
        if (INSTANCE == null) {
            synchronized (ConversionRequestBody.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ConversionRequestBody();
                }
            }
        }
        return INSTANCE;
    }


    public RequestBody body(Map<String, Object> map) {
        RequestBody requestBody = null;
        try {
            Map<String, Object> snParams = new HashMap<>();
            Map<String, Object> params = new HashMap<>();
            // 遍历键值对对象的集合，得到每一个键值对对象
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.put(entry.getKey(), entry.getValue());
            }
            JSONObject object = new JSONObject(params);
            requestBody = RequestBody.create(MediaType.parse(Constants.Http.ContentType.CONTENT_TYPE_JSON), object.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return requestBody;
    }
}
