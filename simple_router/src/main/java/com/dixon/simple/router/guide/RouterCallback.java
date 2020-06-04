package com.dixon.simple.router.guide;

public interface RouterCallback {

    void onFound();

    void onLost();

    void onArrival();

    void onIntercept(Interceptor interceptor);
}
