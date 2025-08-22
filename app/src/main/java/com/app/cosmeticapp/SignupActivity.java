package com.app.cosmeticapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class SignupActivity extends AppCompatActivity {
    EditText name, email, password;
    Button signupBtn;
    TextView gotoLogin;

    FirebaseAuth auth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signupBtn = findViewById(R.id.signupBtn);
        gotoLogin = findViewById(R.id.gotoLogin);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Users");

        signupBtn.setOnClickListener(v -> {
            String nameTxt = name.getText().toString().trim();
            String emailTxt = email.getText().toString().trim();
            String passTxt = password.getText().toString().trim();

            if (nameTxt.isEmpty() || emailTxt.isEmpty() || passTxt.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(emailTxt, passTxt)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String uid = auth.getCurrentUser().getUid();
                            User user = new User(nameTxt, emailTxt);
                            database.child(uid).setValue(user);

                            Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        gotoLogin.setOnClickListener(v ->
                startActivity(new Intent(SignupActivity.this, LoginActivity.class))
        );
    }
}
