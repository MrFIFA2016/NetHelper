package com.gshell.server.pojo;

import com.alibaba.fastjson.JSONObject;

public class NormalMsg implements Msg, MsgResolver {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Msg resolveMsg(WsMsg msg) {
        return JSONObject.toJavaObject(msg.getMsg(), NormalMsg.class);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this,true);
    }
}
