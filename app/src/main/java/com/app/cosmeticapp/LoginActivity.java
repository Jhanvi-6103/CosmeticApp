package com.app.cosmeticapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button loginBtn;
    TextView gotoSignup;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        gotoSignup = findViewById(R.id.gotoSignup);
        auth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(v -> {
            String emailTxt = email.getText().toString().trim();
            String passwordTxt = password.getText().toString().trim();

            if (emailTxt.isEmpty() || passwordTxt.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(emailTxt, passwordTxt)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        gotoSignup.setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class))
        );
    }
}
