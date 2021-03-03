package com.app.helper.to;

import org.json.JSONObject;

public class WsMsg {
    Integer sender;
    String id;
    String type;
    String msg;

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("sender", sender);
            json.put("type", type);
            json.put("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
