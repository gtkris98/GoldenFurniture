package com.example.muj.goldenfurniture;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity
{
    private Button changePasswordButton;
    private EditText editTextOldPassword;
    private EditText editTextNewPassword;
    private EditText editTextNewPasswordRetype;
    private FirebaseUser firebaseUser;
    private AuthCredential authCredential;
    private String newPassword,oldPassword;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        changePasswordButton = findViewById(R.id.settings_button);
        editTextOldPassword = findViewById(R.id.settings_old_password);
        editTextNewPassword = findViewById(R.id.settings_new_password);
        editTextNewPasswordRetype = findViewById(R.id.settings_new_password_retype);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(SettingsActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (TextUtils.isEmpty(editTextOldPassword.getText().toString()) || TextUtils.isEmpty(editTextNewPassword.getText().toString()) )
                {
                    Toast.makeText(SettingsActivity.this,"Please Enter all fields",Toast.LENGTH_SHORT).show();
                }
                else if (!editTextNewPassword.getText().toString().equals(editTextNewPasswordRetype.getText().toString()))
                {
                    Toast.makeText(SettingsActivity.this,"New password do not match",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.show();
                    oldPassword = editTextOldPassword.getText().toString();
                    newPassword = editTextNewPassword.getText().toString();
                    authCredential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),oldPassword);
                    firebaseUser.reauthenticate(authCredential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(SettingsActivity.this,"Password Updated Successfully",Toast.LENGTH_LONG).show();
                                                    finish();
                                                }
                                                else
                                                {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(SettingsActivity.this,"ERROR!! Password not updated",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                    else
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(SettingsActivity.this,"ERROR!! Authentication Failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });


    }
}
