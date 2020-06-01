package ru.geekbrains.nvgostev.junit.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInPage extends BaseActions {
    private static final By SIGN_IN_PAGE_HEADER = By.cssSelector(".page-heading");
    private static final By EMAIL_REGISTRATION = By.cssSelector("#email_create");
    private static final By GO_TO_REGISTRATION_BTN = By.cssSelector("#SubmitCreate");

    public boolean isSignInPageHeaderPresent() {
        return isElementPresent(SIGN_IN_PAGE_HEADER);
    }

    public String getSignInPageHeaderText() {
        return driver.findElement(SIGN_IN_PAGE_HEADER).getText();
    }

    public void generateNewRandomEmailAccountAndGoToRegistration(int emailNameLength) {
        StringBuilder emailBuilder = new StringBuilder();
        for (int i = 0; i<emailNameLength; i++) {
            emailBuilder.append((char)(97 + (int)(Math.random() * 26)));
        }
        emailBuilder.append("@gmail.com");
        type(emailBuilder.toString(), EMAIL_REGISTRATION);
        click(GO_TO_REGISTRATION_BTN);
    }

    public SignInPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
}
