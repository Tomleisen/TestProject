package com.test.testproject.main.presenter;

import android.util.Log;

import com.lkn.net.transformer.LifecycleTransformer;
import com.test.testproject.main.module.MainModule;
import com.test.testproject.main.module.bean.ListBean;
import com.test.testproject.main.module.bean.RecommendBean;
import com.test.testproject.main.view.adapter.MainAdapter;
import com.test.testproject.main.view.interfaces.IMainView;
import com.test.testproject.room.utils.RoomUtils;
import com.tom.commonframework.common.base.BasePresenter;
import com.tom.commonframework.common.base.utils.JsonUtils;
import com.tom.commonframework.common.net.rx.HideLoadingSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TomLeisen on 2022/7/23 12:56 上午
 * Email: xy162162a@163.com
 * Description:
 */
public class MainPresenter extends BasePresenter<IMainView> implements MainAdapter.OnPreLoadListener {

    //枚举 Gross:推荐  Free:APP列表
    private final int TYPE_GROSS = 1;
    private final int TYPE_FREE = 2;
    //请求数据条数
    private final int REQUEST_DATA_NUM = 10;
    //是否最后一页
    private boolean isLastPage;
    //页码--默认为第一页
    private int pageNum = 1;

    @Override
    public void onAttach() {
        getTopGrossingapplications(REQUEST_DATA_NUM);
    }

    /**
     * 推荐App获取数据
     */
    public void getTopGrossingapplications(int num) {
        MainModule.getTopGrossingapplications(num)
                .compose(new LifecycleTransformer<>(view))
                .subscribe(new HideLoadingSubscriber<Object>(view) {
                    @Override
                    public void onNext(Object o) {
                        getTopFreeapplications(REQUEST_DATA_NUM);
                        onSuccess(o, TYPE_GROSS);
                    }

                    @Override
                    public void onError(int code, String message) {
                        super.onError(code, message);
                    }
                });
    }

    /**
     * App列表获取数据
     */
    public void getTopFreeapplications(int num) {
        MainModule.getTopFreeapplications(num)
                .compose(new LifecycleTransformer<>(view))
                .subscribe(new HideLoadingSubscriber<Object>(view) {
                    @Override
                    public void onNext(Object o) {
                        onSuccess(o, TYPE_FREE);
                    }

                    @Override
                    public void onError(int code, String message) {
                        super.onError(code, message);
                    }
                });
    }

    /**
     * 处理数据
     *
     * @param o    数据
     * @param type 1：推荐 2：APP列表
     */
    List<RecommendBean.FeedBean.EntryBean> listData = new ArrayList<>();

    private void onSuccess(Object o, int type) {
        if (o != null) {
            Log.e("Data", o.toString());
            RecommendBean grossingBean = JsonUtils.parseJson(o.toString(), RecommendBean.class);
            if (grossingBean != null && grossingBean.getFeed() != null) {
                List<RecommendBean.FeedBean.EntryBean> list = grossingBean.getFeed().getEntryList();
                if (type == TYPE_GROSS) {
                    List<ListBean> changeDataList = changeData(list);
                    view.callBackGross(changeDataList);
                } else if (type == TYPE_FREE) {
                    listData.clear();
                    listData.addAll(list);
                    if (!(listData.size() <= 10)) {
                        List<ListBean> changeDataList = changeData(listData.subList(listData.size() - 10, list.size()));
                        view.callBackFree(changeDataList);
                    } else {
                        view.callBackFree(changeData(listData));
                    }
                }
            }
        }
    }

    /**
     * 数据转换、添加数据到数据库
     */
    private List<ListBean> changeData(List<RecommendBean.FeedBean.EntryBean> list) {
        List<ListBean> listBeanList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            listBeanList.add(new ListBean(list.get(i)));
            //添加数据到数据库
            RoomUtils.insertData(getContext(), new ListBean(list.get(i)));
        }
        return listBeanList;
    }

    /**
     * 预加载
     */
    @Override
    public void onPreload() {
        if (!isLastPage) {
            //翻页加1，请求网络
            if (pageNum < 10) {
                ++pageNum;
                getTopFreeapplications(pageNum * REQUEST_DATA_NUM);
            } else {
                isLastPage = true;
                view.onLastPage(isLastPage);
            }
        }
    }

}

