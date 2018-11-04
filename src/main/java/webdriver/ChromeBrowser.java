package webdriver;


import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;



import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import helpers.StoreData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.awt.*;
import java.io.IOException;


public class ChromeBrowser{

    public static WebDriver driver;
    public static StoreData data;

    @And("^I'm going to run Browser$")
    public void chromeDriver() throws Throwable{
        System.setProperty("webdriver.chrome.driver", "/Users/filip.ninkov/Downloads/chromedriver");
        driver = new ChromeDriver();
    }

    @And("Close Browser")
    public static void tearDown() throws InterruptedException, IOException {
        driver.close();
    }

    public static void waitForElement(String xpath_locator, int timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath_locator)));
    }

    public static void waitForElement(String xpath_locator){
        waitForElement(xpath_locator,10);
    }

    public static void scrollToElement(String xpath_locator){
        WebElement element = driver.findElement(By.xpath(xpath_locator));
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

}
