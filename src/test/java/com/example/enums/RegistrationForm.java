package com.example.enums;

import com.example.dataFaker.DataFaker;


public enum RegistrationForm {
    NAME,
    EMAIL,
    PASSWORD,
    TITLE,
    BIRTHDAY,
    BIRTHMONTH,
    BIRTHYEAR,
    FIRSTNAME,
    LASTNAME,
    COMPANY,
    ADDRESS1,
    ADDRESS2,
    COUNTRY,
    ZIPCODE,
    STATE,
    CITY,
    MOBILENUMBER;

    public String generate() {
        return switch (this) {
            case NAME -> DataFaker.createName();
            case EMAIL -> DataFaker.createEmail();
            case PASSWORD -> DataFaker.createPassword();
            case TITLE -> DataFaker.generateTitle();
            case BIRTHDAY -> DataFaker.createBirthday("day");
            case BIRTHMONTH -> DataFaker.createBirthday("month");
            case BIRTHYEAR -> DataFaker.createBirthday("year");
            case FIRSTNAME -> DataFaker.createFirstName();
            case LASTNAME -> DataFaker.createLastName();
            case COMPANY -> DataFaker.createCompany();
            case ADDRESS1 -> DataFaker.createAddress();
            case ADDRESS2 -> DataFaker.createAddress2();
            case COUNTRY -> DataFaker.generateCountry();
            case ZIPCODE -> DataFaker.createZipCode();
            case STATE -> DataFaker.createState();
            case CITY -> DataFaker.createCity();
            case MOBILENUMBER -> DataFaker.createMobilePhone();
        };
    }
}
