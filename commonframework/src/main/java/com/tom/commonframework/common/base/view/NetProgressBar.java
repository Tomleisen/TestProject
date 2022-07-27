package com.tom.commonframework.common.base.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tom.commonframework.R;

/**
 * Created by TomLeisen on 2018/8/6 下午 1:36
 * Email: xy162162a@163.com
 * Description: Loading
 */
public class NetProgressBar extends Dialog {

    private Context context;

    public NetProgressBar(Context context) {
        super(context);
        this.context = context;
    }

    public  NetProgressBar netProgressBar(String message) {
        NetProgressBar.Builder loadingBuilder = new NetProgressBar.Builder(context)
                .setMessage(message);
        loadingBuilder.create();
        return loadingBuilder.create();
    }

    public NetProgressBar(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private String message;
        private boolean isShowMessage = true;
        private boolean isCancelable = true;
        private boolean isCancelOutside = false;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示信息
         *
         * @param message
         * @return
         */

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 设置是否显示提示信息
         *
         * @param isShowMessage
         * @return
         */
        public Builder setShowMessage(boolean isShowMessage) {
            this.isShowMessage = isShowMessage;
            return this;
        }

        /**
         * 设置点击物理返回键是否可以关闭dialog
         *
         * @param isCancelable
         * @return
         */

        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        /**
         * 设置点击屏幕其他区域是否可以关闭dialog
         *
         * @param isCancelOutside
         * @return
         */
        public Builder setCancelOutside(boolean isCancelOutside) {
            this.isCancelOutside = isCancelOutside;
            return this;
        }

        public NetProgressBar create() {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_loading, null);
            NetProgressBar progressBar = new NetProgressBar(context, R.style.DialogStyle);
            TextView msgText =  view.findViewById(R.id.tipTextView);
            if (isShowMessage) {
                msgText.setText(message);
            } else {
                msgText.setVisibility(View.GONE);
            }
            progressBar.setContentView(view);
            progressBar.setCancelable(isCancelable);
            progressBar.setCanceledOnTouchOutside(isCancelOutside);
            return progressBar;

        }


    }
}
