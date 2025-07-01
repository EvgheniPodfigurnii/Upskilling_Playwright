package com.example.dataFaker;

import net.datafaker.Faker;
import static net.datafaker.providers.base.Text.*;

public class DataFaker {
    static Faker faker = new Faker();


    public static String generateTitle() {
        String prefix;
        do {
            prefix = faker.name().prefix();
        } while (!prefix.equals("Mr.") && !prefix.equals("Mrs."));
        return prefix;
    }


    public static String createName() {
        return faker.name().fullName();
    }

    public static String createEmail() {
        return faker.internet().emailAddress();
    }

    public static String createPassword() {
        return faker.text().text(TextSymbolsBuilder.builder()
                .len(8)
                .with(EN_UPPERCASE, 1)
                .with(DIGITS, 3)
                .with(DEFAULT_SPECIAL, 1)
                .build());
    }

    public static String createBirthday(String birthday) {
        String birthDayValue = "";
        switch (birthday.toLowerCase()) {
            case "day":
                birthDayValue = String.valueOf(faker.timeAndDate().birthday().getDayOfMonth());
                break;
            case "month":
                birthDayValue = faker.timeAndDate().birthday("MMMM");
                break;
            case "year":
                birthDayValue = String.valueOf(faker.timeAndDate().birthday().getYear());
                break;
            default:
        }

        return birthDayValue;
    }

    public static String createFirstName() {
        return faker.name().firstName();
    }

    public static String createLastName() {
        return faker.name().lastName();
    }

    public static String createCompany() {
        return faker.company().name();
    }

    public static String createAddress() {
        return faker.address().fullAddress();
    }

    public static String createAddress2() {
        return faker.address().fullAddress();
    }

    public static String createState() {
        return faker.address().state();
    }

    public static String createCity() {
        return faker.address().city();
    }

    public static String createZipCode() {
        return faker.address().zipCode();
    }

    public static String createMobilePhone() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String generateCountry() {
        String country;
        do {
            country = faker.address().country();
        } while (!country.equals("India") &&
                !country.equals("United States") &&
                !country.equals("Canada") &&
                !country.equals("Australia") &&
                !country.equals("Israel") &&
                !country.equals("New Zealand") &&
                !country.equals("Singapore"));

        return country;
    }
}
