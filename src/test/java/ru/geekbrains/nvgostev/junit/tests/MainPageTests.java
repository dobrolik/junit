package ru.geekbrains.nvgostev.junit.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class MainPageTests extends BaseUITest {

    @Disabled
    @Test
    public void goToSignInPageTest() {
        mainPage.home();
        mainPage.goToSignIn();
        Assertions.assertTrue(signInPage.isPageHeaderPresent());
        Assertions.assertEquals("authentication", signInPage.getPageHeaderText().toLowerCase());
    }
}
