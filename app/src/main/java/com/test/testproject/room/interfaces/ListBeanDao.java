package com.test.testproject.room.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.test.testproject.main.module.bean.ListBean;

import java.util.List;

/**
 * Created by TomLeisen on 2022/7/26 2:25 上午
 * Email: xy162162a@163.com
 * Description:
 */
@Dao
public interface ListBeanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(ListBean listBean);

    @Delete
    void deleteData(ListBean listBean);

    @Update
    void updateData(ListBean listBean);

    //查询所有数据
    @Query("select * from listbean")
    List<ListBean> getAllDataList();

    //根据单个字段做模糊查询
    @Query("select * from listbean where id = :id")
    ListBean getDataById(String id);

    //根据单个字段做模糊查询
    @Query("select * from listbean where title like '%' || :strName || '%'")
    List<ListBean> loadAllLike(String strName);//加一个占位符strName 表示%last_name%

    //根据多个字段做模糊查询：title,作者，描述
    @Query("select * from listbean where '%' || title || '%''%' || summary || '%''%' || artist || '%' LIKE '%' || :strName || '%'")
    List<ListBean> getDataByFuzzy(String strName);//加一个占位符strName 表示%last_name%

    //删除所有数据
    @Query("DELETE FROM listbean")
    void deleteAllLisData();

} 

