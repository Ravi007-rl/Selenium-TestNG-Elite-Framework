package dataFactory.loginUserDataFactory;

import dataObject.loginUser.LoginUserDataObject;

public class LoginUserDataFactory {

  public static LoginUserDataObject invalidLoginUserData() {
    var loginUserData = new LoginUserDataObject();
    loginUserData.setEmail("invalidEmail");
    loginUserData.setPassword("invalidPassword");
    return loginUserData;
  }
}
