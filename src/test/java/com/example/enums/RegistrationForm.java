package com.example.enums;

import com.example.dataFaker.DataFaker;


public enum RegistrationForm {
    NAME(DataFaker.createName()),
    EMAIL(DataFaker.createEmail()),
    PASSWORD(DataFaker.createPassword()),
    TITLE(DataFaker.generateTitle()),
    BIRTHDAY(DataFaker.createBirthday("day")),
    BIRTHMONTH(DataFaker.createBirthday("month")),
    BIRTHYEAR(DataFaker.createBirthday("year")),
    FIRSTNAME(DataFaker.createFirstName()),
    LASTNAME(DataFaker.createLastName()),
    COMPANY(DataFaker.createCompany()),
    ADDRESS1(DataFaker.createAddress()),
    ADDRESS2(DataFaker.createAddress2()),
    COUNTRY(DataFaker.generateCountry()),
    ZIPCODE(DataFaker.createZipCode()),
    STATE(DataFaker.createState()),
    CITY(DataFaker.createCity()),
    MOBILENUMBER(DataFaker.createMobilePhone());

    private final String value;

    RegistrationForm(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
