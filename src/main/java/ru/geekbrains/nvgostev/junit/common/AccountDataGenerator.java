package ru.geekbrains.nvgostev.junit.common;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import org.ajbrown.namemachine.Gender;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;
import ru.geekbrains.nvgostev.junit.common.Account;

import java.util.*;

public class AccountDataGenerator {

    private Name[] names;
    private int namesCount;

    private Faker faker;

    private static final int MAX_STATE_ID = 53;
    private static final int US_COUNTRY_ID = 21;

    private static final int CHAR_NUM_MIN = 48;
    private static final int CHAR_NUM_MAX = 57;
    private static final int CHAR_SYMBOL_MIN = 97;
    private static final int CHAR_SYMBOL_MAX = 122;

    public AccountDataGenerator(int namesCount) {
        this.namesCount = namesCount;
        HashSet<Name> names = new HashSet<>(namesCount);
        NameGenerator nameGen = new NameGenerator();
        while (names.size() != namesCount) {
            names.add(nameGen.generateName(Gender.MALE));
        }
        this.names = names.stream().sorted(Comparator.comparing(Name::getFirstName)).toArray(Name[]::new);
        faker = new Faker(new Locale("en-US"));
    }

    private int getRandomInt(int min, int max) {
        max = max - min + 1;
        return (int) (Math.random() * max) + min;
    }

    private String getRandomString(int len, int charMin, int charMax) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; ++i) {
            sb.append((char)getRandomInt(charMin, charMax));
        }
        return sb.toString();
    }

    public String genEmail(String firstName, String lastName) {
        StringBuilder emailBuilder = new StringBuilder();
        return emailBuilder.
                append(firstName.toLowerCase()).append(".").
                append(lastName.toLowerCase()).append("@").
                append(getRandomString(5, CHAR_SYMBOL_MIN, CHAR_SYMBOL_MAX)).
                append(".gmail.com").toString();
    }

    public Account genAccount() {
        if (namesCount == 0) {
            throw new RuntimeException("No more possible unique accounts");
        }
        int pairId = getRandomInt(0, namesCount - 1);
        String firstName = names[pairId].getFirstName();
        String lastName = names[pairId].getLastName();
        names[pairId] = names[namesCount - 1];
        --namesCount;

        Address address = faker.address();

        return new Account(
                firstName,
                lastName,
                genEmail(firstName, lastName),
                getRandomString(5, CHAR_SYMBOL_MIN, CHAR_SYMBOL_MAX),
                address.streetAddress(),
                address.city(),
                getRandomInt(1, MAX_STATE_ID),
                getRandomString(5, CHAR_NUM_MIN, CHAR_NUM_MAX),
                US_COUNTRY_ID,
                faker.phoneNumber().cellPhone(),
                "My home address"
        );
    }

    public List<Account> genAccounts(int count) {
        if (count <= 0 || count > namesCount) {
            throw new RuntimeException(String.format("There is %d possible unique accounts number left. Can't generate %d accounts", namesCount, count));
        }
        List<Account> accounts = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            accounts.add(genAccount());
        }
        return accounts;
    }
}
