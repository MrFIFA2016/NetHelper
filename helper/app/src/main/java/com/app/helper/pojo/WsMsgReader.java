package com.app.helper.pojo;


import com.alibaba.fastjson.JSONObject;
import com.app.helper.pojo.WsMsg;

import org.json.JSONTokener;

public class WsMsgReader {

    String text;

    public WsMsgReader(String text) {
        this.text = text;
    }

//    public boolean valid() {
//        JSONTokener jt = new JSONTokener(text);
//        try {
//            JSONObject json = new JSONObject(jt);
//            if (json.has("id") && json.has("sender") && json.has("type") && json.has("msg"))
//                return true;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

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
