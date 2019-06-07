package com.goma.admin.ormoccity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//import application.goma.admin.ormoccity.R;

public class Splash extends AppCompatActivity {
//    TextView version;
//    int versionCode = BuildConfig.VERSION_CODE;
//    String versionName = BuildConfig.VERSION_NAME;
    private int SPLASH_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        version = (TextView) findViewById(R.id.txtVersion);
//        version.setText("Version "+versionName);



        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(SPLASH_TIME);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(Splash.this,Home.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();

    }
}
