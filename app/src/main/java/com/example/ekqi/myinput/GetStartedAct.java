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

public class GetStartedAct extends AppCompatActivity {

    Animation ttb, btn_to_top;
    ImageView emblem_app;
    TextView intro_app;
    Button btn_sign_in, btn_new_account_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);
        btn_to_top = AnimationUtils.loadAnimation(this,R.anim.btn_to_top);

        emblem_app = findViewById(R.id.emblem_app);
        intro_app = findViewById(R.id.intro_app);

        emblem_app.startAnimation(ttb);
        intro_app.startAnimation(ttb);

        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_new_account_create = findViewById(R.id.btn_new_account_create);

        btn_sign_in.startAnimation(btn_to_top);
        btn_new_account_create.startAnimation(btn_to_top);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoSign = new Intent(GetStartedAct.this,SignInAct.class);
                startActivity(gotoSign);
            }
        });

        btn_new_account_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoRegisterOne = new Intent(GetStartedAct.this,RegisterOneAct.class);
                startActivity(gotoRegisterOne);
            }
        });
    }
}
