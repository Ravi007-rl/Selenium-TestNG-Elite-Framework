package com.selenium.dataFactory.registerUserDataFactory;

import com.github.javafaker.Faker;
import com.selenium.dataObject.registerUser.RegisterUserDataObject;
import com.selenium.testng.elite.utils.RandomHelper;

public class RegisterUserDataFactory {

  public static RegisterUserDataObject getInvalidRegisterUserData() {

    // Register User DataObject
    var registerUser = new RegisterUserDataObject();
    var invalidData = RandomHelper.getRandomString(33);
    registerUser.setFirstName(invalidData);
    registerUser.setLastName(invalidData);
    registerUser.setEmail(new Faker().internet().emailAddress());
    registerUser.setTelephone(invalidData);
    registerUser.setPassword(invalidData);
    registerUser.setConfirmPassword(invalidData);
    return registerUser;
  }

  public static RegisterUserDataObject getRegisterUserData() {

    // Register User DataObject
    var registerUser = new RegisterUserDataObject();

    // generate random data
    var faker = new Faker();
    var password = faker.internet().password();

    registerUser.setFirstName(faker.name().firstName());
    registerUser.setLastName(faker.name().lastName());
    registerUser.setEmail(faker.internet().emailAddress());
    registerUser.setTelephone(faker.phoneNumber().phoneNumber());
    registerUser.setPassword(password);
    registerUser.setConfirmPassword(password);
    return registerUser;
  }
}
