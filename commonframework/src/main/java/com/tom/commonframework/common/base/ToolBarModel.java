package com.tom.commonframework.common.base;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.tom.commonframework.BR;
import com.tom.commonframework.R;


/**
 * @author Created by Tomleisen on 2018/1/19 0007 14:30
 * @version V1.0
 * @function
 */

public class ToolBarModel  extends BaseObservable {

    @Bindable
    public int background;

    @Bindable
    public String leftText;
    @Bindable
    public int leftImageRes = R.drawable.ic_back;

    @Bindable
    public String centerText;
    @Bindable
    public int centerTextColor;
    public int centerImageRes;

    @Bindable
    public String rightText;
    @Bindable
    public int rightImageRes;
    @Bindable
    public boolean bottomLineVisible = true;

    @Bindable
    public boolean leftToolBarVisible = true;
    @Bindable
    public boolean rightToolBarVisible = true;

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
        notifyPropertyChanged(BR.leftText);
    }

    public void setLeftImageRes(int leftImageRes) {
        this.leftImageRes = leftImageRes;
        notifyPropertyChanged(BR.leftImageRes);
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
        notifyPropertyChanged(BR.centerText);
    }

    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
        notifyPropertyChanged(BR.centerTextColor);
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
        notifyPropertyChanged(BR.rightText);
    }

    public void setRightImageRes(int rightImageRes) {
        this.rightImageRes = rightImageRes;
        notifyPropertyChanged(BR.rightImageRes);
    }

    public void setBackground(int background) {
        this.background = background;
        notifyPropertyChanged(BR.background);
    }

    public void setBottomLineVisible(boolean bottomLineVisible) {
        this.bottomLineVisible = bottomLineVisible;
        notifyPropertyChanged(BR.bottomLineVisible);
    }

    public void setLeftToolBarVisible(boolean leftToolBarVisible) {
        this.leftToolBarVisible = leftToolBarVisible;
        notifyPropertyChanged(BR.leftToolBarVisible);
    }

    public void setRightToolBarVisible(boolean rightToolBarVisible) {
        this.rightToolBarVisible = rightToolBarVisible;
        notifyPropertyChanged(BR.rightToolBarVisible);
    }
}
