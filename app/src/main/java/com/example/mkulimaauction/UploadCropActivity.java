package com.example.mkulimaauction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UploadCropActivity extends AppCompatActivity {

    private TextInputEditText cropNameEditText, cropDescriptionEditText, cropPriceEditText;
    private MaterialButton selectImageButton, uploadCropButton;
    private ImageView cropImageView;

    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

    // Store selected drawable resource ID and key
    private int selectedImageRes = -1;
    private String selectedImageKey = null;

    // Crop names and images
    private final String[] crops = {"Maize", "Beans", "Wheat", "Broccoli", "Carrot", "Cow", "Apples", "Peas", "Nuts"};
    private final int[] cropImages = {
            R.drawable.maize,
            R.drawable.beans,
            R.drawable.wheat,
            R.drawable.brocoli,
            R.drawable.carrot,
            R.drawable.cow,
            R.drawable.apples,
            R.drawable.peas,
            R.drawable.rednuts
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_crop);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Bind Views
        cropNameEditText = findViewById(R.id.cropNameEditText);
        cropDescriptionEditText = findViewById(R.id.cropDescriptionEditText);
        cropPriceEditText = findViewById(R.id.cropPriceEditText);
        cropImageView = findViewById(R.id.cropImageView);
        selectImageButton = findViewById(R.id.selectImageButton);
        uploadCropButton = findViewById(R.id.uploadCropButton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");

        // Pick from preset images
        selectImageButton.setOnClickListener(v -> selectPresetImage());

        // Save crop
        uploadCropButton.setOnClickListener(v -> uploadCrop());
    }

    private void selectPresetImage() {
        new AlertDialog.Builder(this)
                .setTitle("Select Crop Image")
                .setItems(crops, (dialog, which) -> {
                    cropImageView.setImageResource(cropImages[which]); // preview image
                    selectedImageRes = cropImages[which];              // save resource ID
                    selectedImageKey = crops[which];                   // save name (e.g. "Maize")
                })
                .show();
    }

    private void uploadCrop() {
        String name = cropNameEditText.getText().toString().trim();
        String description = cropDescriptionEditText.getText().toString().trim();
        String price = cropPriceEditText.getText().toString().trim();

        if (name.isEmpty() || price.isEmpty() || selectedImageKey == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        long now = System.currentTimeMillis();
        long oneHourLater = now + (60 * 60 * 1000); // 1 hour auction

        // Save to Firestore
        Map<String, Object> crop = new HashMap<>();
        crop.put("cropName", name);                      // ✅ match Crop.java
        crop.put("description", description);
        crop.put("basePrice", Double.parseDouble(price));
        crop.put("imageKey", selectedImageKey);

        // --- Auction-related fields (match Crop.java exactly) ---
        crop.put("highestBid", Double.parseDouble(price)); // start with base price
        crop.put("highestBidderId", "");                   // none yet
        crop.put("auctionEndTime", oneHourLater);          // correct name

        String cropId = name + "_" + System.currentTimeMillis();
        crop.put("id", cropId);
        db.collection("crops").document(name).set(crop)
                .addOnSuccessListener(documentReference -> {
                    progressDialog.dismiss();
                    Toast.makeText(UploadCropActivity.this, "Crop saved!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(UploadCropActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                });
    }


}
