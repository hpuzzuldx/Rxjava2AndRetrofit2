package com.ldx.retrofit2andrxjava2;

import android.app.Activity;
import android.os.Bundle;

import com.ldx.retrofit2andrxjava2.beans.BaseData;
import com.ldx.retrofit2andrxjava2.beans.DataInfo;
import com.ldx.retrofit2andrxjava2.netapi.ApiService;
import com.ldx.retrofit2andrxjava2.netapi.BaseUrlConfig;
import com.ldx.retrofit2andrxjava2.netsubscribe.ApiSubscribe;
import com.ldx.retrofit2andrxjava2.netutils.SubscriberImpl;
import com.ldx.retrofit2andrxjava2.netutils.other.ResponseListener;
import com.ldx.retrofit2andrxjava2.networkbase.RequestOptions;
import com.ldx.retrofit2andrxjava2.networkbase.RetrofitManager;
import com.ldx.retrofit2andrxjava2.networkbase.utils.SchedulerTransform;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ApiSubscribe business = new ApiSubscribe();
        business.getInfo(MainActivity.this, new ResponseListener() {
            @Override
            public void onNetFault(String errorMsg, int errorCode) {
                System.out.println("=========11111=====111======" + errorMsg + errorCode);
            }

            @Override
            public void onDataFault(String errorMsg, int errorCode) {
                System.out.println("=========11111=====222======" + errorMsg + errorCode);
            }

            @Override
            public void onDataSuccess(BaseData data) {
                System.out.println("=========11111=====333======" + data.toString());
            }
        });

        business.getInfo2(MainActivity.this, new ResponseListener() {
            @Override
            public void onNetFault(String errorMsg, int errorCode) {
                System.out.println("=========2222=====111======" + errorMsg + errorCode);
            }

            @Override
            public void onDataFault(String errorMsg, int errorCode) {
                System.out.println("=========22222=====22222=====" + errorMsg + errorCode);
            }

            @Override
            public void onDataSuccess(BaseData data) {
                System.out.println("=========2222=====333======" + data.toString());
            }
        });

        Map<String, Object> source = new HashMap<>();
        RetrofitManager.getService(BaseUrlConfig.BASE_URL_API, ApiService.class).getInfo(source)
                .compose(SchedulerTransform.getInstance().<DataInfo>applySchedulers())
                .subscribe(new SubscriberImpl<DataInfo>(RequestOptions.create(this).loading(true).loadingMessage("等待。。。")) {
                    @Override
                    protected void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        System.out.println("=========3333=====111======" + code + message);
                    }

                    @Override
                    protected void onSuccess(DataInfo o) {
                        super.onSuccess(o);
                        System.out.println("=========33333=====3333======" + o.toString());
                    }

                    @Override
                    protected void onNetworkLost() {
                        super.onNetworkLost();
                        System.out.println("=========33333=====2222======");
                    }
                });

    }
    }
