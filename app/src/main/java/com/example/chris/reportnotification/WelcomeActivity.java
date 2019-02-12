package com.example.chris.reportnotification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import utilities.PrefUtil;

public class WelcomeActivity extends AppCompatActivity {

    private Handler handler;
    private SharedPreferences shared;
    private String userId;
    private PrefUtil pref;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        pref = new PrefUtil(this);
        try{
            shared  = pref.getUserInfo();
            userId  = shared.getString(PrefUtil.ID, null);
        }catch (Exception e){e.getMessage();}
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                intent=new Intent(WelcomeActivity.this, MainActivity.class);
                /*if(userId==null){
                    intent=new Intent(WelcomeActivity.this, LoginActivity.class);
                }else{
                    intent=new Intent(WelcomeActivity.this, MainActivity.class);
                }*/
                startActivity(intent);
                finish();
            }
        },500);
    }
}
