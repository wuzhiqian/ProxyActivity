package com.wzq.proxyintent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by wzq on 17-1-4.
 */

public class TestActivity1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
      //  Log.e("test1", getIntent().getStringExtra("key"));
    }


    public void back(View view)
    {
        finish();
    }
}
