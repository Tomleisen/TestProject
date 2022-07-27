package com.tom.commonframework.common.base.view;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.lkn.net.utils.AppInstanceUtils;
import com.tom.commonframework.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Created by Tomleisen on 2018/1/19 0007 14:30
 * @version V1.0
 * Description:  自定义时长Toast
 */
public class MyToast {
    private static final String TAG = "MyToast";
    public static final String FIXCATION_TAG = "1";
    public static final String CUSTOM_TAG = "2";
    public static final int LENGTH_ALWAYS = 0;
    public static final int LENGTH_SHORT = 2;
    public static final int LENGTH_LONG = 4;

    private Toast toast;
    private Context mContext;
    private int mDuration = LENGTH_SHORT;
    private int animations = -1;
    private boolean isShow = false;

    private Object mTN;
    private Method show;
    private Method hide;
    private static MyToast myToast;
    //Toase 显示的位置  Gravity.TOP/Gravity.CENTER/Gravity.BOTTOM
    private static final int TOAST_LOACTION = Gravity.CENTER;

    private Handler handler = new Handler();

    public MyToast(Context context) {
        if (context != null) {
            if (toast == null) {
                toast = new Toast(context);
            }
        }else {
            if (toast == null) {
                toast = new Toast(context);
            }
        }
    }


    private Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    /**
    * 内部获取上下文
    * */
    public static Application getContext(){
        Application context = AppInstanceUtils.INSTANCE;
        return context;
    }

    /**
     * 固定时长的toast调用,支持R.string.id
     *
     * @param context
     * @param id
     */
    public static void show(Context context, @StringRes int id) {
        Application context1 = getContext();
        if (null != context1) {
            String msg = context1.getString(id);
            if (!TextUtils.isEmpty(msg)) {
                show(context1, msg);
            }
        }
    }

    /**
     * 固定时长的toast调用
     *
     * @param context
     * @param text
     */
    public static void show(Context context, String text) {
        Application context2 = getContext();
        if (null != context2) {
            if (!TextUtils.isEmpty(text)){
                if (myToast != null) {
                    myToast.hide();
                    myToast = MyToast.makeText(context2, text, 2000,FIXCATION_TAG);
                    toastLocation();
                } else {
                    myToast = MyToast.makeText(context2, text, 2000,FIXCATION_TAG);
                    toastLocation();
                }
            }
        }
    }

    /**
     * 自定义时长的toast,支持R.string.id
     *
     * @param context
     * @param id       R.string.id
     * @param duration 自定义时间
     */
    public static void show(Context context, @StringRes int id, int duration) {
        Application context3 = getContext();
        if (null != context3) {
            String msg = context3.getString(id);
            if (!TextUtils.isEmpty(msg)) {
                show(context3, msg, duration);
            }
        }
    }

    /**
     * 自定义时长的toast
     *
     * @param context
     * @param text
     * @param duration 自定义时间
     */
    public static void show(Context context, String text, int duration) {
        Application context4 = getContext();
        if (null != context4 ) {
            if (!TextUtils.isEmpty(text)){
                if (myToast != null) {
                    myToast.hide();
                    myToast = MyToast.makeText(context4, text, duration,CUSTOM_TAG);
                    if (null != myToast){
                        toastLocation();
                        myToast.show();
                    }
                } else {
                    myToast = MyToast.makeText(context4, text, duration,CUSTOM_TAG);
                    if (null != myToast){
                        toastLocation();
                        myToast.show();
                    }
                }
            }
        }
    }


    /**
     * Show the view for the specified duration.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)  //使用@TargetApi()， 使高版本API的代码在低版本SDK不报错
    public void show() {
        if (isShow) {
            return;
        }

        initTN();   //初始化TN里面的东西
        try {
            if (null != show && null != mTN){
                show.invoke(mTN);   //反射方法的调用
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        isShow = true;
        //判断duration，如果大于#LENGTH_ALWAYS 则设置消失时间
        if (mDuration > LENGTH_ALWAYS) {
            handler.postDelayed(hideRunnable, mDuration);
        }
    }

    /**
     * Close the view if it's showing, or don't show it if it isn't showing yet.
     * You do not normally have to call this.  Normally view will disappear on its own
     * after the appropriate duration.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void hide() {
        myToast = null;
        if (!isShow){
            return;
        }
        try {
            if (null != hide && null != mTN) {
                hide.invoke(mTN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        isShow = false;
    }

    public void setView(View view) {
        toast.setView(view);
    }

    public View getView() {
        return toast.getView();
    }

    /**
     * Set how long to show the view for.
     *
     * @see #LENGTH_SHORT
     * @see #LENGTH_LONG
     * @see #LENGTH_ALWAYS
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setMargin(float horizontalMargin, float verticalMargin) {
        toast.setMargin(horizontalMargin, verticalMargin);
    }

    public float getHorizontalMargin() {
        return toast.getHorizontalMargin();
    }

    public float getVerticalMargin() {
        return toast.getVerticalMargin();
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        toast.setGravity(gravity, xOffset, yOffset);
    }

    public int getGravity() {
        return toast.getGravity();
    }

    public int getXOffset() {
        return toast.getXOffset();
    }

    public int getYOffset() {
        return toast.getYOffset();
    }

    public static MyToast makeText(Context context, CharSequence text, int duration, String tag) {
        MyToast myToast = new MyToast(context);
        myToast.toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.layout_custom_toast,null);
        TextView textView = (TextView) view.findViewById(R.id.content);
        textView.setText(text);
        myToast.toast.setView(view);
        //此TAG标记区别是否是自定义时长调用，解决某些手机调用不显示toast情况（调用反射拿不到方法，直接利用系统显示）
        if (TextUtils.equals(tag,FIXCATION_TAG)){
            myToast.toast.show();
        }else if (TextUtils.equals(tag,CUSTOM_TAG)){
            myToast.mDuration = duration;
        }
        return myToast;
    }

    public static MyToast makeText(Context context, int resId, int duration, String tag)
            throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId), duration,tag);
    }

    public void setText(int resId) {
        setText(mContext.getText(resId));
    }

    public void setText(CharSequence s) {
        toast.setText(s);
    }

    public int getAnimations() {
        return animations;
    }

    public void setAnimations(int animations) {
        this.animations = animations;
    }

    private void initTN() {
        try {
            //通过反射机制，getDeclaredField获取到Toast类中的mTN
            Field tnField = toast.getClass().getDeclaredField("mTN");
            tnField.setAccessible(true);    //提高反射速度
            mTN = tnField.get(toast);

            show = mTN.getClass().getMethod("show");
            hide = mTN.getClass().getMethod("hide");
//            show = mTN.getClass().getDeclaredMethod("show");
//            hide = mTN.getClass().getDeclaredMethod("hide");
            //设置动画
            if (animations != -1) {
                Field tnParamsField = mTN.getClass().getDeclaredField("mParams");
                tnParamsField.setAccessible(true);
                WindowManager.LayoutParams params = (WindowManager.LayoutParams) tnParamsField.get(mTN);
                params.windowAnimations = animations;
            }

            //调用tn.show()之前一定要先设置mNextView*/
            Field tnNextViewField = mTN.getClass().getDeclaredField("mNextView");
            tnNextViewField.setAccessible(true);
            tnNextViewField.set(mTN, toast.getView());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * toast 显示的位置
     */
    private static void toastLocation(){
        if (TOAST_LOACTION != Gravity.BOTTOM){
            myToast.setGravity(TOAST_LOACTION, 0, 0);
        }
    }

}
