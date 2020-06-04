package com.dixon.simple.router.api;

public class SRouterData {

    private String clazz;
    private String interceptor;

    public SRouterData(String clazz, String interceptor) {
        this.clazz = clazz;
        this.interceptor = interceptor;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public String toString() {
        return "SRouterData{" +
                "clazz='" + clazz + '\'' +
                ", interceptor='" + interceptor + '\'' +
                '}';
    }
}
