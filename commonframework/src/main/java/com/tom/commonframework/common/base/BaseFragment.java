package com.tom.commonframework.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.tom.commonframework.R;
import com.tom.commonframework.common.base.utils.EventBusUtils;
import com.tom.commonframework.databinding.FragmentOhtCommonBinding;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Tomleisen on 2018/1/19 0007 14:30
 * Email: xy162162a@163.com
 * Description:
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment
        implements ICommonView, LifecycleProvider<FragmentEvent>, View.OnClickListener {

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();
    protected P presenter;
    protected BaseActivity activity;
    private FragmentOhtCommonBinding binding;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBusUtils.register(this);
        generatePresenter();
        activity = (BaseActivity) context;
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (activity != null) {
            activity.setDefaultTypeFace();
        }
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    private void generatePresenter() {
        try {
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) type;
                presenter = ((Class<P>) pType.getActualTypeArguments()[0]).newInstance();
                presenter.setView(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        if (presenter != null) {
            presenter.onAttach();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_oht_common, container, false);
        int layoutId = getLayoutId();
        if (layoutId == 0) {
            if (initContentBinding(inflater)) {
                initView(binding.flBaseContainer);
            }
        } else {
            View root = inflater.inflate(layoutId, binding.flBaseContainer, true);
            initView(root);
        }
        setStatusBar();
        addEvents();
        return binding.getRoot();
    }

    protected boolean initContentBinding(LayoutInflater inflater) {
        return false;
    }

    protected abstract int getLayoutId();

    public <T extends ViewDataBinding> T getContentBinding(LayoutInflater inflater, @LayoutRes int layoutId) {
        return DataBindingUtil.inflate(inflater, layoutId, binding.flBaseContainer, true);
    }

    protected void initView(View view) {

    }

    protected void addEvents() {

    }

    protected void setStatusBar() {
        if (activity != null) {
            activity.setStatusBar();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Subscribe
    public void onEvent(ExitEvent exitEvent) {

    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDetach();
        }
    }

    @Override
    @CallSuper
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
        EventBusUtils.unregister(this);
    }

    @Override
    public void setScreenColor(int screenColor) {
        if (activity != null) {
            activity.setScreenColor(screenColor);
        }
    }

    @Override
    public void showToast(String msg) {
        if (activity != null) {
            activity.showToast(msg);
        }
    }

    @Override
    public void showToast(@StringRes int stringRes) {
        if (activity != null) {
            activity.showToast(stringRes);
        }
    }

    @Override
    public void showLoading() {
        if (activity != null) {
            activity.showLoading();
        }
    }

    @Override
    public void showLoading(String message) {
        if (activity != null) {
            activity.showLoading(message);
        }
    }

    @Override
    public void dismissLoading() {
        if (activity != null) {
            activity.dismissLoading();
        }
    }

    @Override
    public int color(int id) {
        return getResources().getColor(id);
    }

    @Override
    public void finish() {
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    public void setResult(int resultCode) {
        if (activity != null) {
            activity.setResult(resultCode);
        }
    }

    @Override
    public void setResult(int resultCode, Intent data) {
        if (activity != null) {
            activity.setResult(resultCode, data);
        }
    }

    @Override
    public void showDialog(String msg) {
        showDialog(msg, false);
    }

    @Override
    public void showDialog(String msg, boolean isShowTitle) {
        if (activity != null) {
            activity.showDialog(msg, isShowTitle);
        }
    }

    @Override
    public void showLogOutDialog(String msg) {
        if (activity != null) {
            activity.showLogOutDialog(msg);
        }
    }

    @Override
    public Bundle getBundle() {
        if (activity != null) {
            return activity.getBundle();
        }
        return null;
    }


    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    @CallSuper
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    @CallSuper
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

}
