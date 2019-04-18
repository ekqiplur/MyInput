package com.example.ekqi.myinput;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterTwoAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_countinue, btn_plus;
    EditText edit_fullname, edit_status;
    ImageView image_profile;

    DatabaseReference reference;
    StorageReference storage;

    String USER_NAMEKEY = "user_namekey";
    String user_namekey = "";
    String user_namekey_new = "";

    Uri photo_location;
    Integer max_photo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        getUsernameLocal();

        edit_fullname = findViewById(R.id.edit_fullname);
        edit_status = findViewById(R.id.edit_status);
        image_profile = findViewById(R.id.image_profile);
        btn_plus = findViewById(R.id.btn_plus);
        btn_back = findViewById(R.id.btn_back);
        btn_countinue = findViewById(R.id.btn_countinue);

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });

        btn_countinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_countinue.setEnabled(false);
                btn_countinue.setText("Loading...");

                final String fullname = edit_fullname.getText().toString().trim();
                final String status = edit_status.getText().toString().trim();

                boolean isEmptyFields =  false;
                if (TextUtils.isEmpty(fullname)){
                    isEmptyFields = true;
                    edit_fullname.setError("Tidak boleh kosong");
                }

                if (TextUtils.isEmpty(status)){
                    isEmptyFields = true;
                    edit_status.setError("Tidak boleh kosong");
                }

                if (photo_location == null){
                    Toast.makeText(RegisterTwoAct.this, "Foto harus di isi", Toast.LENGTH_SHORT).show();
                    btn_countinue.setEnabled(true);
                    btn_countinue.setText("Continue");
                }

                if (!isEmptyFields){

                    reference = FirebaseDatabase.getInstance().getReference()
                            .child("Users").child(user_namekey_new);

                    storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(user_namekey_new);
                    if (photo_location != null) {
                        StorageReference storageReference = storage.child(System.currentTimeMillis() + "." + getExtension(photo_location));
                        storageReference.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String uri_photo = uri.toString();
                                        reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                        reference.getRef().child("nama_lengkap").setValue(fullname);
                                        reference.getRef().child("status").setValue(status);
                                    }
                                });

                            }
                        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                Intent gotoContinue = new Intent(RegisterTwoAct.this, SuccessRegisterAct.class);
                                gotoContinue.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(gotoContinue);
                                finish();

                            }
                        });
                    }
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

        if (requestCode == max_photo && resultCode == RESULT_OK && data != null && data.getData() !=null ){
            photo_location = data.getData();

            Picasso.get().load(photo_location).fit().centerCrop().into(image_profile);
        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_NAMEKEY,MODE_PRIVATE);
        user_namekey_new = sharedPreferences.getString(user_namekey,"");
    }
}
