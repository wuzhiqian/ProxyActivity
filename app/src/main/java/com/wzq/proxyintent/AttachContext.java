package com.wzq.proxyintent;

import android.app.Instrumentation;
import android.os.Handler;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wzq on 17-1-4.
 */

public class AttachContext {

    public static void attachContextThread() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            currentActivityThreadField.setAccessible(true);
            Object currentActivityThread = currentActivityThreadField.get(null);
        //获取Handler（mH）对象
            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler mH = (Handler) mHField.get(currentActivityThread);
        //修改Handler里面处理启动Activity的方法
            Field mCallBackField = Handler.class.getDeclaredField("mCallback");
            mCallBackField.setAccessible(true);
            mCallBackField.set(mH, new ActivityThreadHandlerCallback(mH));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void attachContext() {
        try {
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);

            Class<?> singleton = Class.forName("android.util.Singleton");
            Field mInstanceField = singleton.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);


            Object rawIActivityManager = mInstanceField.get(gDefault);


            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iActivityManagerInterface}, new IActivityManagerHandler(rawIActivityManager));
            mInstanceField.set(gDefault, proxy);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error occured", e.getMessage());
        }


    }




}
