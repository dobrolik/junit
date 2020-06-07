package ru.geekbrains.nvgostev.junit.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ru.geekbrains.nvgostev.junit.pom.*;

public abstract class BaseUITest {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driver.get();
    }

    public static WebDriverWait getWait() {
        return wait.get();
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUpDriver(@Optional("chrome") String browser) {
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;
        }
        driver.get().manage().window().maximize();
        wait.set(new WebDriverWait(driver.get(), 8));
    }

    protected MainPage mainPage;
    protected SignInPage signInPage;
    protected RegistrationPage registrationPage;
    protected AccountPage accountPage;
    protected ShopPage shopPage;
    protected ShoppingCart shoppingCart;

    @AfterMethod
    public void shutdown() {
        driver.get().quit();
    }
}
