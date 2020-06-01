package ru.geekbrains.nvgostev.junit.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage extends BaseActions {

    public static final By REGISTRATION_PAGE_FORM = By.cssSelector("#account-creation_form");
    public static final By REGISTRATION_PAGE_FORM_SUBTITLE = By.cssSelector(".page-subheading");
    public static final By FIRST_NAME = By.cssSelector("#customer_firstname");
    public static final By LAST_NAME = By.cssSelector("customer_lastname");
    public static final By PASSWORD = By.cssSelector("#passwd");
    public static final By ADDRESS = By.cssSelector("#address1");
    public static final By CITY = By.cssSelector("#city");
    public static final By STATE = By.cssSelector("#id_state");
    public static final By ZIP = By.cssSelector("#postcode");
    public static final By MOBILE = By.cssSelector("#phone_mobile");
    public static final By SUBMIT_BTN = By.cssSelector("#submitAccount");

    public RegistrationPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean isRegistrationPageFormPresent() {
        return isElementPresent(REGISTRATION_PAGE_FORM);
    }

    public String getRegistrationPageFormText() {
        return driver.findElement(REGISTRATION_PAGE_FORM_SUBTITLE).getText();
    }

    public void registrationFormFill(String[] data) {
        type(data[0], FIRST_NAME);
        type(data[1], LAST_NAME);
        type(data[2], PASSWORD);
        type(data[3], ADDRESS);
        type(data[4], CITY);
        Select select = new Select(driver.findElement(STATE));
        select.selectByIndex(Integer.parseInt(data[5]));
        type(data[6], ZIP);
        type(data[7], MOBILE);
        click(SUBMIT_BTN);
    }
}
