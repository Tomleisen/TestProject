package com.tom.commonframework.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.StringRes;

import com.google.gson.Gson;

/**
 * @author Created by Tomleisen on 2018/1/19 0007 14:30
 * @version V1.0
 * @function Databinding 框架对应的Presenter
 */

public abstract class BasePresenter<V extends ICommonView> implements ILifeCycle {

    protected V view;

    public void setView(V view) {
        this.view = view;
    }

    public V getView() {
        return this.view;
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDetach() {

    }

    public Context getContext() {
        return view != null ? view.getContext() : null;
    }

    protected void showToast(String msg) {
        if (view != null) {
            view.showToast(msg);
        }
    }

    protected void showToast(@StringRes int stringRes) {
        if (view != null) {
            view.showToast(stringRes);
        }
    }

    protected void showLoading() {
        if (view != null) {
            view.showLoading();
        }
    }

    protected void dismissLoading() {
        if (view != null) {
            view.dismissLoading();
        }
    }

    protected void showDialog(String msg) {
        if (view != null) {
            view.showDialog(msg);
        }
    }

    protected void finish() {
        if (view != null) {
            view.finish();
        }
    }

    protected void setResult(int resultCode) {
        if (view != null) {
            view.setResult(resultCode);
        }
    }

    protected void setResult(int resultCode, Intent data) {
        if (view != null){
            view.setResult(resultCode, data);
        }
    }

    protected Bundle getBundle() {
        return view != null ? view.getBundle() : null;
    }

    /**
     * 添加跳转回调
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    protected <K> K getParamEntityJsonString(Class<K> clazz) {
        try {
            String targetParam = view.getBundle().getString("target_param");
            K result = null;
            if(!TextUtils.isEmpty(targetParam)) {
                result = (new Gson()).fromJson(targetParam, clazz);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
