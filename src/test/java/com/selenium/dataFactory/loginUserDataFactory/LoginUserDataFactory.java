package com.selenium.dataFactory.loginUserDataFactory;

import com.selenium.dataObject.loginUser.LoginUserDataObject;

public class LoginUserDataFactory {

  public static LoginUserDataObject inValidLoginUserData() {
    var loginUserData = new LoginUserDataObject();
    loginUserData.setEmail("invalidEmail");
    loginUserData.setPassword("invalidPassword");
    return loginUserData;
  }
}
