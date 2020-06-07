package ru.geekbrains.nvgostev.junit.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage extends ContentPage {
    private static final String HOMEPAGE_URL = "http://automationpractice.com/";

    @Override
    public boolean isPageHeaderPresent() {
        return false;
    }

    @Override
    public String getPageHeaderText() {
        return "";
    }

    public MainPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
       }

     public MainPage home() {
        driver.get(HOMEPAGE_URL);
        return this;
    }

}
