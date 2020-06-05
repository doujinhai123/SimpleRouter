package com.dixon.simplerouter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dixon.app.comp.Book;
import com.dixon.baselib.LoginStatue;
import com.dixon.simple.router.core.SRouter;
import com.dixon.simple.router.api.SimpleRouter;
import com.dixon.simple.router.guide.Interceptor;
import com.dixon.simple.router.guide.RouterCallback;

@SimpleRouter(value = "main_page", interceptor = "")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startNormal(View view) {
        // 模拟登录状态
        LoginStatue.isLogin = true;
        SRouter.build(this, "second_page")
                .addParams("name", "dixon.xu")
                .addParams("book", new Book("测试标题"))
                .execute();
    }

    public void startIntercept(View view) {
        LoginStatue.isLogin = false;
        SRouter.build(this, "second_page")
                .interceptor(new Interceptor() {
                    @Override
                    public boolean onIntercept(Context context) {
                        Log.e("SimpleRouter", "临时拦截器 不拦截");
                        return false;
                    }
                })
                .callback(new RouterCallback() {
                    @Override
                    public void onFound() {
                        Log.e("SimpleRouter", "Found");
                    }

                    @Override
                    public void onLost() {
                        Log.e("SimpleRouter", "Lost");
                    }

                    @Override
                    public void onArrival() {
                        Log.e("SimpleRouter", "Arrival");
                    }

                    @Override
                    public void onIntercept(Interceptor interceptor) {
                        Log.e("SimpleRouter", "Intercept " + interceptor.getClass().toString());
                    }
                })
                .execute();
    }
}
