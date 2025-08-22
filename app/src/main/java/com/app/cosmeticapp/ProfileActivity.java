package com.app.cosmeticapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private EditText editName, editEmail;
    private ImageView profileImage, editIcon, nameEditIcon, emailEditIcon;
    private Button saveBtn, logoutBtn;
    private LinearLayout viewOrdersBtn, viewWishlistBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;
    private StorageReference storageRef;

    private Uri selectedImageUri;
    private String uid;

    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Init views
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        profileImage = findViewById(R.id.profileImage);
        editIcon = findViewById(R.id.editIcon);
        nameEditIcon = findViewById(R.id.nameEditIcon);
        emailEditIcon = findViewById(R.id.emailEditIcon);
        saveBtn = findViewById(R.id.saveBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        viewOrdersBtn = findViewById(R.id.cardOrders);
        viewWishlistBtn = findViewById(R.id.cardWishlist);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        storageRef = FirebaseStorage.getInstance().getReference("profileImages").child(uid + ".jpg");

        // Image picker
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        selectedImageUri = uri;
                        profileImage.setImageURI(uri); // preview
                        uploadImageToFirebase(uri);
                    }
                });

        // Listeners
        editIcon.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
        nameEditIcon.setOnClickListener(v -> {
            editName.setEnabled(true);
            editName.requestFocus();
        });
        emailEditIcon.setOnClickListener(v -> {
            editEmail.setEnabled(true);
            editEmail.requestFocus();
        });

        saveBtn.setOnClickListener(v -> updateUserData());
        logoutBtn.setOnClickListener(v -> logoutUser());

        viewOrdersBtn.setOnClickListener(v ->
                Toast.makeText(this, "Orders page coming soon!", Toast.LENGTH_SHORT).show());
        viewWishlistBtn.setOnClickListener(v ->
                Toast.makeText(this, "Wishlist page coming soon!", Toast.LENGTH_SHORT).show());

        loadUserData();
    }

    private void loadUserData() {
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);

                    editName.setText(name);
                    editName.setEnabled(false);

                    editEmail.setText(email);
                    editEmail.setEnabled(false);

                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Failed to load profile data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserData() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        if (name.isEmpty()) {
            editName.setError("Name is required");
            return;
        }

        databaseRef.child("name").setValue(name);
        databaseRef.child("email").setValue(email);

        Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();

        editName.setEnabled(false);
        editEmail.setEnabled(false);
    }

    private void uploadImageToFirebase(Uri uri) {
        storageRef.putFile(uri)
                .addOnSuccessListener(taskSnapshot ->
                        storageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                            String imageUrl = downloadUri.toString();

                            // Save image URL to Realtime Database
                            databaseRef.child("profileImage").setValue(imageUrl)
                                    .addOnSuccessListener(aVoid ->
                                            Toast.makeText(this, "Profile image updated", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e ->
                                            Toast.makeText(this, "Image URL save failed", Toast.LENGTH_SHORT).show());
                        }))
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show());
    }



    private void logoutUser() {
        mAuth.signOut();
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }
}
