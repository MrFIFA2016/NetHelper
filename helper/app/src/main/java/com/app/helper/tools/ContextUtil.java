package com.app.helper.tools;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ContextUtil {

    /**
     * Context对象
     */
    private static Context CONTEXT_INSTANCE;

    private static Object MAINACTIVITY_INSTANCE;

    public static void show() {
        if (MAINACTIVITY_INSTANCE == null) {
            synchronized (ContextUtil.class) {
                if (MAINACTIVITY_INSTANCE == null) {
                    try {
                        Class<?> mainActivity = Class.forName("com.app.networkhelper.MainActivity");
                        Method showInfo = mainActivity.getMethod("showInfo", String.class);
                        showInfo.invoke(mainActivity, "我来自外部dex文件");
                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 取得Context对象
     * PS:必须在主线程调用
     *
     * @return Context
     */
    public static Context getContextReflect() {
        if (CONTEXT_INSTANCE == null) {
            synchronized (ContextUtil.class) {
                if (CONTEXT_INSTANCE == null) {
                    try {
                        Class<?> ActivityThread = Class.forName("android.app.ActivityThread");

                        Method method = ActivityThread.getMethod("currentActivityThread");
                        Object currentActivityThread = method.invoke(ActivityThread);//获取currentActivityThread 对象

                        Method method2 = currentActivityThread.getClass().getMethod("getApplication");
                        CONTEXT_INSTANCE = (Context) method2.invoke(currentActivityThread);//获取 Context对象

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return CONTEXT_INSTANCE;
    }
}