package com.tom.commonframework.common.base.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tom.commonframework.R;


/**
 * Created by TomLeisen on 2018/8/14 下午3:26
 * Email: xy162162a@163.com
 * Description: 公用弹窗
 */

public class BaseDialog extends Dialog {

    //一个按钮
    public static int OneButton = 1;
    //两个按钮
    public static int TwoButton = 2;
    private DialogParams dialogParams;

    public BaseDialog(@NonNull Context context, @NonNull DialogParams dialogParams) {
        super(context, R.style.DialogStyle);
        this.dialogParams = dialogParams;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_with_two_button);
        setContentView(R.layout.dialog_with_two_button_auto);
        initViews();
        initWinAttr();
    }

    private void initViews() {
        TextView title = (TextView) findViewById(R.id.title);
        TextView content = (TextView) findViewById(R.id.content);
        TextView left = (TextView) findViewById(R.id.left);
        View vDivider = findViewById(R.id.v_divider);
        TextView right = (TextView) findViewById(R.id.right);
        setCancelable(dialogParams.isCancelable);
        setCanceledOnTouchOutside(dialogParams.isCancelOutside);
        if (dialogParams.style == OneButton) {
            vDivider.setVisibility(View.GONE);
            left.setVisibility(View.GONE);
        }
        if (!dialogParams.withTitle) {
            title.setVisibility(View.GONE);
        }
        title.setText(dialogParams.title);
        content.setText(dialogParams.content);
        TextView[] views;
        if (dialogParams.style == OneButton) {
            views = new TextView[]{right};
        } else {
            views = new TextView[]{left, right};
        }

        if (dialogParams.buttonContents != null) {
            int len = dialogParams.buttonContents.length;
            if (dialogParams.style == OneButton) {
                views[0].setText(dialogParams.buttonContents[0]);
            } else {
                for (int i = 0; i < len && i < views.length; i++) {
                    views[i].setText(dialogParams.buttonContents[i]);
                }
            }
        }

        if (dialogParams.buttonColor != null) {
            int len = dialogParams.buttonColor.length;
            if (dialogParams.style == OneButton) {
                int color = dialogParams.buttonColor[1];
                views[0].setTextColor(getColors(color));
            } else {
                for (int i = 0; i < len && i < views.length; i++) {
                    int color = dialogParams.buttonColor[i];
                    views[i].setTextColor(getColors(color));
                }
            }

        }

        for (int i = 0; i < views.length; i++) {
            final int position = i;
            views[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnClickListener listener = dialogParams.buttonListeners[position];
                    if (listener != null) {
                        listener.onClick(v);
                    }
                    dismiss();
                }
            });
        }
        // addEvent
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(0x55D8D8D8);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundColor(0x00000000);
                        break;
                    default:
                        break;
                }
                return false;
            }
        };
        left.setOnTouchListener(touchListener);
        right.setOnTouchListener(touchListener);
    }

    private void initWinAttr() {
        Window win = getWindow();
        if (win != null) {
            WindowManager.LayoutParams lp = win.getAttributes();

            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            lp.width = displayMetrics.widthPixels - Math.round(2 * 28 * displayMetrics.density);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            win.setAttributes(lp);
        }
    }


    @Override
    public void dismiss() {
        if (dialogParams.dismissListener != null) {
            dialogParams.dismissListener.onDismiss();
        }
        super.dismiss();
    }

    public static class Builder {

        private DialogParams dialogParams;
        private Context context;

        public Builder(@NonNull Context context) {
            this.context = context;
            dialogParams = new DialogParams();
        }

        /**
         * 设置点击物理返回键是否可以关闭dialog
         *
         * @param isCancelable
         * @return
         */
        public Builder setCancelable(boolean isCancelable) {
            dialogParams.isCancelable = isCancelable;
            return this;
        }

        /**
         * 设置点击屏幕其他区域是否可以关闭dialog
         *
         * @param isCancelOutside
         * @return
         */
        public Builder setCancelOutside(boolean isCancelOutside) {
            dialogParams.isCancelOutside = isCancelOutside;
            return this;
        }

        /**
         * 是否显示标题
         *
         * @param withTitle
         * @return
         */
        public Builder setWithTitle(boolean withTitle) {
            dialogParams.withTitle = withTitle;
            return this;
        }

        /**
         * 设置标题内容
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            dialogParams.title = title;
            return this;
        }

        /**
         * 设置显示的内容
         *
         * @param content
         * @return
         */
        public Builder setContent(String content) {
            dialogParams.content = content;
            return this;
        }

        /**
         * Dialog的显示样式
         *
         * @param style
         * @return
         */
        public Builder setStyle(int style) {
            dialogParams.style = style;
            return this;
        }

        /**
         * 底部按钮的文字
         *
         * @param buttonContents
         * @return
         */
        public Builder setButtons(@NonNull String[] buttonContents) {
            dialogParams.buttonContents = buttonContents;
            return this;
        }

        /**
         * 设置按钮颜色
         *
         * @param buttonColor 如果 buttonColor 为 0 则设置默认颜色 R.color.C_666666
         * @return
         */
        public Builder setButtonsColor(@NonNull int[] buttonColor) {
            dialogParams.buttonColor = buttonColor;
            return this;
        }

        /**
         * 设置点击事件，与上面setButtons对应
         *
         * @param buttonListeners
         * @return
         */
        public Builder buttonsListener(OnClickListener[] buttonListeners) {
            dialogParams.buttonListeners = buttonListeners;
            return this;
        }

        /**
         * 添加消失监听
         *
         * @param dismissListener
         * @return
         */
        public Builder addDismissListener(OnDismissListener dismissListener) {
            dialogParams.dismissListener = dismissListener;
            return this;
        }


        public Dialog build() {
            return new BaseDialog(context, dialogParams);
        }

    }

    private static class DialogParams {
        int style = TwoButton;
        boolean isCancelable = true;
        boolean isCancelOutside = true;
        boolean withTitle = true;
        String title = "提示";
        String content = "展示的消息内容";
        String[] buttonContents = {"取消", "确定"};
        int[] buttonColor = {R.color.C_666666, R.color.C_00D294};
        OnClickListener[] buttonListeners = {null, null};
        OnDismissListener dismissListener;
    }

    public interface OnClickListener {
        void onClick(View v);
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    private int getColors(int color) {
        if (color == 0) {
            return getContext().getResources().getColor(R.color.C_666666);
        }
        return getContext().getResources().getColor(color);
    }

}
