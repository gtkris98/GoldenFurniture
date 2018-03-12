package com.example.muj.goldenfurniture;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterUser extends AppCompatActivity implements View.OnClickListener
{
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonRegister = findViewById(R.id.bt_register);


        progressDialog = new ProgressDialog(this);

        editTextEmail = findViewById(R.id.et_register_email);
        editTextPassword = findViewById(R.id.et_register_password);

        textViewLogin = findViewById(R.id.tv_signin);

        buttonRegister.setOnClickListener((View.OnClickListener) this);
        textViewLogin.setOnClickListener((View.OnClickListener) this);
    }

    private void registerUser()
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (email.isEmpty())
        {
            Toast.makeText(RegisterUser.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty())
        {
            Toast.makeText(RegisterUser.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        //if both are not empty
        //we will show a progress dialog
        progressDialog.setMessage("Registering User..");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegisterUser.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                            Intent intent = new Intent(RegisterUser.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(RegisterUser.this,"Registration Unsuccessful.Please Try Again",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });


    }
    @Override
    public void onClick(View view)
    {
        if (view == buttonRegister)
        {
            registerUser();
        }
        if (view == textViewLogin)
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

    }


}
