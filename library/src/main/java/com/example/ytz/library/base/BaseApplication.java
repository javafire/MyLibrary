package com.example.ytz.library.base;

import android.app.Application;

import org.xutils.x;

/**
 * Created by admin on 2016/10/21.
 */

public abstract class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initXutils();
        initTitleBar();
        initOther();
    }

    protected abstract void initTitleBar();

    public abstract boolean isDebug();
    public abstract void initOther();

    private void initXutils() {
        x.Ext.init(this);
        x.Ext.setDebug(isDebug());
    }
}
