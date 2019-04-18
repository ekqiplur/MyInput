package com.example.ekqi.myinput;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class HomeAct extends AppCompatActivity implements View.OnClickListener{

    LinearLayout btn_ticket_pisa, btn_ticket_torri, btn_ticket_pagoda, btn_ticket_candi
            ,btn_ticket_spinx ,btn_ticket_monas;
    CircleView btn_to_profile;
    ImageView photo_user;
    TextView textFullname, textstatus, textbalance;

    String USER_NAMEKEY = "user_namekey";
    String user_namekey = "";
    String user_namekey_new = "";

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        photo_user = findViewById(R.id.photo_user);
        textFullname = findViewById(R.id.textFullname);
        textstatus = findViewById(R.id.textstatus);
        textbalance = findViewById(R.id.textbalance);

        btn_ticket_torri = findViewById(R.id.btn_ticket_torri);
        btn_ticket_pagoda = findViewById(R.id.btn_ticket_pagoda);
        btn_ticket_candi = findViewById(R.id.btn_ticket_candi);
        btn_ticket_spinx = findViewById(R.id.btn_ticket_spinx);
        btn_ticket_monas = findViewById(R.id.btn_ticket_monas);
        btn_ticket_pisa = findViewById(R.id.btn_ticket_pisa);
        btn_to_profile = findViewById(R.id.btn_to_profile);

        btn_ticket_pisa.setOnClickListener(this);
        btn_ticket_torri.setOnClickListener(this);
        btn_ticket_pagoda.setOnClickListener(this);
        btn_ticket_candi.setOnClickListener(this);
        btn_ticket_spinx.setOnClickListener(this);
        btn_ticket_monas.setOnClickListener(this);
        btn_to_profile.setOnClickListener(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_namekey_new);

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                textFullname.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                textstatus.setText(dataSnapshot.child("status").getValue().toString());
                textbalance.setText("US$ "+dataSnapshot.child("user_balance").getValue().toString());

                Picasso.get().load(dataSnapshot.child("url_photo_profile").getValue().toString())
                        .centerCrop().fit().into(photo_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ticket_pisa:
                Intent gotoTicketPisa = new Intent(getApplicationContext(),TiketDetailAct.class);
                gotoTicketPisa.putExtra("jenis_tiket","Pisa");
                startActivity(gotoTicketPisa);
                break;

            case R.id.btn_ticket_torri:
                Intent gotoTicketTorri = new Intent(getApplicationContext(),TiketDetailAct.class);
                gotoTicketTorri.putExtra("jenis_tiket","Torri");
                startActivity(gotoTicketTorri);
                break;

            case R.id.btn_ticket_pagoda:
                Intent gotoTicketPagoda = new Intent(getApplicationContext(),TiketDetailAct.class);
                gotoTicketPagoda.putExtra("jenis_tiket","Pagoda");
                startActivity(gotoTicketPagoda);
                break;

            case R.id.btn_ticket_candi:
                Intent gotoTicketCandi = new Intent(getApplicationContext(),TiketDetailAct.class);
                gotoTicketCandi.putExtra("jenis_tiket","Candi");
                startActivity(gotoTicketCandi);
                break;

            case R.id.btn_ticket_spinx:
                Intent gotoTicketSpinx= new Intent(getApplicationContext(),TiketDetailAct.class);
                gotoTicketSpinx.putExtra("jenis_tiket","Spinx");
                startActivity(gotoTicketSpinx);
                break;

            case R.id.btn_ticket_monas:
                Intent gotoTicketMonas= new Intent(getApplicationContext(),TiketDetailAct.class);
                gotoTicketMonas.putExtra("jenis_tiket","Monas");
                startActivity(gotoTicketMonas);
                break;

            case R.id.btn_to_profile:
                Intent gotoProfle = new Intent(HomeAct.this,MyProfileAct.class);
                startActivity(gotoProfle);
                break;
        }

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_NAMEKEY,MODE_PRIVATE);
        user_namekey_new = sharedPreferences.getString(user_namekey,"");
    }
}
