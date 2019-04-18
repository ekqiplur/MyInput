package com.example.ekqi.myinput;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_countinue;
    EditText edit_user_name, edit_password, edit_email;

    DatabaseReference reference, reference_user;

    String USER_NAMEKEY = "user_namekey";
    String user_namekey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        edit_user_name = findViewById(R.id.edit_user_name);
        edit_password = findViewById(R.id.edit_password);
        edit_email = findViewById(R.id.edit_email);
        btn_back = findViewById(R.id.btn_back);
        btn_countinue = findViewById(R.id.btn_countinue);

        btn_countinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_countinue.setEnabled(false);
                btn_countinue.setText("Loading...");

                reference_user = FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(edit_user_name.getText().toString().trim());

                reference_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            Toast.makeText(RegisterOneAct.this, "Username sudah terdaftar", Toast.LENGTH_SHORT).show();
                            btn_countinue.setEnabled(true);
                            btn_countinue.setText("Continue");

                        } else {

                            SharedPreferences sharedPreferences = getSharedPreferences(USER_NAMEKEY,MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(user_namekey, edit_user_name.getText().toString().trim());
                            editor.apply();

                            final String user = edit_user_name.getText().toString().trim();
                            final String pass = edit_password.getText().toString().trim();
                            final String email = edit_email.getText().toString().trim();

                            boolean isEmptyFields = false;

                            if (TextUtils.isEmpty(user)){
                                isEmptyFields = true;
                                edit_user_name.setError("Tidak boleh kosong");
                            }

                            if (TextUtils.isEmpty(pass)){
                                isEmptyFields = true;
                                edit_password.setError("Tidak boleh kosong");
                            }

                            if (TextUtils.isEmpty(email)){
                                isEmptyFields = true;
                                edit_email.setError("Tidak boleh kosong");
                            }

                            if (!isEmptyFields) {
                                reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(edit_user_name.getText().toString().trim());

                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        dataSnapshot.getRef().child("username").setValue(user);
                                        dataSnapshot.getRef().child("password").setValue(pass);
                                        dataSnapshot.getRef().child("email").setValue(email);
                                        dataSnapshot.getRef().child("user_balance").setValue(800);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                Intent gotoContinue = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                                startActivity(gotoContinue);
                            }

                        }
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

}
