package com.wzq.proxyintent;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wzq on 17-1-4.
 */
public class IActivityManagerHandler implements InvocationHandler {

    Object mBase;
    public IActivityManagerHandler(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().contains("startActivity")) {
            Intent raw;
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }
            //获取原来的Intent
            raw = (Intent) args[index];
            //构造新的Intent
            Intent newIntent = new Intent();
            String stubPackage = getClass().getPackage().getName();
            ComponentName componentName = new ComponentName(stubPackage, ProxyActivity.class.getName());
            newIntent.setComponent(componentName);
            //保存原来的Intent，后面恢复要用
            newIntent.putExtra("rawIntent", raw);
            args[index] = newIntent;
            return method.invoke(mBase, args);
        }
        return method.invoke(mBase, args);
    }
}