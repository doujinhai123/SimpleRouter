package com.dixon.app.comp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.dixon.simple.router.api.SimpleParam;
import com.dixon.simple.router.api.SimpleRouter;
import com.dixon.simple.router.core.SRouter;

@SimpleRouter(value = "second_page", interceptor = "com.dixon.app.comp.SecondInterceptor")
public class SecondActivity extends AppCompatActivity {

    @SimpleParam("name")
    String mName;

    @SimpleParam("book")
    Book mBook;

    private TextView mNameView, mBookView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        SRouter.initParams(this);

        mNameView.setText(mName);
        if (mBook != null) {
            mBookView.setText(mBook.getTitle());
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mBookView = findViewById(R.id.tvBook);
        mNameView = findViewById(R.id.tvName);
    }
}
