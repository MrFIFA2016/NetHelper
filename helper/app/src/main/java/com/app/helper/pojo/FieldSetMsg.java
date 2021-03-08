package com.app.helper.pojo;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class FieldSetMsg implements Msg, MsgResolver {

    private String className;

    private String fieldName;

    private Param param;


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
