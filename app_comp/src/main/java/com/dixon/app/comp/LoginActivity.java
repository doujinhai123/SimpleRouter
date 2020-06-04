package com.dixon.app.comp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dixon.simple.router.api.SimpleRouter;

@SimpleRouter(value = "login_page", interceptor = "")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
