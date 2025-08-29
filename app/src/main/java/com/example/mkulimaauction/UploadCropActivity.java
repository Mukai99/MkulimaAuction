package com.example.mkulimaauction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UploadCropActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 22;

    private ImageView cropImageView;
    private MaterialButton selectImageButton, uploadCropButton;
    private TextInputEditText cropNameEditText, cropDescriptionEditText, cropPriceEditText;

    private Uri filePath;
    private FirebaseFirestore db;
    private StorageReference storageRef;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_crop);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        // Bind Views
        cropImageView = findViewById(R.id.cropImageView);
        selectImageButton = findViewById(R.id.selectImageButton);
        uploadCropButton = findViewById(R.id.uploadCropButton);
        cropNameEditText = findViewById(R.id.cropNameEditText);
        cropDescriptionEditText = findViewById(R.id.cropDescriptionEditText);
        cropPriceEditText = findViewById(R.id.cropPriceEditText);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        // Select Image
        selectImageButton.setOnClickListener(v -> selectImage());

        // Upload Crop
        uploadCropButton.setOnClickListener(v -> uploadCrop());
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Crop Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                cropImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadCrop() {
        String name = cropNameEditText.getText().toString().trim();
        String description = cropDescriptionEditText.getText().toString().trim();
        String price = cropPriceEditText.getText().toString().trim();

        if (filePath == null || name.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        // Create unique image name
        String imageName = "crops/" + UUID.randomUUID().toString();

        StorageReference ref = storageRef.child(imageName);
        ref.putFile(filePath)
                .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
                    // Save crop info to Firestore
                    Map<String, Object> crop = new HashMap<>();
                    crop.put("name", name);
                    crop.put("description", description);
                    crop.put("price", Double.parseDouble(price));
                    crop.put("imageUrl", uri.toString());

                    db.collection("crops").add(crop)
                            .addOnSuccessListener(documentReference -> {
                                progressDialog.dismiss();
                                Toast.makeText(UploadCropActivity.this, "Crop uploaded!", Toast.LENGTH_SHORT).show();
                                finish(); // Close activity
                            })
                            .addOnFailureListener(e -> {
                                progressDialog.dismiss();
                                Toast.makeText(UploadCropActivity.this, "Failed to upload crop", Toast.LENGTH_SHORT).show();
                            });

                }))
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(UploadCropActivity.this, "Image Upload Failed!", Toast.LENGTH_SHORT).show();
                });
    }
}
