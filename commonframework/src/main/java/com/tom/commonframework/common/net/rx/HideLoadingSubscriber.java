package com.tom.commonframework.common.net.rx;

import androidx.annotation.NonNull;

import com.tom.commonframework.common.base.ICommonView;


/**
 * Created by tomleisen on 2017/7/31.
 */

public abstract class HideLoadingSubscriber<T> extends LknDefaultSubscriber<T> {

    public HideLoadingSubscriber() {
        super();
    }

    public HideLoadingSubscriber(@NonNull ICommonView mvpCommonView) {
        super(mvpCommonView);
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (mvpCommonView != null) {
            mvpCommonView.dismissLoading();
        }
    }

    @Override
    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
        super.onError(e);
        if (mvpCommonView != null) {
            mvpCommonView.dismissLoading();
        }
    }
    public void cancelRequest(){
        cancel();
     }
    
}
