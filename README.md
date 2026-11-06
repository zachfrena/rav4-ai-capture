# Before you get started:

## Installing the RAV4 AI Capture Chrome Extension
The extension can be installed locally in Chrome using Developer Mode. This allows you to load and test the extension directly from its project folder without publishing it to the Chrome Web Store.
1. Prepare the extension folder
   Ensure all extension files are located together in one directory.

For example:
```
rav4-extension/
├── manifest.json
├── popup.html
├── popup.js
└── content.js
````

2. Open Chrome Extensions
   1. Open Google Chrome.
   2. Navigate to chrome://extensions/.
   3. Enable Developer mode using the toggle in the top-right corner.
3. Load the extension
   1. Click Load unpacked.
   2. Navigate to your rav4-extension directory.
   3. Select the folder containing manifest.json.
   4. Click Open.
      Chrome should now display RAV4 AI Capture in your installed extensions.
4. Pin the extension
   1. Click the Extensions puzzle-piece icon in Chrome's toolbar.
   2. Find RAV4 AI Capture.
3. Click the pin icon to make it easily accessible.
5. Run the application
   Before using the extension:
   1. Start your Spring Boot application from IntelliJ.
   2. Verify that the backend is running on port 8080.
   3. Open a Facebook Marketplace RAV4 listing.
   4. Refresh the Marketplace page if it was already open when the extension was installed.
   5. Click the RAV4 AI Capture extension icon.
6. Extract and save a listing
   1. Click Extract Vehicle.
   2. The extension reads the Marketplace listing and sends it to the backend.
   3. Gemini processes the listing and returns structured vehicle information.
   4. Review the extracted information in the popup.
   5. Click Add To Database to save the listing to SQLite.
7. Updating the extension
   Whenever you modify the extension's JavaScript, HTML, or manifest:
   1. Navigate to chrome://extensions/.
   2. Find RAV4 AI Capture.
   3. Click the Reload icon.
   4. Refresh the Facebook Marketplace listing page.
   5. Test the updated extension.


## Define GEMINI_API_KEY
The application reads the API key from the GEMINI_API_KEY environment variable. You can configure it using any of these methods:
1. Terminal (macOS/Linux):
   ```
   export GEMINI_API_KEY="your_api_key"
   ```
2. IntelliJ IDEA:
    - Open Run → Edit Configurations.
    - Add GEMINI_API_KEY=your_api_key under Environment variables.
3. .env file (based on provided `sample.env`):
    - Create a local .env file in the project root.
    - Add GEMINI_API_KEY=your_api_key.
    - Ensure .env is in .gitignore.
    - Note: Spring Boot does not automatically load .env files without additional configuration or tooling. You would need to export it manually like:
```
    export GEMINI_API_KEY="your_api_key"
    mvn spring-boot:run
```
---

# Full Application flow:

## 1. User opens a Facebook Marketplace listing 
- The user navigates to a RAV4 vehicle listing in Chrome.
- The Chrome extension is loaded and its content script (content.js) is available on the webpage.

## 2. Chrome extension initializes the extraction workflow
- The extension consists of three files:
  - popup.html	-> Displays the extension interface and buttons. 
  - popup.js ->	Handles user interactions and communicates with the backend. 
  - content.js -> Reads and extracts information from the Marketplace webpage.

## 3. Extract vehicle information from the webpage
- When the user selects `Extract Vehicle`, popup.js sends a message to content.js.
- content.js extracts information such as:
  - Listing title
  - Price
  - Mileage
  - Seller description
  - Vehicle title status
  - Location
  - Posting date/time
  - Listing URL
- The extracted information is assembled into a JSON object.

## 4. Send extraction request to Spring Boot
- popup.js sends an HTTP POST request containing the extracted listing information.
- i.e. POST request to http://localhost:8080/api/extract
- The request is received by ExtractionController.

## 5. Process the extraction request
- The backend processes the request through the following components:
  - ExtractionController	receives the HTTP request.
  - ListingExtractionService	builds the Gemini prompt and processes the response.
  - GeminiClient	communicates with the Google Gemini API.

- The workflow:
  1. ExtractionController.extract() receives the ExtractionRequest.
  2. Calls ListingExtractionService.extract().
  3. ListingExtractionService constructs a prompt using the listing information.
  4. Calls GeminiClient.extract() to submit the prompt to Gemini.
  5. Gemini analyzes the listing and returns structured JSON.
  6. ListingExtractionService cleans and maps the JSON into a VehicleListing object.

## 6. Return structured vehicle information to Chrome
- The structured VehicleListing object is returned through the controller.
- Spring Boot serializes the object into JSON and sends it back to popup.js.
- The extension:
  - Receives the JSON response.
  - Displays the extracted vehicle information to the user.
  - Enables the Add To Database button.
  
## 7. Submit the vehicle for database storage
- When the user selects Add To Database, popup.js sends another HTTP POST request.
- POST request to http://localhost:8080/api/save
- The request contains the structured vehicle information returned by Gemini.

## 8. Persist the vehicle listing
- The backend processes the save request through these components: 
  - ExtractionController receives the save request.
  - VehicleDatabaseService checks for duplicates and maps the listing to a database entity.
  - VehicleListingRepository handles database persistence through Spring Data JPA.

- The workflow:
  1. ExtractionController.save() receives the VehicleListing.
  2. Calls VehicleDatabaseService.save().
  3. The service checks whether the listing URL already exists using findByLink().
  4. If the listing already exists, it returns "Already exists".
  5. Otherwise, it maps VehicleListing into a VehicleListingEntity.
  6. Calls VehicleListingRepository.save() to persist the entity.
  7. Hibernate/JPA writes the record into SQLite (rav4.db).
     The result is returned to the Chrome extension and displayed to the user.
