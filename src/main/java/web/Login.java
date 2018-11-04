package web;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import gherkin.lexer.Th;
import helpers.ParseString;
import helpers.StoreData;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.AssertJUnit;
import webdriver.ChromeBrowser;

import java.security.Key;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static webdriver.ChromeBrowser.driver;
import static webdriver.ChromeBrowser.waitForElement;


public class Login {

    public static String web_URL = "https://www.phptravels.net/";

    public static String my_account_FIELD = "(//*[contains(text(),'My Account')])[2]";
    public static String login_FIELD = "(//*[contains(text(),'Login')])[2]";

    public static String username_FIELD = "//input[@name='username']";
    public static String password_FIELD = "//input[@name='password']";
    public static String login_BTN = "(//button[@type='submit'])[1]";
    public static String verify_login_FIELD = "//h3[contains(text(),'Hi,')]";

    public static String section_hotels_FIELD  = "//a[@title='Hotels']";

    public static String hotel_or_city_FIELD = "//a[@class='select2-choice']";
    public static String search_hotel_FIELD = "//div[@class='select2-search']";
    public static String search_result_FIELD = "(//*[@class='select2-result-label'])[2]";
    public static String check_in_date_FIELD = "//input[@name='checkin']";
    public static String check_out_date_FIELD = "//input[@name='checkout']";
    public static String search_BTN = "(//button[contains(text(),'Search')])[1]";

    public static String book_now_BTN = "(//button[contains(text(),'Book Now')])[1]";
    public static String room_price_FIELD = "(//b[contains(text(),'%s')]/following::h2/strong)[1]";
    public static String room_select_CHECK = "(//b[contains(text(),'%s')]/following::div[@class='control__indicator'])[1]";

    public static String confirm_booking_BTN = "//button[contains(text(),'CONFIRM THIS BOOKING')]";
    public static String tax_vat_amount_FIELD = "//*[@id='displaytax']";
    public static String deposit_now_FIELD = "//*[@id='displaydeposit']";
    public static String total_amount_FIELD = "//*[@id='displaytotal']";

    @Given("Go to the web page")
    public void goToWebPage(){
        driver.get(web_URL);
    }

    @Given("I'm navigating to the login form")
    public void goToLoginWeb() throws Throwable{
//        Thread.sleep(2000);
        ChromeBrowser.waitForElement(my_account_FIELD);
        driver.findElement(By.xpath(my_account_FIELD)).click();
        ChromeBrowser.waitForElement(login_FIELD);
        driver.findElement(By.xpath(login_FIELD)).click();
    }

    @Given("^I'm login into web page with user = (.*?) and password = (.*?)$")
    public void loginInWeb(String email, String password){
        ChromeBrowser.waitForElement(username_FIELD);
        driver.findElement(By.xpath(username_FIELD)).sendKeys(email);
        ChromeBrowser.waitForElement(password_FIELD);
        driver.findElement(By.xpath(password_FIELD)).sendKeys(password);
        driver.findElement(By.xpath(login_BTN)).click();
        ChromeBrowser.waitForElement(verify_login_FIELD);
    }

    public void goToHotels(){
        ChromeBrowser.waitForElement(section_hotels_FIELD);
        driver.findElement(By.xpath(section_hotels_FIELD)).click();
    }

    @Given("Search by hotel name and select it")
    public void searchByHotelCity() throws Throwable{
        this.goToHotels();
        ChromeBrowser.waitForElement(hotel_or_city_FIELD);
        driver.findElement(By.xpath(hotel_or_city_FIELD)).click();
        //Thread.sleep(3000);
        driver.findElement(By.xpath(hotel_or_city_FIELD)).sendKeys(StoreData.getInstance().getData("hotel_name"));
        Thread.sleep(5000);
        driver.findElement(By.xpath(search_result_FIELD)).click();
        this.selectCheckInDate();
        this.selectCheckOutDate();
        driver.findElement(By.xpath(search_BTN)).click();
        ChromeBrowser.waitForElement(book_now_BTN);
    }

