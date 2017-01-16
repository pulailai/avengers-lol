package com.yuapt.service.core.utils;

import com.yanglinkui.ab.dsl.service.Statements;

import java.util.Map;

/**
 * Created by jimmy on 17/1/16.
 */
public class AvengersHttpHeader {


    private Map<String, String> headers;

    private Statements s;


    public AvengersHttpHeader() {
    }

    public AvengersHttpHeader(Map<String, String> headers, Statements s) {
        this.headers = headers;
        this.s = s;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Statements getS() {
        return s;
    }

    public void setS(Statements s) {
        this.s = s;
    }
}
