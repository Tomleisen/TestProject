package com.tom.commonframework.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.orhanobut.logger.Logger;
import com.tom.commonframework.R;
import com.tom.commonframework.common.base.utils.EventBusUtils;
import com.tom.commonframework.common.base.utils.StatusBarUtil;
import com.tom.commonframework.common.base.view.BaseDialog;
import com.tom.commonframework.common.base.view.NetProgressBar;
import com.tom.commonframework.databinding.ActivityBaseToolbarBinding;
import com.tom.commonframework.databinding.ActivityOhtCommonBinding;
import com.tom.commonframework.common.base.utils.LogOutEventBus;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity
        implements ICommonView, View.OnClickListener, LifecycleProvider<ActivityEvent> {

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
    private ToolBarModel toolBarModel = new ToolBarModel();
    private ActivityOhtCommonBinding binding;
    protected ActivityBaseToolbarBinding toolbarBinding;
    protected P presenter;
    private boolean isFirst = true;
    private NetProgressBar progressBar;
    //项目统一路径,如需在子目录新建则自己添加
    public static final String DIR = Environment.getExternalStorageDirectory() + File.separator + "SYApp" + File.separator + "Client";


    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        setDefaultTypeFace();
        super.onCreate(savedInstanceState);
        keepScreen(this);
        this.lifecycleSubject.onNext(ActivityEvent.CREATE);
        EventBusUtils.register(this);
        init();

        int layoutId = getLayoutId();
        if (layoutId != 0) {
            getContentBinding(layoutId);
            initView();
            setStatusBar();
            addEvents();
        }
    }


    private void init() {
        //1.初始化 databinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_oht_common);

        //2.构造 presenter
        generatePresenter();

        //3.初始化toolbar
        initDefaultToolbar();
    }

    protected abstract int getLayoutId();

    protected void initView() {

    }

    protected void addEvents() {

    }


    protected void onLeftToolBarViewClick(View view) {
        onBackPressed();
    }


    protected void onRightToolBarViewClick(View view) {

    }

    @Override
    public void onClick(View view) {

    }


    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        if (isFirst) {
            // 在初始化完成以后才调用
            if (presenter != null) {
                presenter.onAttach();
            }
            isFirst = false;
        }
        this.lifecycleSubject.onNext(ActivityEvent.START);
    }

    @NonNull
    @Override
    public Bundle getBundle() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Logger.d("页面传入bundle参数：key = " + bundle.keySet() + "," + bundle.toString());
                return bundle;
            }
        }
        return new Bundle();
    }


    private void generatePresenter() {
        Class<? extends BasePresenter> cls = getPresenterCls();
        try {
            if (cls != null) {
                this.presenter = (P) cls.newInstance();
                if (this.presenter != null) {
                    this.presenter.setView(this);
                }
            } else {
                Logger.e("没有指定presenter或者构造presenter失败 --！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected Class<? extends BasePresenter> getPresenterCls() {
        try {
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) type;
                return (Class<? extends BasePresenter>) pType.getActualTypeArguments()[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void initDefaultToolbar() {
        toolbarBinding = getToolbarBinding(R.layout.activity_base_toolbar);
//        setSupportActionBar(binding.toolbarHead);
        //设置默认的toobar颜色
        toolBarModel.background = color(R.color.C_00D294);
        toolBarModel.centerTextColor = color(R.color.C_FFFFFF);
        setToolbarTitle(toolBarModel);
        toolbarBinding.setToolbar(toolBarModel);
        toolbarBinding.rlLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLeftToolBarViewClick(v);

            }
        });
        toolbarBinding.tbLeftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLeftToolBarViewClick(v);

            }
        });
        toolbarBinding.rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightToolBarViewClick(v);
            }
        });
        toolbarBinding.tbRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightToolBarViewClick(v);

            }
        });
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.C_00D294));
    }

    /**
     * 设置刘海屏幕或者水滴屏的背景颜色
     */
    @Override
    public void setScreenColor(int screenColor) {
        binding.lyHead.setBackground(getResources().getDrawable(screenColor));
    }

    protected void setToolbarTitle(ToolBarModel toolBarModel) {
    }

    public <T extends ViewDataBinding> T getToolbarBinding(@LayoutRes int layoutId) {
        binding.toolbarHead.removeAllViews();
        return DataBindingUtil.inflate(getLayoutInflater(), layoutId, binding.toolbarHead, true);
    }

    public <T extends ViewDataBinding> T getContentBinding(@LayoutRes int layoutId) {
        return DataBindingUtil.inflate(getLayoutInflater(), layoutId, binding.flLayoutContainer, true);
    }

    protected void hideToolbar() {
        binding.toolbarHead.setVisibility(View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (presenter != null) {
            presenter.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToast(@StringRes int stringRes) {
        Toast.makeText(getApplicationContext(), getString(stringRes), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        showLoading("");
    }

    /**
     * @param message 提示语
     */
    @Override
    public void showLoading(String message) {
        if (progressBar == null) {
            progressBar = new NetProgressBar(this).netProgressBar(message);
        }
        if (!progressBar.isShowing()) {
            progressBar.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (progressBar != null && progressBar.isShowing()) {
            progressBar.dismiss();
        }
    }

    /**
     * 统一转换颜色方法
     *
     * @param id
     * @return
     */
    @Override
    public int color(int id) {
        return getResources().getColor(id);
    }


    private Dialog dialog;

    @Override
    public void showDialog(String msg) {
        showDialog(msg, false);
    }

    @Override
    public void showDialog(String msg, boolean isShowTitle) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new BaseDialog.Builder(getContext())
                .setWithTitle(isShowTitle)
                .setCancelable(false)
                .setCancelOutside(false)
                .setStyle(BaseDialog.OneButton)
                .setContent(msg)
                .setButtons(new String[]{"确定"})
                .build();
        dialog.show();
    }

    /**
     * 全局收到错误码 1003 或者其他需要跳转到登录的弹窗处理
     * 需要做处理的自行复写此方法
     */
    @Override
    public void showLogOutDialog(String msg) {
        EventBusUtils.sendEvent(new LogOutEventBus(msg));
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        this.lifecycleSubject.onNext(ActivityEvent.RESUME);
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        this.lifecycleSubject.onNext(ActivityEvent.DESTROY);
        if (presenter != null) {
            presenter.onDetach();
            presenter = null;
        }
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
        EventBusUtils.unregister(this);
    }

    @Subscribe
    public void onEvent(ExitEvent exitEvent) {
        if (exitEvent != null) {
            finish();
        }
    }

    public void setDefaultTypeFace() {
        try {
            Configuration configuration = this.getResources().getConfiguration();
            configuration.fontScale = 1.0F;
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getBaseContext().getResources().updateConfiguration(configuration, metrics);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return this.lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(this.lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(this.lifecycleSubject);
    }

    @Override
    @CallSuper
    protected void onPause() {
        this.lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        this.lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    /**
     * 保持屏幕常亮
     */
    public static void keepScreen(Activity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

}
