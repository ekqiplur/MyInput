package com.example.ekqi.myinput;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class TiketDetailAct extends AppCompatActivity {

    Button btn_buy_ticket;
    TextView title_ticket, location_ticket,
            photo_spot_ticket, wifi_ticket,
            festival_ticket, description_ticked;
    ImageView header_tiket;
    LinearLayout btn_back;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_detail);

        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        photo_spot_ticket = findViewById(R.id.photo_spot_ticket);
        wifi_ticket = findViewById(R.id.wifi_ticket);
        festival_ticket = findViewById(R.id.festival_ticket);
        description_ticked = findViewById(R.id.description_ticked);
        location_ticket = findViewById(R.id.location_ticket);
        header_tiket = findViewById(R.id.header_tiket);
        title_ticket = findViewById(R.id.title_ticket);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);

        btn_back = findViewById(R.id.btn_back);

        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                photo_spot_ticket.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                wifi_ticket.setText(dataSnapshot.child("is_wifi").getValue().toString());
                festival_ticket.setText(dataSnapshot.child("is_festival").getValue().toString());
                description_ticked.setText(dataSnapshot.child("short_desc").getValue().toString());
                location_ticket.setText(dataSnapshot.child("lokasi").getValue().toString());
                title_ticket.setText(dataSnapshot.child("nama_wisata").getValue().toString());

                Picasso.get().load(dataSnapshot.child("url_thumbnail").getValue().toString())
                        .centerCrop().fit().into(header_tiket);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCheckout = new Intent(TiketDetailAct.this,TicketCheckoutAct.class);
                gotoCheckout.putExtra("jenis_tiket", jenis_tiket_baru);
                startActivity(gotoCheckout);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
