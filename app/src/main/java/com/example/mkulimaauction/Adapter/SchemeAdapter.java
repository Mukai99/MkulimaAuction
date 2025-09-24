package com.example.mkulimaauction.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mkulimaauction.R;
import com.example.mkulimaauction.Scheme;

import java.util.List;

public class SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.SchemeViewHolder> {

    private Context context;
    private List<Scheme> schemeList;

    public SchemeAdapter(Context context, List<Scheme> schemeList) {
        this.context = context;
        this.schemeList = schemeList;
    }

    @NonNull
    @Override
    public SchemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_scheme, parent, false);
        return new SchemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchemeViewHolder holder, int position) {
        Scheme scheme = schemeList.get(position);

        holder.title.setText(scheme.getTitle());
        holder.description.setText(scheme.getDescription());
    }

    @Override
    public int getItemCount() {
        return schemeList.size();
    }

    public static class SchemeViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;

        public SchemeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.schemeTitle);
            description = itemView.findViewById(R.id.schemeDescription);
        }
    }
}
