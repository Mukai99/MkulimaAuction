package com.example.mkulimaauction.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mkulimaauction.Bid;
import com.example.mkulimaauction.Crop;
import com.example.mkulimaauction.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
        holder.cropPriceText.setText("Base Price: Ksh " + crop.getBasePrice());
        holder.cropHighestBidText.setText("Highest Bid: Ksh " + crop.getHighestBid());

        int imageRes = getImageResFromKey(crop.getImageKey());
        if (imageRes != -1) {
            holder.cropImageView.setImageResource(imageRes);
        } else {
            holder.cropImageView.setImageResource(R.drawable.error);
        }

        // Bid button click
        holder.bidButton.setOnClickListener(v -> {
            if (System.currentTimeMillis() > crop.getAuctionEndTime()) {
                Toast.makeText(context, "Auction ended", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Place your bid");

            final EditText input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            builder.setView(input);

            builder.setPositiveButton("Bid", null); // we’ll override later
            builder.setNegativeButton("Cancel", (d, w) -> d.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();

            // Override Positive button to prevent auto-dismiss
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                double bidAmount;
                try {
                    bidAmount = Double.parseDouble(input.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(context, "Invalid amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (bidAmount > crop.getHighestBid()) {
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    updateBidInFirestore(crop.getId(), bidAmount, userId, holder.cropHighestBidText, dialog);
                } else {
                    Toast.makeText(context, "Bid must be higher than current highest bid", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void updateBidInFirestore(String cropId, double bidAmount, String bidderId, TextView highestBidView, AlertDialog dialog) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference cropRef = db.collection("crops").document(cropId);

        // Update highest bid on crop
        cropRef.update(
                "highestBid", bidAmount,
                "highestBidderId", bidderId
        ).addOnSuccessListener(aVoid -> {
            highestBidView.setText("Highest Bid: Ksh " + bidAmount);
            Toast.makeText(context, "Bid placed!", Toast.LENGTH_SHORT).show();

            // Save bid history in "bids" collection
            String bidId = db.collection("bids").document().getId(); // generate unique id
            Bid bid = new Bid(
                    bidId,
                    cropId,
                    bidderId,
                    bidAmount,
                    System.currentTimeMillis()
            );

            db.collection("bids").document(bidId).set(bid)
                    .addOnSuccessListener(unused -> {
                        // Optional: toast for success
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Failed to save bid history", Toast.LENGTH_SHORT).show();
                    });

        }).addOnFailureListener(e ->
                Toast.makeText(context, "Failed to place bid", Toast.LENGTH_SHORT).show()
        );
    }



    private void saveBidToFirestore(String cropId, double bidAmount, String buyerId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create unique bidId
        String bidId = cropId + "_" + System.currentTimeMillis();

        // Create bid object
        Bid bid = new Bid(
                bidId,
                cropId,
                buyerId,
                bidAmount,
                System.currentTimeMillis()
        );

        // Save bid under "bids" collection
        db.collection("bids").document(bidId).set(bid)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(context, "Bid recorded!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Failed to save bid record", Toast.LENGTH_SHORT).show()
                );
    }


    private int getImageResFromKey(String key) {
        if (key == null) return -1;
        switch (key) {
            case "Maize": return R.drawable.maize;
            case "Beans": return R.drawable.beans;
            case "Wheat": return R.drawable.wheat;
            case "Broccoli": return R.drawable.brocoli;
            case "Carrot": return R.drawable.carrot;
            case "Cow": return R.drawable.cow;
            case "Apples": return R.drawable.apples;
            case "Peas": return R.drawable.peas;
            case "Nuts": return R.drawable.rednuts;
            default: return -1;
        }
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public static class CropViewHolder extends RecyclerView.ViewHolder {
        ImageView cropImageView;
        TextView cropNameText, cropDescriptionText, cropPriceText, cropHighestBidText;
        Button bidButton;

        public CropViewHolder(@NonNull View itemView) {
            super(itemView);
            cropImageView = itemView.findViewById(R.id.cropImageView);
            cropNameText = itemView.findViewById(R.id.cropNameText);
            cropDescriptionText = itemView.findViewById(R.id.cropDescriptionText);
            cropPriceText = itemView.findViewById(R.id.cropPriceText);
            cropHighestBidText = itemView.findViewById(R.id.cropHighestBidText);
            bidButton = itemView.findViewById(R.id.bidButton);
        }
    }
}
