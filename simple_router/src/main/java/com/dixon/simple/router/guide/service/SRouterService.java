package com.dixon.simple.router.guide.service;

import android.text.TextUtils;

import com.dixon.simple.router.api.SRouterData;
import com.dixon.simple.router.guide.Interceptor;

import java.util.Map;

public class SRouterService implements IService {

    private static volatile SRouterService instance;

    public static SRouterService getInstance() {
        if (instance == null) {
            throw new IllegalArgumentException("You must init SRouterService first");
        }
        return instance;
    }

    public static void init(Map<String, SRouterData> routers) {
        if (instance == null) {
            synchronized (SRouterService.class) {
                instance = new SRouterService(routers);
            }
        }
    }

    private Map<String, SRouterData> routers;

    private SRouterService(Map<String, SRouterData> routers) {
        this.routers = routers;
    }

    @Override
    public Class getRouter(String routerString) {
        SRouterData routerData = routers.get(routerString);
        if (routerData == null) {
            return null;
        }
        String className = routerData.getClazz();
        if (!TextUtils.isEmpty(className)) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Interceptor getInterceptor(String routerString) {
        SRouterData routerData = routers.get(routerString);
        if (routerData == null) {
            return null;
        }
        String interceptor = routerData.getInterceptor();
        if (!TextUtils.isEmpty(interceptor)) {
            try {
                return (Interceptor) Class.forName(interceptor).newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
