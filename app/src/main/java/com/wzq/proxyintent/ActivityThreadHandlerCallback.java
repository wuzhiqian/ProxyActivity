package com.wzq.proxyintent;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;

/**
 * Created by wzq on 17-1-4.
 */
public class ActivityThreadHandlerCallback implements Handler.Callback {
    Handler mBase;

    public ActivityThreadHandlerCallback(Handler base) {
        mBase = base;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            // ActivityThread里面 "LAUNCH_ACTIVITY" 这个字段的值是100
            case 100:
                handleLaunchActivity(msg);
                break;
        }
        mBase.handleMessage(msg);
        return true;
    }

    private void handleLaunchActivity(Message msg) {
        Object obj = msg.obj;
        try {
            Field intent = obj.getClass().getDeclaredField("intent");
            intent.setAccessible(true);
            Intent raw = (Intent) intent.get(obj);
            Intent target = raw.getParcelableExtra("rawIntent");
            raw.setComponent(target.getComponent());
        } catch (Exception e) {
            throw new RuntimeException("hook launch activity failed", e);
        }
    }
}