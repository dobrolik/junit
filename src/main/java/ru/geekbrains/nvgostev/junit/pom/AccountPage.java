package ru.geekbrains.nvgostev.junit.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountPage extends BaseActions{

    public static final By ACCOUNT_HEADER = By.cssSelector(".page-heading");

    public boolean isAccountPageHeaderPresent() {
        return isElementPresent(ACCOUNT_HEADER);
    }

    public String getAccountPageHeaderText() {
        return driver.findElement(ACCOUNT_HEADER).getText();
    }

    public AccountPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
}
