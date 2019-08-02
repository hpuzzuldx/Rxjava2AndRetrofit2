package com.ldx.retrofit2andrxjava2.netutils;

import com.ldx.retrofit2andrxjava2.beans.BaseData;
import com.ldx.retrofit2andrxjava2.constants.NetConstants;
import com.ldx.retrofit2andrxjava2.networkbase.RequestOptions;
import com.ldx.retrofit2andrxjava2.networkbase.SubscriberBase;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

public abstract class SubscriberImpl<T extends BaseData> extends SubscriberBase<T> {

    public SubscriberImpl(RequestOptions requestOptions) {
        super(requestOptions);
    }

    //网络错误处理
    @Override
    public void onError(Throwable e) {
        super.onError(e);
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {//请求超时
            // listener.onNetFault("请求超时", 8);
            onNetworkLost();
        } else if (e instanceof ConnectException) {//网络连接超时
            // listener.onNetFault("网络连接超时", 1);
            onNetworkLost();
        } else if (e instanceof SSLHandshakeException) {//安全证书异常
            //listener.onNetFault("安全证书异常", 2);
            onNetworkLost();
        } else if (e instanceof HttpException) {//请求的地址不存在
            int code = ((HttpException) e).code();
            if (code == 504) {
                // listener.onNetFault("网络异常，请检查您的网络状态", 3);
                onNetworkLost();
            } else if (code == 404) {
                // listener.onNetFault("请求的地址不存在", 4);
                onNetworkLost();
            } else {
                // listener.onNetFault("请求失败", 5);
                onNetworkLost();
            }
        } else if (e instanceof UnknownHostException) {
            //listener.onNetFault("域名解析失败", 6);
            onNetworkLost();
        } else {
            onFailure(NetConstants.CODE_REQUEST_ERROR, "网络错误");
        }
    }

    @Override
    public void onNext(T o) {
        super.onNext(o);
        if (o == null) {
            onFailure(NetConstants.CODE_REQUEST_ERROR, "服务器返回错误");
            return;
        }

        switch (o.code) {
            case NetConstants.OK:
                onSuccess(o);
                break;

            case NetConstants.CODE_LOGIN_INVALID:
                onLoginInvalid(o.msg);
                break;

            case NetConstants.CODE_VERSION_INVALID:
                onVersionInvalid(o.msg);
                break;

            case NetConstants.CODE_SIGN_FAILURE:
                onSignFailure(o.msg);
                break;

            default:
                onFailure(o.code, o.msg);
                break;
        }
    }

    protected void onFailure(int code, String message) {
    }

    protected void onSuccess(T o) {
    }

    protected void onNetworkLost() {
        //网络发生错误
        System.out.println("=========网络错误============");
    }

    /**
     * 登录失败处理
     *
     * @param content
     */
    public static void onLoginInvalid(String content) {

        System.out.println("=========登录失败============" + content);
    }

    public static void onVersionInvalid(String message) {

        System.out.println("=========登录失败============" + message);
    }

    public static void onSignFailure(String message) {

        System.out.println("=========登录失败============" + message);
    }
}
