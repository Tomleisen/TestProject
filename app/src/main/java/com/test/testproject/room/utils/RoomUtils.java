package com.test.testproject.room.utils;

import android.content.Context;
import android.text.TextUtils;

import com.test.testproject.main.module.bean.ListBean;
import com.test.testproject.room.asynctask.QueryAsyncTask;
import com.test.testproject.room.base.MyDataBase;

/**
 * Created by TomLeisen on 2022/7/25 7:47 下午
 * Email: xy162162a@163.com
 * Description:数据库工具类，数据库操作必须运行在子线程。
 */
public class RoomUtils {


    public static void queryData(Context context, QueryAsyncTask.QueryCallback callback) {
        queryData(context, "", callback);
    }

    /**
     * 查询
     *
     * @param context  上下文
     * @param text     查询条件
     * @param callback 回调
     */
    public static void queryData(Context context, String text, QueryAsyncTask.QueryCallback callback) {
        //判断cursor是否为空
        QueryAsyncTask task = new QueryAsyncTask(context, callback);
        if (!TextUtils.isEmpty(text)) {
            task.execute(text);
        } else {
            task.execute();
        }
    }

    /**
     * 新增数据/插入数据
     */
    public static void insertData(Context context, ListBean listBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyDataBase.getInstance(context).getListBeanDao().insertData(listBean);
            }
        }).start();
    }

    /**
     * 更新数据
     */
    public static void updateData(Context context, ListBean listBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyDataBase.getInstance(context).getListBeanDao().updateData(listBean);
            }
        }).start();
    }

    /**
     * 删除数据
     */
    public static void deleteData(Context context, ListBean listBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyDataBase.getInstance(context).getListBeanDao().deleteData(listBean);
            }
        }).start();
    }

    /**
     * 删除所有数据
     */
    public static void deleteAllData(Context context) {
        //数据库必须运行在子线程中，所以新开子线程做数据库操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyDataBase.getInstance(context).getListBeanDao().deleteAllLisData();
            }
        }).start();
    }

} 

