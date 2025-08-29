package com.example.mkulimaauction;

public class Scheme {
    private String schemeId;
    private String title;
    private String description;

    public Scheme() {}

    public Scheme(String schemeId, String title, String description) {
        this.schemeId = schemeId;
        this.title = title;
        this.description = description;
    }

    public String getSchemeId() { return schemeId; }
    public void setSchemeId(String schemeId) { this.schemeId = schemeId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
