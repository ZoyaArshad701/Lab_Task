package com.example.lab_task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText EmailET, PwdET;
    private FirebaseAuth auth;
    private Button signin,Signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmailET = findViewById(R.id.EmailET);
        PwdET = findViewById(R.id.PwdET);
        Signup=findViewById(R.id.Signup);
        signin=findViewById(R.id.signin);
    }
    public void Signup() {
        try {

            if (!EmailET.getText().toString().isEmpty()
                    &&
                    !PwdET.getText().toString().isEmpty()) {
                if (auth != null) {


                    Signup.setEnabled(false);

                    auth.createUserWithEmailAndPassword(EmailET.getText().toString(),
                            PwdET.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            if (authResult.getUser() != null) {

                                auth.signOut();
                                EmailET.setText("");

                                PwdET.setText("");
                                EmailET.requestFocus();

                                Signup.setEnabled(true);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Signup.setEnabled(true);
                            EmailET.requestFocus();

                            Toast.makeText(MainActivity.this, "Failed To Create User" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else if (EmailET.getText().toString().isEmpty()) {
                Signup.setEnabled(true);


                EmailET.requestFocus();
                Toast.makeText(this, "Please Enter The Email", Toast.LENGTH_SHORT).show();
            } else if (PwdET.getText().toString().isEmpty()) {
                Signup.setEnabled(true);


                PwdET.requestFocus();
                Toast.makeText(this, "Please Enter The Password", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {

            Signup.setEnabled(true);

            EmailET.requestFocus();
            Toast.makeText(this, "Signup Error" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void signin()
    {
        try {

            if (!EmailET.getText().toString().isEmpty() && !PwdET.getText().toString().isEmpty()) {

                if (auth.getCurrentUser() != null) {


                    auth.signOut();
                    signin.setEnabled(false);

                    Toast.makeText(this, "User Logged Out Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(EmailET.getText().toString(),
                            PwdET.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {


                                    signin.setEnabled(true);
                                    Toast.makeText(MainActivity.this, "User Logged In", Toast.LENGTH_SHORT).show();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            signin.setEnabled(true);
                            EmailET.requestFocus();

                        }
                    });
                }
            } else if (EmailET.getText().toString().isEmpty()) {
                signin.setEnabled(true);


                EmailET.requestFocus();
                Toast.makeText(this, "Please Enter The Email", Toast.LENGTH_SHORT).show();
            } else if (PwdET.getText().toString().isEmpty()) {
                signin.setEnabled(true);


                PwdET.requestFocus();
                Toast.makeText(this, "Please Enter The Password", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {

            signin.setEnabled(true);
            EmailET.requestFocus();


            Toast.makeText(this, "Logging In Error" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void in(View view){
        signin();
    }
    public void up(View view)
    {
        Signup();
    }
    public void openUploadPage(View view) {
        try {
            startActivity(new Intent(MainActivity.this, Upload.class));
        } catch (Exception e) {
            Toast.makeText(this, "openUploadPage:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
