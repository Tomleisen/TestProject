package com.tom.commonframework.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;

/**
 * @author Created by Tomleisen on 2018/1/19 0007 14:30
 * @version V1.0
 * Description:  基础框架接口
 */

public interface ICommonView {

    Context getContext();

    void showToast(String msg);

    void showToast(@StringRes int stringRes);

    void showLoading();

    void showLoading(String message);

    void dismissLoading();

    void showDialog(String msg);

    void showDialog(String msg, boolean isShowTitle);

    void showLogOutDialog(String msg);

    void finish();

    void setResult(int resultCode);

    void setResult(int resultCode, Intent data);

    int color(int id);

    void setScreenColor(int screenColor);

    Bundle getBundle();


}
