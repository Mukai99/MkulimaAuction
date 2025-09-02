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

public class HomeActivity extends AppCompatActivity {

    private TextView welcomeText;
    private FirebaseAuth auth;
    private MaterialButton viewCropsButton, viewSchemesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        welcomeText = findViewById(R.id.welcomeText);
        viewCropsButton = findViewById(R.id.viewCropsButton);
        viewSchemesButton = findViewById(R.id.viewSchemesButton);

        if (user != null) {
            String email = user.getEmail();
            welcomeText.setText("Welcome, " + email + "!");
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

    // Optional: If you still want a logout menu in the top-right corner
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
