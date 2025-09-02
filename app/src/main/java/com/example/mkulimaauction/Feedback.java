package com.example.mkulimaauction;

public class Feedback {
    private String feedbackId;
    private String buyerId; // reference to User
    private String cropId;  // reference to Crop
    private String comment;
    private int rating; // e.g. 1–5 stars

    public Feedback() {}

    public Feedback(String feedbackId, String buyerId, String cropId, String comment, int rating) {
        this.feedbackId = feedbackId;
        this.buyerId = buyerId;
        this.cropId = cropId;
        this.comment = comment;
        this.rating = rating;
    }

    public String getFeedbackId() { return feedbackId; }
    public void setFeedbackId(String feedbackId) { this.feedbackId = feedbackId; }

    public String getBuyerId() { return buyerId; }
    public void setBuyerId(String buyerId) { this.buyerId = buyerId; }

    public String getCropId() { return cropId; }
    public void setCropId(String cropId) { this.cropId = cropId; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}
