package com.example.muj.goldenfurniture;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity
{
    private EditText et;
    private Button bt;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    AlertDialog alertDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        firebaseAuth = FirebaseAuth.getInstance();
        et = findViewById(R.id.et_reset_email);
        bt = findViewById(R.id.bt_reset_password);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Reset Password");
        alertDialog.setMessage("Please check you mail for link to reset password");
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String email = et.getText().toString().trim();
                if (email.isEmpty())
                {
                    Toast.makeText(ResetPassword.this,"Please enter email",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    progressDialog.setMessage("Please wait..");
                    progressDialog.show();
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                alertDialog.show();
                            }

                        }
                    });
                }

            }
        });

    }
}
