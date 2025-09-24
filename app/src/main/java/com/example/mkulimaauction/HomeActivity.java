package com.example.mkulimaauction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    private TextView welcomeText;
    private FirebaseAuth auth;
    private MaterialButton viewCropsButton, viewSchemesButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        welcomeText = findViewById(R.id.welcomeText);
        viewCropsButton = findViewById(R.id.viewCropsButton);
        viewSchemesButton = findViewById(R.id.viewSchemesButton);

        if (user != null) {
            // Fetch user name from Firestore instead of showing email
            String userId = user.getUid();
            DocumentReference docRef = db.collection("users").document(userId);
            docRef.get().addOnSuccessListener(document -> {
                if (document.exists()) {
                    String name = document.getString("name");
                    if (name != null && !name.isEmpty()) {
                        welcomeText.setText("Welcome, " + name + "!");
                    } else {
                        // fallback to email if no name found
                        welcomeText.setText("Welcome, " + user.getEmail() + "!");
                    }
                } else {
                    welcomeText.setText("Welcome, " + user.getEmail() + "!");
                }
            }).addOnFailureListener(e -> {
                welcomeText.setText("Welcome, " + user.getEmail() + "!");
            });
        } else {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }

        // View Crops button → go to ViewCropsActivity
        viewCropsButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, CropsActivity.class));
        });

        // Government Schemes button → go to SchemesActivity
        viewSchemesButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, SchemesActivity.class));
        });
    }

    // Optional: Logout menu
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            auth.signOut();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
