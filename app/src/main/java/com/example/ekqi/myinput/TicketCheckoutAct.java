package com.example.ekqi.myinput;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {

    Button btn_buy_ticket, btn_minus, btn_plus;

    LinearLayout btn_back;

    TextView text_jumlah_Ticket, text_my_balance, text_total_harga,
            text_nama_wisata, lokasi_wisata, ketentuan_wisata;
    Integer jumlah_ticket = 1;
    Integer mybelance = 0;
    Integer totalharga = 0;
    Integer hargaticket = 0;
    ImageView notice_uang;

    DatabaseReference reference, reference2, reference3, reference4;

    String USER_NAMEKEY = "user_namekey";
    String user_namekey = "";
    String user_namekey_new = "";

    Integer nomer_transaksi = new Random().nextInt();

     String date_wisata = "";
     String time_wisata = "";

     Integer sisa_balance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_check_out);

        Bundle bundle = getIntent().getExtras();
        String jenis_tiket_baru = bundle.getString("jenis_tiket");
        getUsernameLocal();

        text_nama_wisata = findViewById(R.id.text_nama_wisata);
        lokasi_wisata = findViewById(R.id.lokasi_wisata);
        ketentuan_wisata = findViewById(R.id.ketentuan_wisata);
        text_jumlah_Ticket = findViewById(R.id.text_jumlah_Ticket);
        text_total_harga = findViewById(R.id.text_total_harga);
        text_my_balance = findViewById(R.id.text_my_balance);

        notice_uang = findViewById(R.id.notice_uang);

        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        btn_minus = findViewById(R.id.btn_minus);
        btn_plus = findViewById(R.id.btn_plus);

        btn_back = findViewById(R.id.btn_back);

        text_jumlah_Ticket.setText(jumlah_ticket.toString());

        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(user_namekey_new);

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mybelance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                text_my_balance.setText("US$ "+mybelance+"");

                if (mybelance < hargaticket){
                    btn_buy_ticket.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_buy_ticket.setEnabled(false);
                    text_my_balance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                text_nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi_wisata.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan_wisata.setText(dataSnapshot.child("ketentuan").getValue().toString());
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();
                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();

                hargaticket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                totalharga = hargaticket * jumlah_ticket;
                text_total_harga.setText("US$ "+totalharga+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah_ticket += 1;
                text_jumlah_Ticket.setText(jumlah_ticket.toString());

                if (jumlah_ticket > 1){
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);
                }

                totalharga = hargaticket * jumlah_ticket;
                text_total_harga.setText("US$ "+totalharga+"");
                if (totalharga > mybelance){
                    btn_buy_ticket.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_buy_ticket.setEnabled(false);
                    text_my_balance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah_ticket -= 1;
                text_jumlah_Ticket.setText(jumlah_ticket.toString());

                if (jumlah_ticket < 2){
                    btn_minus.animate().alpha(0).setDuration(300).start();
                    btn_minus.setEnabled(false);
                }

                totalharga = hargaticket * jumlah_ticket;
                text_total_harga.setText("US$ "+totalharga+"");
                if (totalharga < mybelance){
                    btn_buy_ticket.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_buy_ticket.setEnabled(true);
                    text_my_balance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);
                }
            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets")
                        .child(user_namekey_new).child(text_nama_wisata.getText().toString() + nomer_transaksi);
                reference3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        reference3.getRef().child("id_tiket").setValue(text_nama_wisata.getText().toString() + nomer_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(text_nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi_wisata.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan_wisata.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(jumlah_ticket);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);
                        reference3.getRef().child("date_wisata").setValue(date_wisata);


                        Intent gotoSuccessTicket = new Intent(TicketCheckoutAct.this,SuccessBuyTicketAct.class);
                        gotoSuccessTicket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoSuccessTicket);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(user_namekey_new);

                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = mybelance - totalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
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
