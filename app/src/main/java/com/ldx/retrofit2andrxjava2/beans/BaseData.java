package com.ldx.retrofit2andrxjava2.beans;

import com.ldx.retrofit2andrxjava2.constants.NetConstants;

import java.io.Serializable;

public class BaseData implements Cloneable, Serializable {
    public int code = NetConstants.NO_DATA;
    public String msg;

    public BaseData clone() {
        try {
            BaseData obj;
            obj = (BaseData)super.clone();
            return obj;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
