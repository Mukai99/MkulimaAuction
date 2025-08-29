package com.example.mkulimaauction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mkulimaauction.Adapter.SchemeAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SchemesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SchemeAdapter adapter;
    private List<Scheme> schemeList;
    private FirebaseFirestore db;
    private TextView emptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schemes);

        recyclerView = findViewById(R.id.recyclerViewSchemes);
        emptyView = findViewById(R.id.emptyViewSchemes);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        schemeList = new ArrayList<>();
        adapter = new SchemeAdapter(this, schemeList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadSchemes();
    }

    private void loadSchemes() {
        db.collection("schemes").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    schemeList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Scheme scheme = doc.toObject(Scheme.class);
                        if (scheme != null) {
                            schemeList.add(scheme);
                        }
                    }
                    adapter.notifyDataSetChanged();

                    if (schemeList.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
    }
}
