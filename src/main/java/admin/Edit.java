package admin;

import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import webdriver.ChromeBrowser;

import helpers.StoreData;

import java.util.Map;

import static webdriver.ChromeBrowser.driver;

public class Edit {


    public static String edit_form_submit_BTN = "//button[@id='update']";

    public static String edit_price_rooms_FIELD = "//input[@name='basicprice']";

    public static String selected_room_type_FIELD = "(//label[contains(text(),'Room Type')]/following::span[@class='select2-chosen'])[1]";
    public static String selected_hotel_FIELD = "(//label[contains(text(),'Hotel')]/following::span[@class='select2-chosen'])[1]";

    public static String logout_BTN = "";




    public void changeRoomPrice(double newPrice){
        ChromeBrowser.waitForElement(edit_price_rooms_FIELD);
        driver.findElement(By.xpath(edit_price_rooms_FIELD)).clear();
        driver.findElement(By.xpath(edit_price_rooms_FIELD)).sendKeys(String.valueOf(newPrice));
        driver.findElement(By.xpath(edit_form_submit_BTN)).click();
    }

    public static String getRoomPrice(){
        ChromeBrowser.waitForElement(edit_price_rooms_FIELD);
        return driver.findElement(By.xpath(edit_price_rooms_FIELD)).getText();
    }

    public static String getHotelName(){
        ChromeBrowser.waitForElement(selected_hotel_FIELD);
        return driver.findElement(By.xpath(selected_hotel_FIELD)).getText();
    }

    public static String getRoomName(){
        ChromeBrowser.waitForElement(selected_room_type_FIELD);
        return driver.findElement(By.xpath(selected_room_type_FIELD)).getText();
    }

    public void logoutFromAdmin(){
        ChromeBrowser.waitForElement(logout_BTN);
        driver.findElement(By.xpath(logout_BTN)).click();
        ChromeBrowser.waitForElement(Form.submit_BTN);
    }

    @Given("^I'm going to change the price = (.*?) and get values for later use$")
    public void stepEdit(Double price){
        this.changeRoomPrice(price);
        StoreData.getInstance().addData("room_name",Edit.getRoomName());
        StoreData.getInstance().addData("hotel_name",Edit.getHotelName());
        StoreData.getInstance().addData("room_price",Edit.getRoomPrice());

    }
}
