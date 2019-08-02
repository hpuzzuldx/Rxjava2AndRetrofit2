package com.ldx.retrofit2andrxjava2.netutils.other;

import com.ldx.retrofit2andrxjava2.beans.BaseData;

public interface ResponseListener <T extends BaseData>{
    void onNetFault(String errorMsg, int errorCode);

    void onDataFault(String errorMsg, int errorCode);

    void onDataSuccess(T data);
}
