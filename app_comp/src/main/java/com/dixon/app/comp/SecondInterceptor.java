package com.dixon.app.comp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dixon.baselib.LoginStatue;
import com.dixon.simple.router.core.SRouter;
import com.dixon.simple.router.guide.Interceptor;

public class SecondInterceptor implements Interceptor {

    @Override
    public boolean onIntercept(Context context) {
        // 模拟登陆判断
        if (!LoginStatue.isLogin) {
            Toast.makeText(context, "未登陆，跳转登陆页", Toast.LENGTH_SHORT).show();
            SRouter.build(context, "login_page").execute();
            Log.e("SimpleRouter", "通用拦截器 拦截");
            return true;
        }
        Log.e("SimpleRouter", "通用拦截器 不拦截");
        return false;
    }
}
