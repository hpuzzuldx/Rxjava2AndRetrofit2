package com.ldx.retrofit2andrxjava2.netutils.other;

import com.ldx.retrofit2andrxjava2.beans.BaseData;
import com.ldx.retrofit2andrxjava2.constants.NetConstants;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BaseSubscribe {

    protected <T extends BaseData> void toDetachAndSubscribe(Observable<T> observable, ResponseListener<T> listener) {
        Observable<T> detachedObservable = detachDataAndMessageFrom(observable);
        detachedObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(listener));
    }

    private <T extends BaseData> Observable<T> detachDataAndMessageFrom(Observable<T> observable) {
        return observable.map(new Function<T, T>() {
            @Override
            public T apply(T tBaseResponse) throws Exception {
                if (tBaseResponse.code != NetConstants.OK) {
                    throw new DataFaultException(tBaseResponse.code, tBaseResponse.msg);
                }
                return tBaseResponse;
            }
        });
    }

}
