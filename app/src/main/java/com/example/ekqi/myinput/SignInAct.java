package com.example.ekqi.myinput;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInAct extends AppCompatActivity {

    TextView btn_new_account;
    Button btn_sign_in;
    EditText edit_password, edit_user_name;

    DatabaseReference reference;

    String USER_NAMEKEY = "user_namekey";
    String user_namekey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edit_password = findViewById(R.id.edit_password);
        edit_user_name = findViewById(R.id.edit_user_name);

        btn_new_account = findViewById(R.id.btn_new_account);
        btn_sign_in = findViewById(R.id.btn_sign_in);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("Please Wait...");

                final String user = edit_user_name.getText().toString().trim();
                final String pass = edit_password.getText().toString().trim();

                if (user.isEmpty()){
                    Toast.makeText(SignInAct.this, "Username kosong", Toast.LENGTH_SHORT).show();
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("Sign In");

                }else {
                    if (pass.isEmpty()){
                        Toast.makeText(SignInAct.this, "Password kosong", Toast.LENGTH_SHORT).show();
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("Sign In");
                    }else {
                        reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(user);

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String passwordFirebase = dataSnapshot.child("password").getValue().toString();
                                    if (pass.equals(passwordFirebase)) {

                                        SharedPreferences sharedPreferences = getSharedPreferences(USER_NAMEKEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(user_namekey, user);
                                        editor.apply();

                                        Intent gotoHome = new Intent(SignInAct.this, HomeAct.class);
                                        gotoHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(gotoHome);
                                        finish();
                                    } else {
                                        Toast.makeText(SignInAct.this, "Password salah", Toast.LENGTH_SHORT).show();
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("Sign In");
                                    }
                                } else {
                                    Toast.makeText(SignInAct.this, "Username tidak ada", Toast.LENGTH_SHORT).show();
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("Sign In");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoRegisterOne = new Intent(SignInAct.this,RegisterOneAct.class);
                gotoRegisterOne.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(gotoRegisterOne);
            }
        });
    }
}
