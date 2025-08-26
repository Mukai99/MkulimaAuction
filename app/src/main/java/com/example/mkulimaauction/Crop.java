package com.example.mkulimaauction;

public class Crop {
    private String cropId;
    private String farmerId;
    private String cropName;
    private String description;
    private double basePrice;
    private String auctionStatus;
    private String imageUrl;
    private long timestamp;



    //empty constructor for firestore
    public Crop(){

    }

    public Crop(String cropId, String farmerId, String cropName, String description, double basePrice, String auctionStatus, String imageUrl,long timestamp){
        this.cropId = cropId;
        this.farmerId = farmerId;
        this.cropName = cropName;
        this.description = description;
        this.basePrice = basePrice;
        this.auctionStatus = auctionStatus;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
    }
    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public String getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(String auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
//toString method for easier debugging and logging
    @Override
    public String toString(){
        return "Crop{" +
                "cropId='" + cropId + '\'' +
                ", farmerId='" + farmerId + '\'' +
                ", cropName='" + cropName + '\'' +
                ", description='" + description + '\'' +
                ", basePrice=" + basePrice +
                ", auctionStatus='" + auctionStatus + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", timestamp=" + timestamp +
                '}';

    }







}
