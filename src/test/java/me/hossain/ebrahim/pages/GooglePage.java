package me.hossain.ebrahim.pages;

import me.hossain.ebrahim.drivers.PageDriver;
import me.hossain.ebrahim.utils.ExcelUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static me.hossain.ebrahim.drivers.BaseDriver.driver;

public class GooglePage {

    public GooglePage() {
        PageFactory.initElements(PageDriver.getCurrentDriver(), this);
    }

    // Locator for the Google Search Box
    @FindBy(name = "q")
    WebElement searchBox;

    public void googleSearch(String sheetName) throws Exception {
        List<String> keywords = ExcelUtils.readValuesFromExcel(sheetName, 2);
        System.out.println("Keywords: " + keywords);

        for (int i = 0; i < keywords.size(); i++) {
            String keyword = keywords.get(i);
            if (keyword == null || keyword.trim().isEmpty()) {
                System.out.println("Skipping empty or null keyword.");
                continue;
            }

            System.out.println("Keyword: " + keyword);
            searchBox.clear();
            searchBox.sendKeys(keyword);
            Thread.sleep(1500);

            // Wait for the suggestion list to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("wM6W7d")));

            // Find all the suggestion elements
            List<WebElement> suggestions = driver.findElements(By.className("wM6W7d"));

            if (suggestions.isEmpty()) {
                System.out.println("No suggestions found.");
            } else {
                // Initialize longest and shortest suggestions
                String longestSuggestion = "";
                String shortestSuggestion = null;

                for (WebElement suggestion : suggestions) {
                    String text = suggestion.getText();
                    if (text != null && !text.isEmpty()) {
                        if (shortestSuggestion == null || text.length() < shortestSuggestion.length()) {
                            shortestSuggestion = text;
                        }
                        if (text.length() > longestSuggestion.length()) {
                            longestSuggestion = text;
                        }
                    }
                }

                // Print the longest and shortest suggestions
                System.out.println("Longest suggestion: " + (longestSuggestion != null ? longestSuggestion : "None"));
                System.out.println("Shortest suggestion: " + (shortestSuggestion != null ? shortestSuggestion : "None"));
                // Store the longest and shortest suggestions in Excel
                ExcelUtils.writeResultsToExcel(sheetName, i , longestSuggestion, shortestSuggestion); // Row numbers start from 1
            }
        }
    }

}