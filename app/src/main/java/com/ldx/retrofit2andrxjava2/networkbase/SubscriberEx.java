package com.ldx.retrofit2andrxjava2.networkbase;

import com.ldx.retrofit2andrxjava2.networkbase.ui.ProgressDialog;
import com.ldx.retrofit2andrxjava2.networkbase.utils.Util;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class SubscriberEx<T> implements Observer<T> {
    protected final RequestOptions mRequestOptions;
    private ProgressDialog mProgressDialog;
    private Disposable mDisposable;

    public SubscriberEx(RequestOptions requestOptions) {
        if (requestOptions == null) {
            throw new IllegalArgumentException("RequestOptions cannot be null");
        }
        this.mRequestOptions = requestOptions;
        if (mRequestOptions.isLoading()) {
            this.mProgressDialog = new ProgressDialog();
        }
    }

    // onSubscribe 是2.x新添加的方法，在发射数据前被调用，相当于1.x的onStart方法
    @Override
   public void onSubscribe(@NonNull Disposable d) {
        mDisposable = d;

        if (!Util.checkAlive(mRequestOptions.getCaller())) {
            mDisposable.dispose();
            return;
        }
        if (mRequestOptions.isLoading() && mProgressDialog != null) {
            mProgressDialog.showLoadingDialog(mRequestOptions.getContext(), mRequestOptions.getLoadingMessage(), mRequestOptions.isCancelable(), mRequestOptions.isCancelableOnTouchOutside());
        }
    }

   @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFinish();
    }


    @Override
    public void onComplete() {
        onFinish();
    }

    /**
     * This method must be called if you want to use the default loading dialog in case of override.
     * Otherwise you can ignore it.
     */
    private void onFinish() {
        if (mRequestOptions.isLoading() && mProgressDialog != null) {
            mProgressDialog.removeLoadingDialog();
            mProgressDialog = null;
        }
        if (!Util.checkAlive(mRequestOptions.getCaller())) {
            mDisposable.dispose();
        }
    }


}

