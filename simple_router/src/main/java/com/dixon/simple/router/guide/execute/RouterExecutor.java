package com.dixon.simple.router.guide.execute;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dixon.simple.router.base.core.Constant;
import com.dixon.simple.router.guide.Interceptor;
import com.dixon.simple.router.guide.RouterCallback;
import com.dixon.simple.router.guide.service.SRouterService;

public class RouterExecutor implements Executor, RouterCallback {

    private static final int REQUEST_CODE_NONE = -1;

    private Context context;
    private String router;
    private Bundle bundle; // 参数合集
    private RouterCallback callback; // 路由监控
    private Interceptor tempInterceptor; // 临时跳转拦截器
    private int requestCode = REQUEST_CODE_NONE;

    public RouterExecutor(Context context, String router) {
        this.context = context;
        this.router = router;
    }

    public RouterExecutor(Context context, String router, Bundle bundle, RouterCallback callback, Interceptor tempInterceptor) {
        this.context = context;
        this.router = router;
        this.bundle = bundle;
        this.callback = callback;
        this.tempInterceptor = tempInterceptor;
    }

    public RouterExecutor(Context context, String router, Bundle bundle, RouterCallback callback, Interceptor tempInterceptor, int requestCode) {
        this.context = context;
        this.router = router;
        this.bundle = bundle;
        this.callback = callback;
        this.tempInterceptor = tempInterceptor;
        this.requestCode = requestCode;
    }

    @Override
    public void execute() {
        Class goClass = SRouterService.getInstance().getRouter(router);
        if (goClass == null) {
            onLost();
            return;
        }
        onFound();
        if (!intercept()) {
            realStartActivity(goClass);
            onArrival();
        }
    }

    private boolean intercept() {
        if (tempInterceptor != null && tempInterceptor.onIntercept(context)) {
            // 临时拦截器拦截掉事件
            onIntercept(tempInterceptor);
            return true;
        }
        Interceptor interceptor = SRouterService.getInstance().getInterceptor(router);
        if (interceptor != null && interceptor.onIntercept(context)) {
            // 通用拦截器拦截掉事件
            onIntercept(interceptor);
            return true;
        }
        return false;
    }

    private void realStartActivity(Class goClass) {
        Intent intent = new Intent(context, goClass);
        if (bundle != null) {
            intent.putExtra(Constant.ROUTER_BUNDLE_NAME, bundle);
        }
        // application需要设置该属性
        if (context instanceof Application) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (requestCode != REQUEST_CODE_NONE && context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, requestCode);
        } else {
            context.startActivity(intent);
        }
    }

    @Override
    public void onFound() {
        if (callback != null) {
            callback.onFound();
        }
    }

    @Override
    public void onLost() {
        if (callback != null) {
            callback.onLost();
        }
    }

    @Override
    public void onArrival() {
        if (callback != null) {
            callback.onArrival();
        }
    }

    @Override
    public void onIntercept(Interceptor interceptor) {
        if (callback != null) {
            callback.onIntercept(interceptor);
        }
    }
}
