package ru.geekbrains.nvgostev.junit.tests;


import org.testng.annotations.Test;
import ru.geekbrains.nvgostev.junit.pom.MainPage;
import ru.geekbrains.nvgostev.junit.pom.SignInPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MainPageTests extends BaseUITest {

    @Test
    public void goToSignInPageTest() {
        SignInPage signInPage = new MainPage(getDriver(), getWait())
                .home()
                .getHeader().sigIn();
        assertThat("Page header is presented", signInPage.isPageHeaderPresent(), equalTo(true));
        assertThat("Page header text is correct", signInPage.getPageHeaderText(), equalToIgnoringCase("authentication"));
    }
}
