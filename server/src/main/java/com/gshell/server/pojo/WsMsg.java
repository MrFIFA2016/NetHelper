package com.gshell.server.pojo;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class WsMsg {
    Integer sender;
    String id;
    String type;
    JSONObject msg;

    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public enum MsgType {
        EXEC("exec"),
        SET_FIELD("setField");

        String type;

        MsgType(String type) {
            this.type = type;
        }


    }
}
