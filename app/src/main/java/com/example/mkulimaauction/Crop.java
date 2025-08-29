package com.example.mkulimaauction;

public class Crop {
    private String id;
    private String farmerId;
    private String cropName;
    private String description;
    private double basePrice;
    private String imageUrl;

    // Empty constructor required for Firestore
    public Crop() {}

    public Crop(String id, String farmerId, String cropName, String description, double basePrice, String imageUrl) {
        this.id = id;
        this.farmerId = farmerId;
        this.cropName = cropName;
        this.description = description;
        this.basePrice = basePrice;
        this.imageUrl = imageUrl;
    }

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
}
