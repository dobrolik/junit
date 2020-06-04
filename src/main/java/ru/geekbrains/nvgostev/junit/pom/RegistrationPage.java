package ru.geekbrains.nvgostev.junit.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.geekbrains.nvgostev.junit.common.Account;

public class RegistrationPage extends BaseActions {
    public static final By REGISTRATION_PAGE_FORM = By.cssSelector("#account-creation_form");
    public static final By REGISTRATION_PAGE_FORM_SUBTITLE = By.cssSelector(".page-subheading");

    private static final By FIRST_NAME_INPUT = By.cssSelector("#customer_firstname");
    private static final By LAST_NAME_INPUT = By.cssSelector("#customer_lastname");
    private static final By EMAIL_INPUT = By.cssSelector("#email");
    private static final By PASSWD_INPUT = By.cssSelector("#passwd");
    private static final By ADDR_FIRST_NAME_INPUT = By.cssSelector("[id=firstname]");
    private static final By ADDR_LAST_NAME_INPUT = By.cssSelector("[id=lastname]");
    private static final By ADDR_INPUT = By.cssSelector("#address1");
    private static final By CITY_INPUT = By.cssSelector("#city");
    private static final By STATE_SELECT = By.cssSelector("#id_state");
    private static final By ZIP_INPUT = By.cssSelector("#postcode");
    private static final By COUNTRY_SELECT = By.cssSelector("#id_country");
    private static final By PHONE_INPUT = By.cssSelector("#phone_mobile");
    private static final By ADDR_ALIAS_INPUT = By.cssSelector("#alias");

    private static final By REGISTER_BTN = By.cssSelector("#submitAccount");

    private static final By ALERT_DIV = By.cssSelector("#center_column .alert");

    public RegistrationPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String getRegistrationPageFormText() {
        return driver.findElement(REGISTRATION_PAGE_FORM_SUBTITLE).getText();
    }

    public boolean isRegistrationPageFormPresent() {
        return isElementPresent(REGISTRATION_PAGE_FORM);
    }

    public void fillFormAndSubmit(Account acc) {
        type(acc.getFirstName(), FIRST_NAME_INPUT);
        type(acc.getLastName(), LAST_NAME_INPUT);
        type(acc.getEmail(), EMAIL_INPUT);
        type(acc.getPassword(), PASSWD_INPUT);
        type(acc.getAddrFirstName(), ADDR_FIRST_NAME_INPUT);
        type(acc.getAddrLastName(), ADDR_LAST_NAME_INPUT);
        type(acc.getAddress(), ADDR_INPUT);
        type(acc.getCity(), CITY_INPUT);
        select(acc.getStateId(), STATE_SELECT);
        type(acc.getZip(), ZIP_INPUT);
        select(acc.getCountryId(), COUNTRY_SELECT);
        type(acc.getMobilePhone(), PHONE_INPUT);
        type(acc.getAddressAlias(), ADDR_ALIAS_INPUT);
        click(REGISTER_BTN);
    }

}

