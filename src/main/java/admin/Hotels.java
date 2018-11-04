package admin;

import cucumber.api.java.en.Given;
import helpers.StoreData;
import org.openqa.selenium.By;
import webdriver.ChromeBrowser;
import static webdriver.ChromeBrowser.driver;


public class Hotels {

    public static String hotels_main_menu_FIELD = "//a[@href='#Hotels']";
    public static String rooms_category_FIELD = "//a[contains(text(),'Rooms')]"; // too many elements i get first, working for now
    public static String edit_first_room_BTN = "(//a[@title='Edit'])[1]";


    public void selectCategoryHotel() {
        ChromeBrowser.waitForElement(hotels_main_menu_FIELD);
        driver.findElement(By.xpath(hotels_main_menu_FIELD)).click();
    }

    public void selectSubCategoryRooms() {
        ChromeBrowser.waitForElement(rooms_category_FIELD);
        driver.findElement(By.xpath(rooms_category_FIELD)).click();
    }

    public void editFirstRoom() {
        ChromeBrowser.waitForElement(edit_first_room_BTN);
        driver.findElement(By.xpath(edit_first_room_BTN)).click();
        ChromeBrowser.waitForElement(Edit.edit_form_submit_BTN);
    }

    @Given("^Going to open Hotels section and edit first room from section Rooms$")
    public void stepHotels(){
        this.selectCategoryHotel();
        this.selectSubCategoryRooms();
        this.editFirstRoom();
    }
}