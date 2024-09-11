package com.selenium.dataObject.registerUser;

import lombok.Data;

@Data
public class RegisterUserDataObject {
  private String firstName;
  private String lastName;
  private String email;
  private String telephone;
  private String password;
  private String confirmPassword;
  private boolean subscribe;
  private boolean privacyPolicy;
}
