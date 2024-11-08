package steps;

import base.DriverInstance;
import io.cucumber.java.en.*;
import io.cucumber.java.*;
import Utility.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class CarValuationSteps {

    private DriverInstance driverInstance;
    private WebDriverWait wait;
    private List<String> registrationNumbers;
    private static final String OUTPUT_FILE = "src/test/resources/output/output.txt";

    // Setup method to initialize the WebDriver and WebDriverWait
    @Before
    public void setup() throws MalformedURLException {
        driverInstance = new DriverInstance();
        driverInstance.setUp(Config.getBrowser());  // Initialize WebDriver
        wait = new WebDriverWait(driverInstance.driver, java.time.Duration.ofSeconds(8));
    }

    // Step definition to extract vehicle registration numbers from an input file
    @Given("I extract registration numbers from {string}")
    public void extractRegistrationNumbers(String inputFile) throws IOException {
        // Read lines from the input file and extract registration numbers using regex
        List<String> lines = FileUtil.readLines(Paths.get("src", "test", "resources", "input", inputFile).toString());
        registrationNumbers = RegexUtil.extractRegistrationNumbers(String.join(" ", lines));
    }

    // Step definition to fetch vehicle details for each registration number
    @When("I fetch vehicle details from the car valuation site for each registration number")
    public void fetchVehicleDetailsForEachRegistrationNumber() {
        // Check if the output file exists and delete it if needed
        File outputFile = new File(Paths.get("src", "test", "resources", "output", "output.txt").toString());
        if (outputFile.exists()) {
            if (outputFile.delete()) {
                System.out.println("output.txt file deleted successfully.");
            } else {
                System.out.println("Failed to delete output.txt file.");
            }
        } else {
            System.out.println("output.txt file does not exist, no need to delete.");
        }

        // Iterate through each registration number and fetch vehicle details
        for (String regNumber : registrationNumbers) {

            System.out.println(regNumber);
            int mileage = RandomUtil.generateRandomMileage();
            driverInstance.driver.get(Config.getBaseUrl() + regNumber + "?mileage=" + mileage);

            // Handle cookie popup if present
            WebDriverUtil.handleCookiePopup(driverInstance.driver, wait, "OptanonAlertBoxClosed", "onetrust-accept-btn-handler");

            try {
                // Wait for the vehicle details section to be visible (e.g., email box)
                new WebDriverWait(driverInstance.driver, Duration.ofSeconds(10)).until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("EmailAddress"))
                );
            } catch (TimeoutException e) {
                // Skip this registration number if vehicle details couldn't be fetched
                System.out.println("Timed out waiting for vehicle details for registration number: " + regNumber);
                continue;
            }

            // Extract vehicle details such as make, model, year
            Map<String, String> vehicleDetails = WebDriverUtil.extractVehicleDetails(driverInstance.driver);

            // Format the extracted details and save them to the output file
            String output = regNumber.replaceAll(" ", "") + "," +
                    vehicleDetails.getOrDefault("make", "Unknown") + "," +
                    vehicleDetails.getOrDefault("model", "Unknown") + "," +
                    vehicleDetails.getOrDefault("year", "Unknown");
            try {
                FileUtil.appendLine(OUTPUT_FILE, output); // Append to the output file
            } catch (IOException e) {
                System.out.println("Error writing to output file: " + e.getMessage());
            }
        }
    }

    // Step definition to confirm that the extracted details are saved to a file
    @When("I save the extracted vehicle details to {string}")
    public void saveExtractedDetailsToFile(String outputFile) {
        System.out.println("Vehicle details saved to: " + outputFile);
    }

    // Step definition to compare the output file with an expected file
    @Then("I compare {string} with {string}")
    public void compareFiles(String outputFile, String expectedFile) throws IOException {
        String outputFilePath = Paths.get("src", "test", "resources", "output", outputFile).toString();
        String expectedFilePath = Paths.get("src", "test", "resources", "expected", expectedFile).toString();

        // Use utility method to compare the files and assert that they match
        boolean isMatch = FileUtil.compareFiles(outputFilePath, expectedFilePath);
        Assert.assertTrue("Files do not match", isMatch);
    }

    // Step definition to confirm no mismatches in the files
    @Then("I expect no mismatches in the files")
    public void expectNoMismatchesInFiles() {
        System.out.println("Files matched successfully, test passed.");
    }

    // After hook to clean up WebDriver after each test
    @After
    public void tearDown() {
        driverInstance.tearDown();  // Cleanup WebDriver
    }
}
