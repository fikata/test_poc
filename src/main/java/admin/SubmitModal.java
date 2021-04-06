package admin;
import helpers.StoreData;
import org.openqa.selenium.By;
import static webdriver.ChromeBrowser.driver;
import cucumber.api.java.en.Given;
import org.testng.Assert;
import webdriver.ChromeBrowser;


public class SubmitModal {
    public static String titleModal_TEXT = "example-modal-sizes-title-lg";
    public static String firstNameValue_FIELD = "//td[contains(text(),'Student Name')]/following::td[1]";
    public static String emailValue_FIELD = "//td[contains(text(),'Student Email')]/following::td[1]";
    public static String genderValue_FIELD = "//td[contains(text(),'Gender')]/following::td[1]";
    public static String mobilePhoneValue_FIELD = "//td[contains(text(),'Mobile')]/following::td[1]";
    public static String dateOfBirthValue_FIELD = "//td[contains(text(),'Date of Birth')]/following::td[1]";
    public static String fileNameValue_FIELD = "//td[contains(text(),'Picture')]/following::td[1]";
    public static String stateAndCityValue_FIELD = "//td[contains(text(),'State and City')]/following::td[1]";

    @Given("^I'm asserting all fields$")
    public void assertAllFields() throws Throwable {
        ChromeBrowser.waitForElementById(titleModal_TEXT, 3);
        StoreData inputData = Form.generateInputData();
        Assert.assertEquals(this.getFirstName(firstNameValue_FIELD),
                inputData.getData("firstName") + " " + inputData.getData("lastName") );

        Assert.assertEquals(this.getEmail(emailValue_FIELD), inputData.getData("userEmail") );
        Assert.assertEquals(this.getPhone(mobilePhoneValue_FIELD), inputData.getData("mobilePhone") );
        Assert.assertEquals(this.getDate(dateOfBirthValue_FIELD), inputData.getData("dateOfBirth") );
        Assert.assertEquals(this.getFile(fileNameValue_FIELD), inputData.getData("file") );
        Assert.assertEquals(this.getRadio(genderValue_FIELD), inputData.getData("radio") );
    }

    public String getFirstName(String locator) {
        return driver.findElement(By.xpath(locator)).getText();
    }

    public String getEmail(String locator) {
        return driver.findElement(By.xpath(locator)).getText();
    }

    public String getPhone(String locator) {
        return driver.findElement(By.xpath(locator)).getText();
    }

    public String getDate(String locator) {
        return driver.findElement(By.xpath(locator)).getText();
    }

    public String getFile(String locator) {
        return driver.findElement(By.xpath(locator)).getText();
    }

    public String getRadio(String locator) {
        return driver.findElement(By.xpath(locator)).getText();
    }
}
