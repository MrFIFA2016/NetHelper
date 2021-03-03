package com.app.helper.executor;

import com.app.helper.pojo.Msg;

public abstract class AbstractExecutor {

    public abstract Object exec(Msg msg) throws Exception;

    public abstract Object getInstance(Object... args);
}
