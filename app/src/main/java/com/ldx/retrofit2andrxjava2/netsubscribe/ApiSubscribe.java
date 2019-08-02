package com.ldx.retrofit2andrxjava2.netsubscribe;

import android.content.Context;

import com.ldx.retrofit2andrxjava2.netapi.ApiService;
import com.ldx.retrofit2andrxjava2.netapi.BaseUrlConfig;
import com.ldx.retrofit2andrxjava2.netutils.other.BaseObserver;
import com.ldx.retrofit2andrxjava2.netutils.other.BaseSubscribe;
import com.ldx.retrofit2andrxjava2.netutils.other.ResponseListener;
import com.ldx.retrofit2andrxjava2.networkbase.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApiSubscribe extends BaseSubscribe {
    private ApiService api;

    public ApiSubscribe() {
        api = RetrofitManager.getService(BaseUrlConfig.BASE_URL_API, ApiService.class);
    }

    public void getInfo(Context context, final ResponseListener listener) {
        Map<String, Object> source = new HashMap<>();
        api.getInfo(source)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(listener));
    }

    public void getInfo2(Context context, final ResponseListener listener) {
        Map<String, Object> source = new HashMap<>();
        toDetachAndSubscribe(api.getInfo(source),listener);
    }

}
