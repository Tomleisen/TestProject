package com.test.testproject.main.view.utils;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.test.testproject.R;

/**
 * Created by TomLeisen on 2022/7/26 3:18 下午
 * Email: xy162162a@163.com
 * Description:工具类
 */
public class MainUtils {

    /**
     * 收起键盘
     */
    public static void hide(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null && view.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 判断用户点击的区域是否是输入框
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 加载四个圆角图
     *
     * @param context
     * @param imageUrl
     * @param view
     */
    public static void loadRoundedImage(Context context, Object imageUrl, ImageView view) {
        //预加载
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .preload();

        Glide.with(context).asBitmap()
                .load(imageUrl)
                .apply(new RequestOptions().placeholder(R.drawable.base_progress))
                .into(view);
    }

} 

