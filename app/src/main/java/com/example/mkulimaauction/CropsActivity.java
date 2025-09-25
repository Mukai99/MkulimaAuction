package com.example.mkulimaauction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mkulimaauction.Adapter.CropAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CropsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CropAdapter adapter;
    private List<Crop> cropList;
    private FirebaseFirestore db;
    private FloatingActionButton addCropFab;
    private TextView emptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops); // ✅ FIXED

        recyclerView = findViewById(R.id.recyclerViewCrops);
        addCropFab = findViewById(R.id.addCropFab);
        emptyView = findViewById(R.id.emptyView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cropList = new ArrayList<>();
        adapter = new CropAdapter(this, cropList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadCrops();

        addCropFab.setOnClickListener(v -> {
            startActivity(new Intent(CropsActivity.this, UploadCropActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void loadCrops() {
        db.collection("crops").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    cropList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Crop crop = doc.toObject(Crop.class);
                        if (crop != null) {
                            crop.setId(doc.getId()); // 🔑 Save Firestore document ID
                            cropList.add(crop);
                        }
                    }
                    adapter.notifyDataSetChanged();

                    if (cropList.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
    }

}
