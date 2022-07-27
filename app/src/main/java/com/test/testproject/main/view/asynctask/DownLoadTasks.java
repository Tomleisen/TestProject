package com.test.testproject.main.view.asynctask;

import android.os.AsyncTask;

import com.lkn.net.transformer.LifecycleTransformer;
import com.test.testproject.main.module.MainModule;
import com.test.testproject.main.module.bean.RatingBean;
import com.tom.commonframework.common.base.ICommonView;
import com.tom.commonframework.common.base.utils.JsonUtils;
import com.tom.commonframework.common.net.rx.HideLoadingSubscriber;

/**
 * Created by TomLeisen on 2022/7/24 2:20 下午
 * Email: xy162162a@163.com
 * Description:异步下载数据
 */
public class DownLoadTasks extends AsyncTask<String, Void, Object> {

    private DownLoadCallback mCallback;
    private ICommonView view;
    private String id;
    private int position;

    public interface DownLoadCallback {
        void getNeededBreads(Object o, int positions);
    }

    public DownLoadTasks(ICommonView views, String ids, DownLoadCallback callback) {
        mCallback = callback;
        id = ids;
        view = views;
    }

    public DownLoadTasks(ICommonView views, String ids, int positions, DownLoadCallback callback) {
        mCallback = callback;
        id = ids;
        view = views;
        position = positions;
    }

    @Override
    protected Object doInBackground(String... strings) {
        getLookup(id);
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o != null) {
            mCallback.getNeededBreads(o, position);
        }
    }

    /**
     * 根据ID搜索
     */
    public void getLookup(String id) {
        MainModule.getLookup(id)
                .compose(new LifecycleTransformer<>(view))
                .subscribe(new HideLoadingSubscriber<Object>(view) {
                    @Override
                    public void onNext(Object o) {
                        if (o != null) {
                            RatingBean bean = JsonUtils.parseJson(o.toString(), RatingBean.class);
                            if (bean != null) {
                                onPostExecute(o);
                            }
                        }
                    }
                    @Override
                    public void onError(int code, String message) {
                        super.onError(code, message);
                    }
                });
    }
}

