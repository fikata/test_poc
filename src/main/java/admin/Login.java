package admin;


import org.openqa.selenium.By;

import static webdriver.ChromeBrowser.driver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import webdriver.ChromeBrowser;


public class Login{

    //private final WebDriver driver = new ChromeDriver();

    public static String admin_URL = "https://www.phptravels.net/admin";

    public static String form_email_FIELD = "//input[@name='email']";
    public static String form_password_FIELD = "//input[@name='password']";
    public static String submit_BTN = "//button[@type='submit']";

    public static String logout_BTN = "//a[contains(text(),'Log Out')]";

    //static final String admin_username = "admin@phptravels.com";
    //static final String admin_password = "demouser";

    @Given("^I'm login to the admin panel with user = (.*?) and password = (.*?)$")
    public void loginIntoAdmin(String email, String password) throws Throwable { //String email, String password
        Thread.sleep(3000); // TODO use waitforelement
        driver.findElement(By.xpath(form_email_FIELD)).sendKeys(email);
        driver.findElement(By.xpath(form_password_FIELD)).sendKeys(password);
        driver.findElement(By.xpath(submit_BTN)).click();
        ChromeBrowser.waitForElement(logout_BTN);

    }

    @Given("^I'm going to Admin page$")
    public void goToAdmin(){
        driver.get(admin_URL);
    }
}

