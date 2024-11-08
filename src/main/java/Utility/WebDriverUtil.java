package Utility;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class WebDriverUtil {

    // Set a wait to handle cookie popup
    public static void handleCookiePopup(WebDriver driver, WebDriverWait wait, String cookieName, String acceptButtonId) {
        Cookie optanonCookie = driver.manage().getCookieNamed(cookieName);
        if (optanonCookie == null) {
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(acceptButtonId)));
            acceptButton.click();
        }
    }

    // Extract vehicle details using JavaScript and return as a map
    public static Map<String, String> extractVehicleDetails(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Map<String, String>) js.executeScript(
                "var elements = document.querySelectorAll('div.d-table-row.details-vehicle-row');" +
                        "var details = {};" +
                        "for (var i = 0; i < elements.length; i++) {" +
                        "    var heading = elements[i].querySelector('.heading');" +
                        "    if (heading && heading.innerText.includes('Manufacturer:')) {" +
                        "        var make = elements[i].querySelector('.value');" +
                        "        if (make) details.make = make.innerText;" +
                        "    }" +
                        "    if (heading && heading.innerText.includes('Model:')) {" +
                        "        var model = elements[i].querySelector('.value');" +
                        "        if (model) details.model = model.innerText;" +
                        "    }" +
                        "    if (heading && heading.innerText.includes('Year:')) {" +
                        "        var year = elements[i].querySelector('.value');" +
                        "        if (year) details.year = year.innerText;" +
                        "    }" +
                        "}" +
                        "return details;"
        );
    }
}