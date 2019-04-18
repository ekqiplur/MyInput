package com.example.ekqi.myinput;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashAct extends AppCompatActivity {

    Animation app_splash, btn_to_top;
    ImageView app_logo;
    TextView app_subtitle;

    String USER_NAMEKEY = "user_namekey";
    String user_namekey = "";
    String user_namekey_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getUsernameLocal();

        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);
        btn_to_top = AnimationUtils.loadAnimation(this,R.anim.btn_to_top);

        app_logo = findViewById(R.id.app_logo);
        app_subtitle = findViewById(R.id.app_subtitle);

        app_logo.startAnimation(app_splash);
        app_subtitle.startAnimation(btn_to_top);

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_NAMEKEY,MODE_PRIVATE);
        user_namekey_new = sharedPreferences.getString(user_namekey,"");

        if (user_namekey_new.isEmpty()){

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent goStarted = new Intent(SplashAct.this, GetStartedAct.class);
                    startActivity(goStarted);
                    finish();
                }
            },2000);

        }else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent goGetHome = new Intent(SplashAct.this, HomeAct.class);
                    goGetHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(goGetHome);
                    finish();
                }
            },2000);
        }
    }
}
