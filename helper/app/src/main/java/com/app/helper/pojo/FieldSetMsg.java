package com.app.helper.pojo;

import com.alibaba.fastjson.JSONObject;

public class FieldSetMsg implements Msg, MsgResolver {

    private String className;

    private String fieldName;

    private Param param;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    @Override
    public Msg resolveMsg(WsMsg msg) {
        JSONObject obj = msg.getMsg();
        return JSONObject.toJavaObject(obj, FieldSetMsg.class);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this,true);
    }
}
