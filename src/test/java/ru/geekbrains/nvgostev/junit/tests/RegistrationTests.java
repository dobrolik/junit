package ru.geekbrains.nvgostev.junit.tests;


import com.sun.org.glassfish.gmbal.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.geekbrains.nvgostev.junit.common.Account;
import ru.geekbrains.nvgostev.junit.common.AccountDataGenerator;
import ru.geekbrains.nvgostev.junit.pom.AccountPage;
import ru.geekbrains.nvgostev.junit.pom.MainPage;
import ru.geekbrains.nvgostev.junit.pom.RegistrationPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.geekbrains.nvgostev.junit.common.Account.Field.*;


public class RegistrationTests extends BaseUITest {
    private static AccountDataGenerator accDataGenerator;

    @BeforeClass
    private void prepareAccountGenerator() {
        accDataGenerator = new AccountDataGenerator(20);
    }

    @DataProvider(name = "CreateNewAccountDataSupplier")
    private static Object[][] createNewAccountTestDataSupplier() {
        return accDataGenerator.genAccounts(5).stream()
                .map(account -> new Object[]{account})
                .toArray(Object[][]::new);
    }

    @Test(
            description = "Checks: Create an account - Register with valid data",
            dataProvider = "CreateNewAccountDataSupplier"
    )
    public void createNewAccountTest(Account account) {
        AccountPage accountPage = new MainPage(getDriver(), getWait())
                .home()
                .getHeader().sigIn()
                .enterEmailAndGoToRegistration(account.getEmail())
                .fillFormAndSubmit(account);
        assertThat("Page header is presented", accountPage.isPageHeaderPresent(), equalTo(true));
        assertThat("Page header text is correct", accountPage.getPageHeaderText(), equalToIgnoringCase("my account"));
    }

    @DataProvider(name = "registrationFailDataSupplier")
    private static Object[][] registrationFailTestDataSupplier() {
        return new Object[][]{
                {accDataGenerator.genAccount(),
                        new Account.Field[]{FIRST_NAME, LAST_NAME, EMAIL, PASSWD},
                        new String[]{RegistrationPage.Alert.FIRST_NAME_REQ.getText(), RegistrationPage.Alert.LAST_NAME_REQ.getText(), RegistrationPage.Alert.EMAIL_REQ.getText(), RegistrationPage.Alert.PASSWD_REQ.getText()}
                },
                {accDataGenerator.genAccount(),
                        new Account.Field[]{PASSWD, ADDR_FIRST_NAME, ADDR_LAST_NAME, ADDRESS},
                        new String[]{RegistrationPage.Alert.PASSWD_REQ.getText(), RegistrationPage.Alert.FIRST_NAME_REQ.getText(), RegistrationPage.Alert.LAST_NAME_REQ.getText(), RegistrationPage.Alert.ADDR_REQ.getText()}
                },
                {accDataGenerator.genAccount(),
                        new Account.Field[]{ADDRESS, CITY, STATE, ZIP},
                        new String[]{RegistrationPage.Alert.ADDR_REQ.getText(), RegistrationPage.Alert.CITY_REQ.getText(), RegistrationPage.Alert.STATE_REQ.getText(), RegistrationPage.Alert.ZIP_REQ.getText()}
                },
                {accDataGenerator.genAccount(),
                        new Account.Field[]{COUNTRY, PHONE},
                        new String[]{RegistrationPage.Alert.COUNTRY_REQ.getText(), RegistrationPage.Alert.PHONE_REQ.getText()}
                },
                {accDataGenerator.genAccount(),
                        new Account.Field[]{PHONE, ADDR_ALIAS},
                        new String[]{RegistrationPage.Alert.PHONE_REQ.getText(), RegistrationPage.Alert.ADDR_ALIAS_REQ.getText()}
                }
        };
    }

    @Test(
            description = "Checks: Create an account - Register with invalid data (missing some fields)",
            dataProvider = "registrationFailDataSupplier"
    )
    public void registrationFailTest(Account account, Account.Field[] fieldsToBreak, String[] expectedAlerts) {
        RegistrationPage registrationPage = new MainPage(getDriver(), getWait())
                .home()
                .getHeader().sigIn()
                .enterEmailAndGoToRegistration(account.getEmail());
        for (Account.Field fieldToBreak : fieldsToBreak) {
            account.setField(fieldToBreak, "");
        }
        registrationPage.fillFormAndSubmit(account);
        assertThat("Registration fail alert is presented", registrationPage.isAlertPresent(), equalTo(true));
        String alert = registrationPage.getAlertText();
        for (String expectedAlert : expectedAlerts) {
            assertThat("Alert text contains", alert, containsString(expectedAlert));
        }
    }
}