package com.app.helper.pojo;

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
        SET_FIELD("setField"),
        CLIENT_LOG("clientLog"),
        DATA_RECORD("dataRecord");

        String type;

        MsgType(String type) {
            this.type = type;
        }

        public boolean equalsType(String type) {
            return this.type.equalsIgnoreCase(type);
        }
    }
}
