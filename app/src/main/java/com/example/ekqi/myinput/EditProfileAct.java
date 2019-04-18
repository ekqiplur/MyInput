package com.example.ekqi.myinput;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfileAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_save_profile, btn_plus;
    EditText edit_fullname, edit_status, edit_email,
            edit_password, edit_user_name;
    ImageView photo_profile;

    String USER_NAMEKEY = "user_namekey";
    String user_namekey = "";
    String user_namekey_new = "";

    DatabaseReference reference;
    StorageReference storage;

    Uri photo_location;
    Integer max_photo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getUsernameLocal();

        edit_fullname = findViewById(R.id.edit_fullname);
        edit_status = findViewById(R.id.edit_status);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        edit_user_name = findViewById(R.id.edit_user_name);

        photo_profile = findViewById(R.id.photo_profile);

        btn_save_profile = findViewById(R.id.btn_save_profile);
        btn_plus = findViewById(R.id.btn_plus);
        btn_back = findViewById(R.id.btn_back);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_namekey_new);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                edit_fullname.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                edit_status.setText(dataSnapshot.child("status").getValue().toString());
                edit_email.setText(dataSnapshot.child("email").getValue().toString());
                edit_password.setText(dataSnapshot.child("password").getValue().toString());
                edit_user_name.setText(dataSnapshot.child("username").getValue().toString());

                Picasso.get().load(dataSnapshot.child("url_photo_profile").getValue().toString())
                        .centerCrop().fit().into(photo_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });

        btn_save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_save_profile.setEnabled(false);
                btn_save_profile.setText("Please Wait...");

                storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(user_namekey_new);
                if (photo_location != null){
                    final StorageReference storageReference = storage.child(System.currentTimeMillis()+ "." +getExtension(photo_location));
                    storageReference.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String uri_photo = uri.toString();
                                    reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                    reference.getRef().child("nama_lengkap").setValue(edit_fullname.getText().toString());
                                    reference.getRef().child("status").setValue(edit_status.getText().toString());
                                    reference.getRef().child("password").setValue(edit_password.getText().toString());
                                    reference.getRef().child("username").setValue(edit_user_name.getText().toString());
                                    reference.getRef().child("email").setValue(edit_email.getText().toString());
                                }
                            });
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Intent gotoDasboard = new Intent(EditProfileAct.this,HomeAct.class);
                            startActivity(gotoDasboard);
                        }
                    });
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    String getExtension(Uri uri){
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri));
    }

    private void selectPhoto() {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic,max_photo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == max_photo && resultCode == RESULT_OK && data != null && data.getData() != null){
            photo_location = data.getData();

            Picasso.get().load(photo_location).centerCrop().fit().into(photo_profile);
        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_NAMEKEY,MODE_PRIVATE);
        user_namekey_new = sharedPreferences.getString(user_namekey,"");
    }
}
