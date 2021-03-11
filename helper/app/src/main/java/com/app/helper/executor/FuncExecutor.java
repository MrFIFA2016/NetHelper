package com.app.helper.executor;

import com.app.helper.hook.util.HookUtil;
import com.app.helper.pojo.FuncExecMsg;
import com.app.helper.pojo.Msg;
import com.app.helper.pojo.Param;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

public class FuncExecutor extends AbstractExecutor {

    public Object exec(Msg msg) throws Exception {
        FuncExecMsg msg0 = (FuncExecMsg) msg;
        Class<?> clazz = Class.forName(msg0.getSignature());
        List<Param> params = msg0.getParams();
        Collections.sort(params);
        Class[] types = new Class[params.size()];
        Object[] args = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            args[i] = params.get(i).getValue();
            types[i] = params.get(i).getPrimitiveClass();
        }
        Method method = clazz.getMethod(msg0.getMethod(), types);
        method.setAccessible(true);

        boolean isStatic = Modifier.isStatic(method.getModifiers());
        Object instance;
        if (isStatic) {
            instance = null;
        } else if (msg0.getNewInstance()) {
            instance = clazz.newInstance();
        } else {
            instance = getInstance(msg0.getAddress());
        }
        if (args.length == 0)
            return method.invoke(instance);

        return method.invoke(instance, args);
    }

    @Override
    public Object getInstance(Object... args) {
        return HookUtil.getObject((Long) args[0]);
    }
}
