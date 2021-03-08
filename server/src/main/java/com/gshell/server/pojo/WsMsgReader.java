package com.gshell.server.pojo;


import com.alibaba.fastjson.JSONObject;

public class WsMsgReader {

    String text;

    public WsMsgReader(String text) {
        this.text = text;
    }

    public WsMsg read() {
        WsMsg msg = new WsMsg();
        try {
            JSONObject json = JSONObject.parseObject(text);
            msg.setId(json.getString("id"));
            msg.setSender(json.getInteger("sender"));
            msg.setType(json.getString("type"));
            msg.setMsg(json.getJSONObject("msg"));
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("msg", text);
            msg.setMsg(json);
        }
        return msg;
    }
}
