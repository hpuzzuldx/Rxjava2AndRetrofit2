package com.ldx.retrofit2andrxjava2.netutils.other;

import com.ldx.retrofit2andrxjava2.beans.BaseData;
import com.ldx.retrofit2andrxjava2.constants.NetConstants;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public class BaseObserver<T extends BaseData> implements Observer<T> {

    private ResponseListener listener;

    /**
     * @param listener 成功回调监听
     */
    public BaseObserver(ResponseListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (t == null) {
            onFailure(NetConstants.CODE_REQUEST_ERROR, "服务器返回错误");
            listener.onDataFault(t.getMsg(),t.getCode());
            return;
        }
        if (t.code == NetConstants.OK){
            listener.onDataSuccess(t);
        }else{
            listener.onDataFault(t.getMsg(),t.getCode());
            switch (t.code){
                case NetConstants.CODE_LOGIN_INVALID :
                    onLoginInvalid(t.msg);
                    break;

                case NetConstants.CODE_VERSION_INVALID:
                    onVersionInvalid(t.msg);
                    break;

                case NetConstants.CODE_SIGN_FAILURE:
                    onSignFailure(t.msg);
                    break;

                default:
                    onFailure(t.code, t.msg);
                    break;
            }
        }

    }

    private void onFailure(int codeRequestError, String 服务器返回错误) {
        //show toast
        System.out.println("=========登录失败============"+"服务器返回错误");
    }


    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof DataFaultException) {
            DataFaultException fault = (DataFaultException) e;
            listener.onDataFault(fault.getMessage(), fault.getErrorCode());
            return;
        }
        try {
            if (e instanceof SocketTimeoutException) {//请求超时
                listener.onNetFault("请求超时", 8);
            } else if (e instanceof ConnectException) {//网络连接超时
                listener.onNetFault("网络连接超时", 1);
            } else if (e instanceof SSLHandshakeException) {//安全证书异常
                listener.onNetFault("安全证书异常", 2);
            } else if (e instanceof HttpException) {//请求的地址不存在
                int code = ((HttpException) e).code();
                if (code == 504) {
                    listener.onNetFault("网络异常，请检查您的网络状态", 3);
                } else if (code == 404) {
                    listener.onNetFault("请求的地址不存在", 4);
                } else {
                    listener.onNetFault("请求失败", 5);
                }
            } else if (e instanceof UnknownHostException) {
                listener.onNetFault("域名解析失败", 6);
            } else {
                listener.onNetFault("error:" + e.getMessage(), 7);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }

    }

    /**
     * 登录失败处理
     * @param content
     */
    public static void onLoginInvalid(String content) {
        System.out.println("=========登录失败============"+content);
    }

    public static void onVersionInvalid(String message){
        System.out.println("=========登录失败============"+message);
    }

    public static void onSignFailure(String message){
        System.out.println("=========登录失败============"+message);
    }

}
