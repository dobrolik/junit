package ru.geekbrains.nvgostev.junit.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Header extends BaseActions{

    private static final By SIGN_IN_BTN = By.cssSelector(".login");
    private static final By SIGN_OUT_BTN = By.cssSelector(".logout");
    private static final By VIEW_CART_BTN = By.cssSelector(".shopping_cart > a");
    private static final By WOMEN_BTN = By.cssSelector("a[title=\"Women\"]");
    private static final By DRESSES_BTN = By.cssSelector("#block_top_menu > ul > li > a[title=\"Dresses\"]");
    private static final By T_SHIRTS_BTN = By.cssSelector("#block_top_menu > ul > li > a[title=\"T-shirts\"]");


    public Header(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void sigIn() {
        if (isElementPresent(SIGN_IN_BTN)) {
            click(SIGN_IN_BTN);
        } else {
            throw new RuntimeException("No sign-in button. Possible reason: already signed in");
        }
    }

    public void signOut() {
        if (isElementPresent(SIGN_OUT_BTN)) {
            click(SIGN_OUT_BTN);
        } else {
            throw new RuntimeException("No sign-out button. Possible reason: not signed in");
        }
    }

    public void viewCart() {
        click(VIEW_CART_BTN);
    }

    public void openCategory(ShoppingCategory category) {
        switch (category) {
            case WOMEN:
                click(WOMEN_BTN);
                break;
            case DRESSES:
                click(DRESSES_BTN);
                break;
            case T_SHIRTS:
                click(T_SHIRTS_BTN);
                break;
        }
    }

    public enum ShoppingCategory {
        WOMEN,
        DRESSES,
        T_SHIRTS
    }

}
