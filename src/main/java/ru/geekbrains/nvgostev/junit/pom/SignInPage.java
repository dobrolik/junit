package ru.geekbrains.nvgostev.junit.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInPage extends ContentPage {
    private static final By EMAIL_REGISTRATION = By.cssSelector("#email_create");
    private static final By GO_TO_REGISTRATION_BTN = By.cssSelector("#SubmitCreate");
    private static final By EMAIL_SIGN_IN_INPUT = By.cssSelector("#email");
    private static final By PASSWD_SIGN_IN_INPUT = By.cssSelector("#passwd");
    private static final By SIGN_IN_BTN = By.cssSelector("#SubmitLogin");

    public SignInPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public RegistrationPage enterEmailAndGoToRegistration(String email) {
        type(email, EMAIL_REGISTRATION);
        click(GO_TO_REGISTRATION_BTN);
        return new RegistrationPage(driver, wait);
    }

    public AccountPage signIn(String email, String password) {
        type(email, EMAIL_SIGN_IN_INPUT);
        type(password, PASSWD_SIGN_IN_INPUT);
        click(SIGN_IN_BTN);
        return new AccountPage(driver, wait);
    }

}
