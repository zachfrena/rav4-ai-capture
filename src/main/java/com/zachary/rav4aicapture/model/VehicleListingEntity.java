package com.zachary.rav4aicapture.model;


import jakarta.persistence.*;

/**
 * database representation of a vehicle listing (ie an Entity).
 */

@Entity
@Table(
        name = "vehicle_listings",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = "link" //the link column must contain unique values to prevent duplicate db records
                )
        }
)
public class VehicleListingEntity {


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;


    private Integer year;

    private String make;

    private String model;

    private String trim;

    private Integer price;

    private Integer kilometers;

    private String titleStatus;

    private String province;

    private String city;

    private String timePosted;

    private String source;

    @Column(length = 5000)
    private String notes;


    @Column(length = 5000)
    private String sellerDescription;


    @Column(unique = true)
    private String link;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }


    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }


    public String getTitleStatus() {
        return titleStatus;
    }

    public void setTitleStatus(String titleStatus) {
        this.titleStatus = titleStatus;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(String timePosted) {
        this.timePosted = timePosted;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public String getSellerDescription() {
        return sellerDescription;
    }

    public void setSellerDescription(String sellerDescription) {
        this.sellerDescription = sellerDescription;
    }


    public String getLink() {
        return link;
    }


    public void setLink(String link) {
        this.link = link;
    }
}