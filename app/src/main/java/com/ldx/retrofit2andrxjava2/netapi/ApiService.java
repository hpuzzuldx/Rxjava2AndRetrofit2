package com.ldx.retrofit2andrxjava2.netapi;

import com.ldx.retrofit2andrxjava2.beans.DataInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("mock/5d400f6307d91f4942763fb1/demo/rxjava2/getdemo")
    Observable<DataInfo> getInfo(@QueryMap Map<String, Object> params);
}
