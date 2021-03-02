package com.app.networkhelper.tools;

import com.app.networkhelper.to.WsMsg;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class WsMsgReader {

    String text;

    public WsMsgReader(String text) {
        this.text = text;
    }

    public boolean valid() {
        JSONTokener jt = new JSONTokener(text);
        try {
            JSONObject json = new JSONObject(jt);
            if (json.has("id") && json.has("sender") && json.has("type") && json.has("msg"))
                return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public WsMsg read() {
        JSONTokener jt = new JSONTokener(text);
        WsMsg msg = new WsMsg();
        try {
            JSONObject json = new JSONObject(jt);
            msg.setId(json.getString("id"));
            msg.setSender(json.getInt("sender"));
            msg.setType(json.getString("type"));
            msg.setMsg(json.getString("msg"));
            return msg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        msg.setMsg(text);
        return msg;
    }
}
