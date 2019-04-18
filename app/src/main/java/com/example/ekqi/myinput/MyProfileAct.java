package com.example.ekqi.myinput;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyProfileAct extends AppCompatActivity {

    Button btn_edit_profile, btn_back_home, btn_sign_out;
    LinearLayout item_my_ticket;

    ImageView photo_profile;
    TextView textFullname, textstatus;

    DatabaseReference reference, reference2;

    String USER_NAMEKEY = "user_namekey";
    String user_namekey = "";
    String user_namekey_new = "";

    RecyclerView myticket_place;
    ArrayList<MyTicket>list;
    TikectAdapter tikectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getUsernameLocal();

        photo_profile = findViewById(R.id.photo_profile);
        textFullname = findViewById(R.id.textFullname);
        textstatus = findViewById(R.id.textstatus);

        btn_sign_out = findViewById(R.id.btn_sign_out);
        btn_back_home = findViewById(R.id.btn_back_home);
        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        item_my_ticket = findViewById(R.id.item_my_ticket);

        myticket_place = findViewById(R.id.myticket_place);

        myticket_place.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_namekey_new);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textFullname.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                textstatus.setText(dataSnapshot.child("status").getValue().toString());

                Picasso.get().load(dataSnapshot.child("url_photo_profile").getValue().toString())
                        .centerCrop().fit().into(photo_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoEditProfile = new Intent(MyProfileAct.this,EditProfileAct.class);
                startActivity(gotoEditProfile);
            }
        });

        reference2 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(user_namekey_new);

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    MyTicket p = dataSnapshot1.getValue(MyTicket.class);
                    list.add(p);
                }
                tikectAdapter = new TikectAdapter(MyProfileAct.this, list);
                myticket_place.setAdapter(tikectAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(USER_NAMEKEY,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(user_namekey, null);
                editor.apply();

                Intent gotoSign = new Intent(MyProfileAct.this,SignInAct.class);
                gotoSign.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(gotoSign);
                finish();
            }
        });

        btn_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_NAMEKEY,MODE_PRIVATE);
        user_namekey_new = sharedPreferences.getString(user_namekey,"");
    }
}
