package com.dixon.simplerouter;

import android.app.Application;

import com.dixon.simple.router.core.SRouter;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SRouter.init(this);
    }
}
