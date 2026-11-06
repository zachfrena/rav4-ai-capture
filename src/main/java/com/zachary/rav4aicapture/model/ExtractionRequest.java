package com.zachary.rav4aicapture.model;

/**
 * Java model class, or DTO (data transfer object)
 * represents the data required by the extraction endpoint
 */

public class ExtractionRequest {

    private String title;
    private String price;
    private String aboutVehicle;
    private String sellerDescription;
    private String location;
    private String link;

    private String timePosted;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getAboutVehicle() {
        return aboutVehicle;
    }

    public void setAboutVehicle(String aboutVehicle) {
        this.aboutVehicle = aboutVehicle;
    }


    public String getSellerDescription() {
        return sellerDescription;
    }

    public void setSellerDescription(String sellerDescription) {
        this.sellerDescription = sellerDescription;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getLink() {
        return link;
    }

    public String getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(String timePosted) {
        this.timePosted = timePosted;
    }

    public void setLink(String link) {
        this.link = link;
    }
}