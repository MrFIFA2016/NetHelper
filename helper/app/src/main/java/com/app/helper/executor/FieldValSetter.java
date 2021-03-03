package com.app.helper.executor;

import com.app.helper.pojo.FieldSetMsg;
import com.app.helper.pojo.Msg;

import java.lang.reflect.Field;

public class FieldValSetter extends AbstractExecutor {

    @Override
    public Object exec(Msg msg) throws Exception {
        try {
            FieldSetMsg msg0 = (FieldSetMsg) msg;
            Class<?> clazz = Class.forName(msg0.getClassName());
            Field field = clazz.getField(msg0.getFieldName());
            field.setAccessible(true);
            Object instance = getInstance(null);
            field.set(instance, msg0.getParam().getValue());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Object getInstance(Object... args) {
        return null;
    }
}
