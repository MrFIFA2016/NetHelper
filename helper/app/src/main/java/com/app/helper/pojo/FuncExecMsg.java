package com.app.helper.pojo;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class FuncExecMsg implements Msg, MsgResolver {

    private String signature;

    private List<Param> params;

    private String method;

    private Boolean newInstance;

    public Boolean getNewInstance() {
        return newInstance;
    }

    public void setNewInstance(Boolean newInstance) {
        this.newInstance = newInstance;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    @Override
    public Msg resolveMsg(WsMsg msg) {
        JSONObject obj = msg.getMsg();
        return JSONObject.toJavaObject(obj, FuncExecMsg.class);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, true);
    }
}
