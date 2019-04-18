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

public class SuccessBuyTicketAct extends AppCompatActivity implements View.OnClickListener{

    Button btn_view_ticket, btn_my_dasboard;
    Animation app_splash, btn_to_top, ttb;
    ImageView icon_success_ticket;
    TextView app_subtitle, app_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        icon_success_ticket = findViewById(R.id.icon_success_ticket);
        app_subtitle = findViewById(R.id.app_subtitle);
        app_title = findViewById(R.id.app_title);
        btn_my_dasboard = findViewById(R.id.btn_my_dasboard);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btn_to_top = AnimationUtils.loadAnimation(this, R.anim.btn_to_top);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);


        icon_success_ticket.startAnimation(app_splash);
        app_subtitle.startAnimation(ttb);
        app_title.startAnimation(ttb);
        btn_my_dasboard.startAnimation(btn_to_top);
        btn_view_ticket.startAnimation(btn_to_top);


        btn_view_ticket.setOnClickListener(this);
        btn_my_dasboard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_view_ticket:
                Intent gotoMyTicket = new Intent(SuccessBuyTicketAct.this,MyTicketDetailAct.class);
                startActivity(gotoMyTicket);
                break;
            case R.id.btn_my_dasboard:
                Intent gotoDasboard = new Intent(SuccessBuyTicketAct.this,HomeAct.class);
                startActivity(gotoDasboard);
                break;
        }
    }
}
