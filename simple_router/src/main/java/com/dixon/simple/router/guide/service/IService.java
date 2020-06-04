package com.dixon.simple.router.guide.service;

import com.dixon.simple.router.guide.Interceptor;

public interface IService {

    Class getRouter(String routerString);

    Interceptor getInterceptor(String routerString);
}
