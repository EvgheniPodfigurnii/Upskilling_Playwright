package com.example.dataFaker;

import net.datafaker.Faker;
import static net.datafaker.providers.base.Text.*;

public class DataFaker {
    Faker faker = new Faker();

    public String createName() {
        return faker.name().fullName();
    }

    public String createEmail() {
        return faker.internet().emailAddress();
    }

    public String createPassword() {
        return faker.text().text(TextSymbolsBuilder.builder()
                .len(8)
                .with(EN_UPPERCASE, 1)
                .with(DIGITS, 3)
                .with(DEFAULT_SPECIAL, 1)
                .build());
    }

    public String createBirthday(String birthday) {
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

    public String createFirstName() {
        return faker.name().firstName();
    }

    public String createLastName() {
        return faker.name().lastName();
    }

    public String createCompany() {
        return faker.company().name();
    }

    public String createAddress() {
        return faker.address().fullAddress();
    }

    public String createAddress2() {
        return faker.address().fullAddress();
    }

    public String createState() {
        return faker.address().state();
    }

    public String createCity() {
        return faker.address().city();
    }

    public String createZipCode() {
        return faker.address().zipCode();
    }

    public String createMobilePhone() {
        return faker.phoneNumber().phoneNumber();
    }
}
