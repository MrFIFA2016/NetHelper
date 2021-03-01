package com.app.networkhelper.to;

import org.json.JSONException;
import org.json.JSONObject;


public class WsReq implements ReqId {
    String reqId;
    Object msg;

    @Override
    public String getReqId() {
        return reqId;
    }

    @Override
    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getMsg() {
        return this.msg;
    }

    public String toJsonString() {
        JSONObject json = new JSONObject();
        try {
            json.put("reqId", reqId);
            json.put("msg", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
