package com.wzq.proxyintent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView text;
    private TextView text1;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        AttachContext.attachContext();
        AttachContext.attachContextThread();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("key", "text1");
                Intent intent = new Intent(MainActivity.this, TestActivity1.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        text1 = (TextView) findViewById(R.id.text1);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("key", "text2");
                Intent intent = new Intent(MainActivity.this, TestActivity2.class);
                intent.putExtras(bundle);;
                startActivity(intent);
            }
        });
    }
}
