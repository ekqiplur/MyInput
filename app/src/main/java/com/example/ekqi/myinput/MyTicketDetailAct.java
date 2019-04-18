package com.example.ekqi.myinput;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyTicketDetailAct extends AppCompatActivity {

    TextView text_nama_wisata, text_lokasi,
            text_date_wisata, text_time_wisata,
            text_ketentuan;

    LinearLayout btn_back;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_detail);

        text_nama_wisata = findViewById(R.id.text_nama_wisata);
        text_lokasi = findViewById(R.id.text_lokasi);
        text_date_wisata = findViewById(R.id.text_date_wisata);
        text_time_wisata = findViewById(R.id.text_time_wisata);
        text_ketentuan = findViewById(R.id.text_ketentuan);

        btn_back = findViewById(R.id.btn_back);

        Bundle bundle = getIntent().getExtras();
        final String nama_wisata_new = bundle.getString("nama_wisata");

        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(nama_wisata_new);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                text_nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                text_lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                text_date_wisata.setText(dataSnapshot.child("date_wisata").getValue().toString());
                text_time_wisata.setText(dataSnapshot.child("time_wisata").getValue().toString());
                text_ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
