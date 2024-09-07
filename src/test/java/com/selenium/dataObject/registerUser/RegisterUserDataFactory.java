package com.selenium.dataObject.registerUser;

import com.github.javafaker.Faker;
import com.selenium.dataFactory.registerUser.RegisterUserDataObject;
import com.selenium.testng.elite.utils.RandomHelper;

public class RegisterUserDataFactory {

    public static RegisterUserDataObject getInvalidRegisterUserData() {
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
}
