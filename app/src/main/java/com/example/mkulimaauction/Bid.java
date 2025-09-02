package com.example.mkulimaauction;

public class Bid {
    private String bidId;
    private String cropId;   // reference to Crop
    private String buyerId;  // reference to User (buyer)
    private double bidAmount;
    private long timestamp;  // when bid was placed

    public Bid() {}

    public Bid(String bidId, String cropId, String buyerId, double bidAmount, long timestamp) {
        this.bidId = bidId;
        this.cropId = cropId;
        this.buyerId = buyerId;
        this.bidAmount = bidAmount;
        this.timestamp = timestamp;
    }

    public String getBidId() { return bidId; }
    public void setBidId(String bidId) { this.bidId = bidId; }

    public String getCropId() { return cropId; }
    public void setCropId(String cropId) { this.cropId = cropId; }

    public String getBuyerId() { return buyerId; }
    public void setBuyerId(String buyerId) { this.buyerId = buyerId; }

    public double getBidAmount() { return bidAmount; }
    public void setBidAmount(double bidAmount) { this.bidAmount = bidAmount; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
