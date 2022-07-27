package com.test.testproject.main.view.asynctask;

import androidx.recyclerview.widget.RecyclerView;

import com.test.testproject.main.module.bean.ListBean;
import com.test.testproject.main.module.bean.RatingBean;
import com.test.testproject.room.utils.RoomUtils;
import com.tom.commonframework.common.base.ICommonView;
import com.tom.commonframework.common.base.utils.JsonUtils;

/**
 * Created by TomLeisen on 2022/7/25 10:00 上午
 * Email: xy162162a@163.com
 * Description:异步下载工具类
 */
public class DownLoadTaskUtils {

    //异步下载应用星级和评论数
    public static void DownLoadTasks(RecyclerView.Adapter adapter,ICommonView mICommonView, String id, ListBean entryBean) {
        DownLoadTasks task = new DownLoadTasks(mICommonView, id, new DownLoadTasks.DownLoadCallback() {
            @Override
            public void getNeededBreads(Object o, int positions) {
                RatingBean bean = JsonUtils.parseJson(o.toString(), RatingBean.class);
                if (bean != null) {
                    if (entryBean != null) {
                        if (bean.getResults() != null && bean.getResults().size() > 0) {
                            entryBean.setRating(bean.getResults().get(0).getRating());
                            entryBean.setCommentsNum(bean.getResults().get(0).getCommentsNum());
//                            adapter.notifyItemChanged(positions, entryBean);
                            adapter.notifyDataSetChanged();
                            //更新数据库数据
                            RoomUtils.updateData(mICommonView.getContext(),entryBean);
                        }
                    }
                }
            }
        });
        task.execute();
    }

    //异步下载应用星级和评论数
    public static void DownLoadTasks(RecyclerView.Adapter adapter,ICommonView mICommonView, String id, int position, ListBean entryBean) {
        DownLoadTasks task = new DownLoadTasks(mICommonView, id, position, new DownLoadTasks.DownLoadCallback() {
            @Override
            public void getNeededBreads(Object o, int positions) {
                RatingBean bean = JsonUtils.parseJson(o.toString(), RatingBean.class);
                if (bean != null) {
                    if (entryBean != null) {
                        if (bean.getResults() != null && bean.getResults().size() > 0) {
                            entryBean.setRating(bean.getResults().get(0).getRating());
                            entryBean.setCommentsNum(bean.getResults().get(0).getCommentsNum());
//                            adapter.notifyItemChanged(positions, entryBean);
                            adapter.notifyDataSetChanged();
                            //更新数据库数据
                            RoomUtils.updateData(mICommonView.getContext(),entryBean);
                        }
                    }
                }
            }
        });
        task.execute();
    }

} 

