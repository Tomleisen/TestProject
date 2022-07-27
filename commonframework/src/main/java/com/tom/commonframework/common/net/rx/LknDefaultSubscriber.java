package com.tom.commonframework.common.net.rx;

import android.app.Dialog;
import android.net.ParseException;
import android.text.TextUtils;
import android.util.MalformedJsonException;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import com.google.gson.JsonSyntaxException;
import com.lkn.net.exception.EmptyException;
import com.lkn.net.exception.ServerException;
import com.lkn.net.response.IResponse;
import com.lkn.net.utils.AppInstanceUtils;
import com.lkn.net.utils.LogUtils;
import com.tom.commonframework.common.base.ICommonView;
import com.tom.commonframework.common.base.view.BaseDialog;
import com.tom.commonframework.common.base.view.MyToast;
import com.tom.commonframework.common.net.exception.NoConnectionException;

import org.json.JSONException;

import java.net.SocketTimeoutException;

import io.reactivex.subscribers.DefaultSubscriber;
import retrofit2.HttpException;

/**
 * Created by Tomleisen on 2017/7/25.
 */

public abstract class LknDefaultSubscriber<T> extends DefaultSubscriber<T> {

    public static final int ERROR_THROWABLE_NULL = -10000;
    public static final int ERROR_IRESPONSE_NULL = -10001;
    public static final int ERROR_PARSE = -10002;
    public static final int ERROR_CONNECT = -10003;
    public static final int ERROR_NETWORK = -10004;
    public static final int ERROR_UNKOWN = -10005;

    protected ICommonView mvpCommonView;


    public LknDefaultSubscriber() {
    }

    public LknDefaultSubscriber(@NonNull ICommonView mvpCommonView) {
        this.mvpCommonView = mvpCommonView;
    }

    @Override
    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
        if (null == e) {
            onError(ERROR_THROWABLE_NULL, "");
            return;
        }
//        LoggerUtils.e(e,"OhtException~~");
        //服务器状态码 与response 对应的isSuccess 不匹配，一般都是业务错误
        if (e instanceof ServerException) {
            ServerException error = (ServerException) e;
            LogUtils.e(error.getMessage());
            IResponse response = error.getResponse();
            this.onError(response.getCode(), response.getMessage());
            return;
        }

        if (e instanceof EmptyException) {
            EmptyException error = (EmptyException) e;
            LogUtils.e(error.getMessage());
            this.onError(ERROR_IRESPONSE_NULL, error.getMessage());
            return;
        }

        if (e instanceof HttpException || e instanceof SocketTimeoutException || e instanceof NoConnectionException) {
            this.onError(ERROR_NETWORK, "");
            return;
        }
        //单元测试异常
        if (e.toString().startsWith("junit.framework")) {
            throw new RuntimeException(e);
        }
        if ((e instanceof MalformedJsonException) || (e instanceof JSONException) || (e instanceof ParseException)
                || (e instanceof JsonSyntaxException) || (e instanceof UnsupportedOperationException)) {
            this.onError(ERROR_PARSE, "");
            return;
        }
        onError(!TextUtils.isEmpty(e.getMessage()) ? ERROR_CONNECT : ERROR_UNKOWN, e.getMessage());
    }

    @CallSuper
    public void onError(int code, String message) {
        switch (code) {
            // Throwable 为空
            case ERROR_THROWABLE_NULL:
                break;
            // Observer<IResponse>  为空
            case ERROR_IRESPONSE_NULL:
                showToast("服务器错误，请稍后再试");
                return;
            // 数据解析失败
            case ERROR_PARSE:
                showToast("数据解析失败");
                return;
            // 网络错误
            case ERROR_NETWORK:
                showToast("服务器错误，请稍后再试");
                return;
            // 请检查网络
            case ERROR_UNKOWN:
                showToast("网络不给力，请稍后再试");
                return;
            //没有网络连接
            case ERROR_CONNECT:
                showToast("网络不给力，请稍后再试");
                return;
            case 10024:
//                showToast("不是公司设备，被拦截");
                return;
            case 10003:// 请重新登录
                showLogOutDialog(message);
                return;
            default:
                break;
        }
//        showToast(message);
        showErrorMsg(code, message);
    }

    /**
     * 错误码处理, 错误码第二位为 0 则Toast,为 1 则 Dialog
     *
     * @param code    状态码
     * @param message 提示消息
     */
    private void showErrorMsg(int code, String message) {
        //TODO 特殊处理
        String codeStr = getHeaderStateIndex(code, 2);
        switch (codeStr) {
            case "0":
                showToast(message);
                break;
            case "1":
                showDialog(message, false);//todo 用户端不需要弹窗
                break;
            default:
                showToast(message);
                break;
        }

    }

    private void showToast(String msg) {
        if (mvpCommonView != null) {
            mvpCommonView.showToast(msg);
        } else {
            MyToast.show(AppInstanceUtils.INSTANCE, msg);
        }
    }

    private Dialog dialog;

    private void showDialog(String msg, boolean isShowTitle) {
        if (mvpCommonView != null) {
            mvpCommonView.showDialog(msg);
        } else {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = new BaseDialog.Builder(AppInstanceUtils.INSTANCE)
                    .setWithTitle(isShowTitle)
                    .setCancelable(false)
                    .setCancelOutside(false)
                    .setStyle(BaseDialog.OneButton)
                    .setContent(msg)
                    .setButtons(new String[]{"确定"})
                    .build();
            dialog.show();
        }
    }

    private void showLogOutDialog(String msg) {
        if (mvpCommonView != null) {
            mvpCommonView.showLogOutDialog(msg);
        }
    }

    /**
     * 返回错误码具体位数值
     *
     * @return 返回具体常量，用于外部具体区分使用UI组件
     */
    private String getHeaderStateIndex(Integer target, int targetIndex) {
        String result = "-1";
        try {
            String targetStr = target.toString();
            if (null != targetStr && targetStr.length() > targetIndex) {
                Character c = targetStr.charAt(targetIndex - 1);
                result = c.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "-1";
        }
        return result;
    }

    @Override
    public void onComplete() {
    }

}
