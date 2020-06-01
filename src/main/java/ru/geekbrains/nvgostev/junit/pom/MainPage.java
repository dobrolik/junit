package ru.geekbrains.nvgostev.junit.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage extends BaseActions {
    private static final String HOMEPAGE_URL = "http://automationpractice.com/";

    private static final By SIGN_IN_BTN = By.cssSelector(".login");

    public void home() {
        driver.get(HOMEPAGE_URL);
    }

    public void goToSignIn() {
        click(SIGN_IN_BTN);
    }

    public MainPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
}
