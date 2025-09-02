package com.example.mkulimaauction.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mkulimaauction.Crop;
import com.example.mkulimaauction.R;
import com.bumptech.glide.Glide;


import java.util.List;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.CropViewHolder> {

    private Context context;
    private List<Crop> cropList;

    public CropAdapter(Context context, List<Crop> cropList) {
        this.context = context;
        this.cropList = cropList;
    }

    @NonNull
    @Override
    public CropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crop, parent, false);
        return new CropViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CropViewHolder holder, int position) {
        Crop crop = cropList.get(position);

        holder.cropNameText.setText(crop.getCropName());
        holder.cropDescriptionText.setText(crop.getDescription());
        holder.cropPriceText.setText("Price: Ksh " + crop.getBasePrice());

        // Load image with Glide
        Glide.with(holder.itemView.getContext())
                .load(crop.getImageUrl())
                .placeholder(R.drawable.error) // while loading
                .error(R.drawable.error)             // if it fails
                .centerCrop()
                .into(holder.cropImageView);
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public static class CropViewHolder extends RecyclerView.ViewHolder {
        ImageView cropImageView;
        TextView cropNameText, cropDescriptionText, cropPriceText;

        public CropViewHolder(@NonNull View itemView) {
            super(itemView);
            cropImageView = itemView.findViewById(R.id.cropImageView);
            cropNameText = itemView.findViewById(R.id.cropNameText);
            cropDescriptionText = itemView.findViewById(R.id.cropDescriptionText);
            cropPriceText = itemView.findViewById(R.id.cropPriceText);
        }
    }
}
