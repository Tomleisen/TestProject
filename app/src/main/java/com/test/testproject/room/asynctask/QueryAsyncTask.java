package com.test.testproject.room.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.test.testproject.main.module.bean.ListBean;
import com.test.testproject.room.base.MyDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TomLeisen on 2022/7/25 7:42 下午
 * Email: xy162162a@163.com
 * Description:异步查询数据
 */
public class QueryAsyncTask extends AsyncTask<String, Void, List<ListBean>> {

    private Context context;
    private QueryCallback mCallback;

    public interface QueryCallback {
        void getQueryCallback(List<ListBean> listBeanList);
    }

    public QueryAsyncTask(Context context) {
        this.context = context;
    }

    public QueryAsyncTask(Context context, QueryCallback mCallback) {
        this.context = context;
        this.mCallback = mCallback;
    }

    @Override
    protected List<ListBean> doInBackground(String... arg0) {
        try {
            MyDataBase myDataBase = MyDataBase.getInstance(context);
            if (arg0.length == 0) {
                //查询所有数据
                return myDataBase.getListBeanDao().getAllDataList() == null ? new ArrayList<>() : myDataBase.getListBeanDao().getAllDataList();
            } else {
                //根据搜索条件查询：title,作者，描述
                return myDataBase.getListBeanDao().getDataByFuzzy(arg0[0])== null ? new ArrayList<>() : myDataBase.getListBeanDao().getDataByFuzzy(arg0[0]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<ListBean> result) {
        super.onPostExecute(result);
        if (result != null) {
            mCallback.getQueryCallback(result);
        }
    }
}
