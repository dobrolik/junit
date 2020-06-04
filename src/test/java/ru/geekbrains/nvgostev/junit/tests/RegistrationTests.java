package ru.geekbrains.nvgostev.junit.tests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.geekbrains.nvgostev.junit.common.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTests extends BaseUITest {

    @Disabled
    @ParameterizedTest
    @MethodSource(value = "genDataForRegistration")
    public void registrationPageTest(Account account) {
        mainPage.home();
        mainPage.goToSignIn();
        signInPage.generateNewRandomEmailAccountAndGoToRegistration(7);
        assertTrue(registrationPage.isRegistrationPageFormPresent());
        assertEquals("your personal information", registrationPage.getRegistrationPageFormText().toLowerCase());
        registrationPage.fillFormAndSubmit(account);
        assertTrue(accountPage.isPageHeaderPresent());
        assertEquals("my account", accountPage.getPageHeaderText().toLowerCase());
    }

    public static Stream<Arguments> genDataForRegistration() {
        String[] firstNames = {"Bob", "Ivan", "Peter", "Jack", "Robin"};
        String[] lastNames = {"Bobson", "Ivanson", "Peterson", "Jackson", "Robinson"};

        List<Arguments> out = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            out.add(Arguments.of(new String[]{
                    getRandomItem(firstNames),
                    getRandomItem(lastNames),
                    generateNumSequence(6),
                    "qweasd",
                    "qweasd",
                    "1",
                    generateNumSequence(6),
                    generateNumSequence(9)
            }
                    ));
        }
        return out.stream();
    }

    public static String generateNumSequence (int numLength) {
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < numLength; i++) {
            num.append((char) (48 + (int) (Math.random() * 10)));
        }
        return num.toString();
    }

    public static String getRandomItem(String[] arr) {
        return arr[(int)(Math.random()*(arr.length))];
    }

}
