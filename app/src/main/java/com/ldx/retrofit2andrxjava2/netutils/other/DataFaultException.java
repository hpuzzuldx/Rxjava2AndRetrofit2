package com.ldx.retrofit2andrxjava2.netutils.other;

public class DataFaultException extends Exception {

    private int errorCode;

    public DataFaultException(int errorCode, String message) {
        super(message);
        this.errorCode=errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
