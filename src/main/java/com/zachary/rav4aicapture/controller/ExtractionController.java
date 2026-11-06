package com.zachary.rav4aicapture.controller;


import com.zachary.rav4aicapture.model.ExtractionRequest;
import com.zachary.rav4aicapture.model.VehicleListing;
import com.zachary.rav4aicapture.service.ListingExtractionService;
import com.zachary.rav4aicapture.service.VehicleDatabaseService;
import org.springframework.web.bind.annotation.*;

/**
 * exposes 2 POST endpoints to interact with both the extraction and database services.
 * Does not perform any extraction or database worklows, but rather just concerns itself with the HTTP boundary.
 *
 */

@RestController
@RequestMapping("/api")
public class ExtractionController {

    private final ListingExtractionService service;
    private final VehicleDatabaseService databaseService;

    public ExtractionController(
            ListingExtractionService service,
            VehicleDatabaseService databaseService
    ) {
        this.service = service;
        this.databaseService = databaseService;
    }

    @PostMapping("/extract")
    public VehicleListing extract(
            @RequestBody ExtractionRequest request
    ) {
        return service.extract(request);
    }

    @PostMapping("/save")
    public String save(
            @RequestBody VehicleListing listing
    ) {

        return databaseService.save(listing);

    }
}