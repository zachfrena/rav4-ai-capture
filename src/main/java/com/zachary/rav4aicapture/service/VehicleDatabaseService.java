package com.zachary.rav4aicapture.service;


import com.zachary.rav4aicapture.model.VehicleListing;
import com.zachary.rav4aicapture.model.VehicleListingEntity;
import com.zachary.rav4aicapture.repository.VehicleListingRepository;
import org.springframework.stereotype.Service;

/**
 * Decides whether to save a database entity or not
 */

@Service
public class VehicleDatabaseService {


    private final VehicleListingRepository repository;


    public VehicleDatabaseService(
            VehicleListingRepository repository
    ) {
        this.repository = repository;
    }



    public String save(
            VehicleListing listing
    ) {


        if (
                repository
                        .findByLink(
                                listing.getLink()
                        )
                        .isPresent()
        ) {

            return "Already exists";

        }



        VehicleListingEntity entity =
                new VehicleListingEntity();


        entity.setYear(
                listing.getYear()
        );

        entity.setMake(
                listing.getMake()
        );

        entity.setModel(
                listing.getModel()
        );

        entity.setTrim(
                listing.getTrim()
        );

        entity.setPrice(
                listing.getPrice()
        );

        entity.setKilometers(
                listing.getKilometers()
        );

        entity.setTitleStatus(
                listing.getTitleStatus()
        );

        entity.setProvince(
                listing.getProvince()
        );

        entity.setCity(
                listing.getCity()
        );

        entity.setSource(
                listing.getSource()
        );

        entity.setNotes(
                listing.getNotes()
        );

        entity.setSellerDescription(
                listing.getSellerDescription()
        );

        entity.setLink(
                listing.getLink()
        );


        repository.save(entity);


        return "Added successfully";
    }
}