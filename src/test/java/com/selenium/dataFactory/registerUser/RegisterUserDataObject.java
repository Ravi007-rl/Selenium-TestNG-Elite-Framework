package com.selenium.dataFactory.registerUser;

import lombok.Data;

@Data
public class RegisterUserDataObject {
  String firstName;
  String lastName;
  String email;
  String telephone;
  String password;
  String confirmPassword;
  boolean subscribe;
  boolean privacyPolicy;
}
