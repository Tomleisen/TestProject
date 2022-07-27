package com.tom.commonframework.common.net.rx;

import androidx.annotation.NonNull;

import com.tom.commonframework.common.base.ICommonView;


/**
 * Created by tomleisen on 2017/7/31.
 */

public abstract class NotHideLoadingSubscriber<T> extends LknDefaultSubscriber<T> {

    public NotHideLoadingSubscriber() {
        super();
    }

    public NotHideLoadingSubscriber(@NonNull ICommonView mvpCommonView) {
        super(mvpCommonView);
    }

    @Override
    public void onComplete() {
        super.onComplete();
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
