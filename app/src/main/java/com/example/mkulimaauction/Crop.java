package com.example.mkulimaauction;

public class Crop {
    private String id;
    private String farmerId;
    private String cropName;
    private String description;
    private double basePrice;
    private String imageUrl;

    // Auction fields
    private double highestBid;       // current highest bid
    private String highestBidderId;  // user who placed it
    private long auctionEndTime;     // timestamp (e.g., System.currentTimeMillis() + duration)

    // Empty constructor required for Firestore
    public Crop() {}

    public Crop(String id, String farmerId, String cropName, String description,
                double basePrice, String imageUrl, long auctionEndTime) {
        this.id = id;
        this.farmerId = farmerId;
        this.cropName = cropName;
        this.description = description;
        this.basePrice = basePrice;
        this.imageUrl = imageUrl;
        this.highestBid = basePrice; // start bidding at base price
        this.highestBidderId = "";
        this.auctionEndTime = auctionEndTime;
    }
    private String imageKey;

    public String getImageKey() { return imageKey; }
    public void setImageKey(String imageKey) { this.imageKey = imageKey; }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFarmerId() { return farmerId; }
    public void setFarmerId(String farmerId) { this.farmerId = farmerId; }

    public String getCropName() { return cropName; }
    public void setCropName(String cropName) { this.cropName = cropName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getHighestBid() { return highestBid; }
    public void setHighestBid(double highestBid) { this.highestBid = highestBid; }

    public String getHighestBidderId() { return highestBidderId; }
    public void setHighestBidderId(String highestBidderId) { this.highestBidderId = highestBidderId; }

    public long getAuctionEndTime() { return auctionEndTime; }
    public void setAuctionEndTime(long auctionEndTime) { this.auctionEndTime = auctionEndTime; }
}
