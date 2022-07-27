package com.test.testproject.room.base;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.test.testproject.main.module.bean.ListBean;
import com.test.testproject.room.interfaces.ListBeanDao;

/**
 * Created by TomLeisen on 2022/7/25 11:06 上午
 * Email: xy162162a@163.com
 * Description:数据库关联之前的表和增删改查数据信息
 */
@Database(entities = {ListBean.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {
    //数据库名称
    private static final String DATABASE_NAME = "test_project_db";

    //增删改查操作
    public abstract ListBeanDao getListBeanDao();

    private static MyDataBase instance;

    public static synchronized MyDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), MyDataBase.class, DATABASE_NAME)
                    .build();
        }
        return instance;
    }

}



