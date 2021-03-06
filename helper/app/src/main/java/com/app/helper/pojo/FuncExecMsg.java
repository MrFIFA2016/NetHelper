package com.app.helper.pojo;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import lombok.Data;

@Data
public class FuncExecMsg implements Msg, MsgResolver {

    private String signature;

    private List<Param> params;

    private String method;

    private Boolean newInstance;

    private Long address;

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
