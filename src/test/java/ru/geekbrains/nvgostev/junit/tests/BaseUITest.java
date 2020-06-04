package ru.geekbrains.nvgostev.junit.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.geekbrains.nvgostev.junit.pom.*;

public abstract class BaseUITest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected MainPage mainPage;
    protected SignInPage signInPage;
    protected RegistrationPage registrationPage;
    protected AccountPage accountPage;
    protected ShopPage shopPage;
    protected ShoppingCart shoppingCart;


    @BeforeEach
    public void init() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 4);
        mainPage = new MainPage(driver, wait);
        signInPage = new SignInPage(driver, wait);
        registrationPage = new RegistrationPage(driver, wait);
        accountPage = new AccountPage(driver, wait);
        shopPage = new ShopPage(driver, wait);
        shoppingCart = new ShoppingCart(driver, wait);
    }

    @AfterEach
    public void shutdown() {
        driver.quit();
    }
}