    public void selectCheckInDate() throws Throwable{
        ChromeBrowser.waitForElement(check_in_date_FIELD);
        //Thread.sleep(3000);
        driver.findElement(By.xpath(check_in_date_FIELD)).click();
        driver.findElement(By.xpath(check_in_date_FIELD)).sendKeys("11/11/2018");
    }

    public void selectCheckOutDate() throws Throwable{
        ChromeBrowser.waitForElement(check_out_date_FIELD);
        //Thread.sleep(3000);
        driver.findElement(By.xpath(check_out_date_FIELD)).click();
        driver.findElement(By.xpath(check_out_date_FIELD)).sendKeys("12/11/2018");
    }

    public void selectRoom() throws Throwable{
        String locator = String.format(room_select_CHECK, StoreData.getInstance().getData("room_name"));
        System.out.println(locator);
        Thread.sleep(2000);
        ChromeBrowser.scrollToElement(locator);
        driver.findElement(By.xpath(locator)).click();
    }

    public void getRoomPrice() throws Throwable{
        String locator = String.format(room_price_FIELD, StoreData.getInstance().getData("room_name"));
        System.out.println(locator);
        ChromeBrowser.scrollToElement(locator);
        Thread.sleep(2000);
        StoreData.getInstance().addData("book_room_price",driver.findElement(By.xpath(locator)).getText());
        System.out.println(StoreData.getInstance().getData("book_room_price"));
    }

    @Given("^Verify room price during booking will be equal with changed one = (.*?)$")
    public void verifyRoomPrices(String price) throws Throwable{
        this.getRoomPrice();
        String price_book = StoreData.getInstance().getData("book_room_price");
        price_book = ParseString.getNumberFromString(price_book);
        price = ParseString.getNumberFromString(price);
        System.out.println(price);
        System.out.println(price_book);
        Assert.assertEquals(price_book,price);
    }

    public void bookRoom(){
        ChromeBrowser.waitForElement(book_now_BTN);
        driver.findElement(By.xpath(book_now_BTN)).click();
    }

    @Given("Go to book page")
    public void bookNow() throws Throwable {
        this.selectRoom();
        this.bookRoom();
    }

    @Given("Verify final price and booked the order")
    public void verifyPriceAndConfirmBooking(){
        String parse_string = StoreData.getInstance().getData("book_room_price");
        parse_string = ParseString.getNumberFromString(parse_string);
        Integer price = Integer.parseInt(parse_string);
        Integer tax_vat = (price * 10)/100;
        Assert.assertEquals(this.getTaxVat(), tax_vat);
        Integer total = price + tax_vat;
        Assert.assertEquals(this.getTotalAmount(), total);
        Integer deposit = (total * 10)/100;
        Assert.assertEquals(this.getDeposit(), deposit);

        ChromeBrowser.waitForElement(confirm_booking_BTN);
        driver.findElement(By.xpath(confirm_booking_BTN));

    }

    public Integer getTaxVat(){
        ChromeBrowser.waitForElement(tax_vat_amount_FIELD);
        String string_tax = driver.findElement(By.xpath(tax_vat_amount_FIELD)).getText();
        string_tax = ParseString.getNumberFromString(string_tax);
        Integer tax = Integer.parseInt(string_tax);
        return tax;
    }

    public Integer getDeposit(){
        ChromeBrowser.waitForElement(deposit_now_FIELD);
        String string_tax = driver.findElement(By.xpath(deposit_now_FIELD)).getText();
        string_tax = ParseString.getNumberFromString(string_tax);
        Integer deposit = Integer.parseInt(string_tax);
        return deposit;
    }

    public Integer getTotalAmount(){
        ChromeBrowser.waitForElement(total_amount_FIELD);
        String string_tax = driver.findElement(By.xpath(total_amount_FIELD)).getText();
        string_tax = ParseString.getNumberFromString(string_tax);
        Integer total = Integer.parseInt(string_tax);
        return total;
    }
}
