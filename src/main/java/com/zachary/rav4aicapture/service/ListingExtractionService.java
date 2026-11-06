package com.zachary.rav4aicapture.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachary.rav4aicapture.ai.GeminiClient;
import com.zachary.rav4aicapture.model.VehicleListing;
import com.zachary.rav4aicapture.model.ExtractionRequest;
import org.springframework.stereotype.Service;

/**
 * Takes raw facebook marketplace information from API request, creates instructions for Gemini, receives Gemini's response, then returns object to controller.
 */
@Service
public class ListingExtractionService {

    private final GeminiClient geminiClient;
    private final ObjectMapper mapper;

    public ListingExtractionService(
            GeminiClient geminiClient,
            ObjectMapper mapper
    ) {
        this.geminiClient = geminiClient;
        this.mapper = mapper;
    }


    public VehicleListing extract(ExtractionRequest request) {

        String prompt = """
        Extract vehicle information from Facebook Marketplace data.

        Return ONLY valid JSON.
        Do not use markdown.
        Do not wrap JSON in ``` blocks.

        Rules:
        - Do not guess.
        - If a field is unknown, return null.
        - Use the title as the authoritative source for year, make, model, and trim.
        - Use seller description only for additional details.
        - Do not infer missing information.

        Field rules:

        year:
        - Extract from title.
        - Return integer only.

        make:
        - Extract vehicle manufacturer from title.

        model:
        - Extract vehicle model from title.

        trim:
        - Extract trim level from title.
        - Examples: LE, LE+, XLE, Limited, SE, XSE.

        price:
        - Must be an integer.
        - Remove currency symbols and commas.
        Example:
        "CA$36,995" -> 36995

        kilometers:
        - Must be an integer.
        - Remove commas, words, and units.
        Example:
        "Driven 86,915 km" -> 86915

        titleStatus:
        - Extract vehicle title status from seller description.
        - Possible values:
          - Clean
          - Rebuilt
          - Salvage
          - null
        - Look for phrases like:
          "rebuilt title"
          "rebuilt"
          "salvage"
          "clean title"
          "accident free"

        city:
        - Extract city from location.
        - Example:
          "Leduc County, AB" -> "Leduc County"

        province:
        - Extract province abbreviation.
        - Example:
          "Leduc County, AB" -> "AB"

        timePosted:
        - Extract listing age if available.
        - Keep original text.
        Examples:
          "18 hours ago"
          "1 week ago"
          "3 days ago"

        source:
        - Extract dealership or seller name from seller description.
        - Example:
          "At Go Auto Outlet South..." -> "Go Auto Outlet South"
        - If private seller, return "Private Seller".
        - If unknown, return null.

        notes:
        - Extract useful vehicle details from seller description.
        - Include:
          engine,
          drivetrain,
          colors,
          VIN,
          stock number,
          vehicle condition,
          options.
        - Do not duplicate year/make/model/price/kilometers.

        Output JSON format:

        {
          "year": integer,
          "make": string,
          "model": string,
          "trim": string,
          "price": integer,
          "kilometers": integer,
          "titleStatus": string,
          "city": string,
          "province": string,
          "timePosted": string,
          "source": string,
          "link": string,
          "sellerDescription": string,
          "notes": string
        }


        Facebook Marketplace Data:

        Title:
        %s

        Price:
        %s

        Kilometers:
        %s

        Time Posted:
        %s

        Seller Description:
        %s

        Location:
        %s

        Link:
        %s
        """.formatted(
                request.getTitle(),
                request.getPrice(),
                request.getAboutVehicle(),
                request.getTimePosted(),
                request.getSellerDescription(),
                request.getLocation(),
                request.getLink()
        );


        String json = geminiClient.extract(prompt);

        System.out.println("Vehicle JSON:");
        System.out.println(json);

        try {
            json = cleanJson(json);

            VehicleListing listing =
                    mapper.readValue(json, VehicleListing.class);

            listing.setLink(request.getLink());

            return listing;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map vehicle JSON", e);
        }
    }
    private String cleanJson(String json) {

        return json
                .replace("```json", "")
                .replace("```", "")
                .trim();
    }
}