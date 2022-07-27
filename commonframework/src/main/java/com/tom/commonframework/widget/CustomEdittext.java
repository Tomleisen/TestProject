package com.tom.commonframework.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;

import com.tom.commonframework.R;
import com.tom.commonframework.common.base.view.MyToast;

import java.util.regex.Pattern;

/**
 * Created by TomLeisen on 2019-06-10 14:21
 * Email: xy162162a@163.com
 * Description: 自定义输入框，一键删除和屏蔽输入法自带表情
 */
public class CustomEdittext extends AppCompatEditText implements View.OnFocusChangeListener {

    private Context mContext;

    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;
    /**
     * 输入表情前的光标位置
     */
    private int cursorPos;
    /**
     * 输入表情前EditText中的文本
     */
    private String inputAfterText;
    /**
     * 是否重置了EditText的内容
     */
    private boolean resetText;


    public CustomEdittext(Context context) {
        this(context, null);
        init();
    }

    public CustomEdittext(Context context, AttributeSet attrs) {
        //这里构造方法很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
        init();
    }

    public CustomEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }


    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.ic_clear_keyword);
        }
        mClearDrawable.setBounds(0, 0, dip2px(mContext, 20), dip2px(mContext, 20));
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        initEditText();
    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                    openKeyBoard(mContext, this);
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    private void initEditText() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                if (!resetText) {
                    cursorPos = getSelectionEnd();
                    // 这里用s.toString()而不直接用s是因为如果用s，
                    // 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
                    // inputAfterText也就改变了，那么表情过滤就失败了
                    inputAfterText = s.toString();
                    setClearIconVisible(false);
                    if (edittextTextWatcher != null){
                        edittextTextWatcher.beforeTextChanged(s,start,before,count);
                    }
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!resetText) {
                    try {

                        if (!checkSpecialSymbol(s.toString())) {
                            resetText = true;
//                            MyToast.show(mContext, "不支持输入特殊符号");
                            //是表情符号就将文本还原为输入表情符号之前的内容
                            setText(inputAfterText);
                            CharSequence text = getText();
                            if (text instanceof Spannable) {
                                Spannable spanText = (Spannable) text;
                                Selection.setSelection(spanText, text.length());
                            }
                        } else {
                            //显示删除图标
                            if (hasFoucs) {
                                setClearIconVisible(s.length() > 0);
                                if (edittextTextWatcher != null){
                                    edittextTextWatcher.onTextChanged(s,start,before,count);
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        MyToast.show(mContext, "不支持输入特殊符号");
                    }
                } else {
                    resetText = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edittextTextWatcher != null){
                    edittextTextWatcher.afterTextChanged(editable);
                }
            }
        });
    }

    /**
     * 正则过滤特殊符号
     * 除以下标点符号之外都为特殊符号
     *
     * @param str
     * @return
     */
    public boolean checkSpecialSymbol(String str) {
        Pattern pattern = Pattern.compile("[0-9a-zA-Z\\u4e00-\\u9fa5_,.?~!:;(){}@\"\"$￥<>''#%-=*/+ ……、·《》“”，。？~！@#：；（）‘’=+——%-]*");
        return pattern.matcher(str).matches();
    }


    /**
     * 重写 TextWatcher ,如要联动监听的话必须要用此接口，不然联动监听时表情符号过滤不了
     */
    public interface CustomEdittextTextWatcher{
         void beforeTextChanged(CharSequence s, int start, int count, int after);
         void onTextChanged(CharSequence s, int start, int before, int count);
         void afterTextChanged(Editable s);
    }

    private CustomEdittextTextWatcher edittextTextWatcher;

    public void addEdittextTextWatcher(CustomEdittextTextWatcher edittextTextWatcher) {
        this.edittextTextWatcher = edittextTextWatcher;
    }


    /**
     * 打开键盘
     *
     * @param context
     * @param editText
     */
    public static void openKeyBoard(final Context context, final View editText) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
            }
        }, 500);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
