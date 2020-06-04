package com.dixon.simple.router.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.dixon.simple.router.api.IRouterCreator;
import com.dixon.simple.router.api.SRouterData;
import com.dixon.simple.router.base.core.Constant;
import com.dixon.simple.router.guide.EmptyGuide;
import com.dixon.simple.router.guide.IGuide;
import com.dixon.simple.router.guide.SRouterGuide;
import com.dixon.simple.router.guide.service.SRouterService;
import com.dixon.simple.router.util.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SRouter {

    // 初始化
    public static void init(Application context) {
        Map<String, SRouterData> routers = new HashMap<>();
        List<String> routerCreators = findRouterCreators(context);
        Log.i(Constant.ROUTER_LOG_TAG, "RouterCreators: " + routerCreators);
        for (String routerCreator : routerCreators) {
            try {
                ((IRouterCreator) Class.forName(routerCreator).newInstance()).createRouter(routers);
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        SRouterService.init(routers);
    }

    public static IGuide build(Context context, String router) {
        if (context != null && !TextUtils.isEmpty(router)) {
            return new SRouterGuide(context, router);
        }
        return new EmptyGuide();
    }

    // 通过遍历apk自身的dex文件 找到所有的RouterCreator_xxx.class
    private static List<String> findRouterCreators(Application context) {
        List<String> routerCreators = new ArrayList<>();
        try {
            Set<String> routerAllClass = ClassUtil.getAllClassNameByStartWith(context, Constant.ROUTER_PACKAGE_NAME);
            for (String className : routerAllClass) {
                if (className.startsWith(Constant.ROUTER_PACKAGE_NAME + "." + Constant.ROUTER_CREATOR_PREFIX)) {
                    routerCreators.add(className);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return routerCreators;
    }

    public static void initParams(Activity activity) {
        try {
            Class<?> paramsSet = Class.forName(activity.getClass().getName() + Constant.PARAM_SET_SUFFIX);
            Method initParams = paramsSet.getDeclaredMethod("initParams", Activity.class);
            initParams.invoke(null, activity);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Log.i(Constant.ROUTER_LOG_TAG, "initParams error: " + e.toString());
        }
    }
}
