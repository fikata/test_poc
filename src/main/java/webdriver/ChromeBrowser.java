package webdriver;


import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;



import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import helpers.StoreData;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.awt.*;
import java.io.File;
import java.io.IOException;


public class ChromeBrowser{

    public static WebDriver driver;

    @And("^I'm going to run Browser$")
    public void selectBrowser() throws Throwable{
        String projectPath = System.getProperty("user.dir");
        if ( System.getProperty("os.name").startsWith("Mac")){
            if (System.getenv("browser") != null) { //handle null pointer
                if (System.getenv("browser").equals("chrome") ) {
                    System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/mac/chromedriver");
                    driver = new ChromeDriver();
                } else if (System.getenv("browser").equals("firefox")) {
                    System.setProperty("webdriver.gecko.driver", projectPath + "/drivers/mac/geckodriver");
                    driver = new FirefoxDriver();
                }
                else {
                    System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/mac/chromedriver");
                    driver = new ChromeDriver();
                }
            }
             else {
                System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/mac/chromedriver");
                driver = new ChromeDriver();
            }
        } else if (System.getProperty("os.name").startsWith("Windows")) {
            if (System.getenv("browser") != null) {
                if (System.getenv("browser").equals("chrome")){
                    System.setProperty("webdriver.chrome.driver", "");
                    driver = new ChromeDriver();
                } else if (System.getenv("browser").equals("firefox")) {
                    System.setProperty("webdriver.gecko.driver","");
                    driver = new FirefoxDriver();

                } else {
                    System.setProperty("webdriver.chrome.driver", "");
                    driver = new ChromeDriver();
                }
            }
            else {
                System.setProperty("webdriver.chrome.driver", "");
                driver = new ChromeDriver();
            }
        } else {
            System.out.println("Linux or cannot detect, manually download the drivers");
        }

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

    public static void waitForElementById(String id_locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(By.id(id_locator)));
    }

    public static void scrollToElement(String xpath_locator){
        WebElement element = driver.findElement(By.xpath(xpath_locator));
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public static void takeScreenshot(String pathname) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(pathname));
    }
}
