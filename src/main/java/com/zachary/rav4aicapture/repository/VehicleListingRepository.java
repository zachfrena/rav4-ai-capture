package com.zachary.rav4aicapture.repository;


import com.zachary.rav4aicapture.model.VehicleListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

/**
 * provides application the ability to perform database operations
 */

public interface VehicleListingRepository
        extends JpaRepository<VehicleListingEntity, Long> {


    Optional<VehicleListingEntity> findByLink(
            String link
    );

}