package admin;

import helpers.StoreData;
import org.openqa.selenium.*;
import static webdriver.ChromeBrowser.driver;
import cucumber.api.java.en.Given;
import webdriver.ChromeBrowser;

public class Form{

    public static String demoqaForm_URL = "https://www.demoqa.com/automation-practice-form";
    public static String firstName_FIELD = "firstName";
    public static String lastName_FIELD = "lastName";
    public static String userEmail_FIELD = "userEmail";
    public static String genderMale_RADIO = "//label[@for='gender-radio-1']";
    public static String mobilePhone_FIELD = "userNumber";
    public static String dateOfBirth_CALENDAR = "dateOfBirthInput"; //datepicker
    public static String hobbies_CHECK = "hobbies-checkbox-1";
    public static String state_SELECT = "react-select-3-input";
    public static String uploadFile_FIELD = "//input[@id='uploadPicture']";
    public static String submitForm_BTN = "submit";
    public static String form_email_FIELD = "//input[@name='email']";
    public static String form_password_FIELD = "//input[@name='password']";
    public static String submit_BTN = "//button[@type='submit']";
    public static String logout_BTN = "//a[contains(text(),'Log Out')]";

    @Given("^I'm fill all fields with valid data firstname etc$")
    public void fillFormWithAllValues() throws Throwable {
        ChromeBrowser.waitForElementById(firstName_FIELD, 3);
        StoreData data = generateInputData();

        driver.findElement(By.id(firstName_FIELD)).sendKeys(data.getData("firstName"));
        driver.findElement(By.id(lastName_FIELD)).sendKeys(data.getData("lastName"));
        driver.findElement(By.id(userEmail_FIELD)).sendKeys(data.getData("userEmail"));
        driver.findElement(By.xpath(genderMale_RADIO)).click();
        driver.findElement(By.id(mobilePhone_FIELD)).sendKeys(data.getData("mobilePhone"));
        datePickerClearAndAddValue(data.getData("dateOfBirth"));
        uploadFile();
        submitForm();
    }

    public static void datePickerClearAndAddValue(String value) {
        driver.findElement(By.id(dateOfBirth_CALENDAR)).sendKeys(Keys.COMMAND,"a");
        driver.findElement(By.id(dateOfBirth_CALENDAR)).sendKeys(Keys.CONTROL,"a");
        driver.findElement(By.id(dateOfBirth_CALENDAR)).sendKeys(value);
    }

    public static void uploadFile() {
        String ptojectPath = System.getProperty("user.dir");
        String fileName = ptojectPath + "/file_to_upload.txt";
        System.out.println(fileName);
        driver.findElement(By.xpath(uploadFile_FIELD)).sendKeys(fileName);
    }

    public static void submitForm() throws Throwable {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement Element = driver.findElement(By.id(submitForm_BTN));
        js.executeScript("arguments[0].scrollIntoView();", Element);
        driver.findElement(By.id(submitForm_BTN)).click();
        Thread.sleep(5000);
    }

    public static StoreData generateInputData() {
        StoreData data = new StoreData();
        data.addData("firstName","Gosho");
        data.addData("lastName","Mosho");
        data.addData("userEmail","email@email.email");
        data.addData("mobilePhone","2345234523");
        data.addData("dateOfBirth","01 January,1999");
        data.addData("radio","Male");
        data.addData("file","file_to_upload.txt");

        return data;
    }

    @Given("^I'm landing directly to the form$")
    public void goToAdmin(){
        driver.get(demoqaForm_URL);
    }
}

