package com.app.helper.tools;

import android.util.Log;
import android.widget.Toast;

import com.app.helper.executor.AbstractExecutor;
import com.app.helper.executor.FieldValSetter;
import com.app.helper.executor.FuncExecutor;
import com.app.helper.pojo.FieldSetMsg;
import com.app.helper.pojo.FuncExecMsg;
import com.app.helper.pojo.Msg;
import com.app.helper.pojo.NormalMsg;
import com.app.helper.pojo.WsMsg;

public class MsgHandler {

    public static void handleMsg(WsMsg message, MsgHandler.CallBack callBack) {
        Msg msg = null;
        AbstractExecutor executor = null;
        if (WsMsg.MsgType.EXEC.equalsType(message.getType())) {
            msg = new FuncExecMsg().resolveMsg(message);
            executor = new FuncExecutor();
        } else if (WsMsg.MsgType.SET_FIELD.equals(message.getType())) {
            msg = new FieldSetMsg().resolveMsg(message);
            executor = new FieldValSetter();
        } else {
            msg = new NormalMsg().resolveMsg(message);

            executor = new AbstractExecutor() {
                @Override
                public Object exec(Msg msg) throws Exception {
                    Log.v("BootStrap", msg.toString());
                    Toast.makeText(ContextUtil.getContextReflect(), msg.toString(), Toast.LENGTH_LONG).show();
                    return null;
                }

                @Override
                public Object getInstance(Object... args) {
                    return null;
                }
            };

        }
        try {
            Object result = executor.exec(msg);
            callBack.onComplated(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CallBack {
        void onComplated(String content);
    }
}
