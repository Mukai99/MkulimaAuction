package com.example.mkulimaauction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
 private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private  MaterialButton registerButton;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        emailEditText = (EditText) findViewById(R.id.registerEmailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);
        registerButton = (MaterialButton) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> registerUser());
    }

        private void registerUser(){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            //Validation
            if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                Toast.makeText(this,"Alll fields are required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 8){
                Toast.makeText(this, "Password mst be at least 8 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            //Firebase Registration
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user !=null){
                        user.sendEmailVerification().addOnCompleteListener(verifyTask ->{
                            if (verifyTask.isSuccessful()){
                                Toast.makeText(this, "Registered! Check your email for verification", Toast.LENGTH_SHORT).show();
                                finish(); // directs back to login
                            }
                            else {
                                Toast.makeText(this, "Failed to send verification email", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else {
                    String error = task.getException() !=null ? task.getException().getMessage() : "Registration failed";
                    Toast.makeText(this,error, Toast.LENGTH_LONG).show();
                }
            });
        }

    }

