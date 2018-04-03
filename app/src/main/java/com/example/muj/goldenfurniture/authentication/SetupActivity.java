package com.example.muj.goldenfurniture.authentication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.muj.goldenfurniture.Manifest;
import com.example.muj.goldenfurniture.R;
import com.example.muj.goldenfurniture.database.Customer;
import com.example.muj.goldenfurniture.furniture;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity
{
    private CircleImageView setupImage;
    private Uri mainImageUri = null;

    private Button submitButton;
    private EditText displayName;
    private ProgressBar setupprogress;
    private EditText phoneNumber;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    private DatabaseReference databaseReference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_user);
        firebaseAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users");

        setupImage = findViewById(R.id.profile_image);
        submitButton = findViewById(R.id.button_submit);
        displayName = findViewById(R.id.edit_text_display_name);
        setupprogress = findViewById(R.id.setup_progress);
        phoneNumber = findViewById(R.id.edit_text_phone_number);

        setupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(ContextCompat.checkSelfPermission(SetupActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(SetupActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(SetupActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE} ,1);
                    }
                    else
                    {
                        bringImagePicker();
                    }
                }
                else
                {
                    bringImagePicker();
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user_name = displayName.getText().toString().trim();
                final String phone_number = phoneNumber.getText().toString();
                final String user_id = firebaseAuth.getCurrentUser().getUid();
                final String email = firebaseAuth.getCurrentUser().getEmail();
                setupprogress.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(phone_number))
                {
                    if (mainImageUri != null)
                    {
                        StorageReference image_path = mStorageRef.child("profile_images").child(user_id + ".jpg");
                        image_path.putFile(mainImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {
                                    Uri download_uri = task.getResult().getDownloadUrl();
                                    Customer customer = new Customer(user_id, phone_number, email, user_name, download_uri.toString());
                                    databaseReference.push().setValue(customer);
                                    Toast.makeText(SetupActivity.this, "The details are uploaded ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SetupActivity.this, furniture.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(SetupActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                                }
                                setupprogress.setVisibility(View.INVISIBLE);

                            }
                        });
                    }
                    else if (mainImageUri == null)
                    {
                        Customer customer = new Customer(user_id, phone_number, email, user_name, "null");
                        databaseReference.push().setValue(customer);
                        setupprogress.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(SetupActivity.this, furniture.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(SetupActivity.this,"Fields cannot be blank",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void bringImagePicker()
    {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(SetupActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                mainImageUri = result.getUri();
                setupImage.setImageURI(mainImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
            }
        }
    }
}