package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class DriverInstance {

    public WebDriver driver;

    public void setUp(String browser) {
        try {
            // URL of the Selenium Grid Hub
            URL gridUrl = new URL("http://localhost:4444/wd/hub");

            // Initialize options and driver based on the provided browser
            if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions options = new ChromeOptions();
                driver = new RemoteWebDriver(gridUrl, options);
            } else if (browser.equalsIgnoreCase("firefox")) {
                FirefoxOptions options = new FirefoxOptions();
                driver = new RemoteWebDriver(gridUrl, options);
            } else {
                throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Ensure driver is not null before quitting
        }
    }
}