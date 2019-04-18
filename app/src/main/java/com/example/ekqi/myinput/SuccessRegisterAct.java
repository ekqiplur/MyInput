package com.example.ekqi.myinput;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessRegisterAct extends AppCompatActivity {

    Button btn_explore;
    ImageView icon_success;
    TextView app_subtitle, app_title;
    Animation app_splash, btn_to_top, ttb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        icon_success = findViewById(R.id.icon_success);
        app_subtitle = findViewById(R.id.app_subtitle);
        app_title = findViewById(R.id.app_title);

        btn_explore = findViewById(R.id.btn_explore);

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btn_to_top = AnimationUtils.loadAnimation(this, R.anim.btn_to_top);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        icon_success.startAnimation(app_splash);
        btn_explore.startAnimation(btn_to_top);
        app_subtitle.startAnimation(ttb);
        app_title.startAnimation(ttb);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHome = new Intent(SuccessRegisterAct.this, HomeAct.class);
                gotoHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(gotoHome);
            }
        });
    }
}
