package org.andestech.learning.rfb19.g3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDateTime;

public class LoginPage {

    public LoginPage(WebDriver wd)
    {


        PageFactory.initElements(wd, this);

    }

   @FindBy(id = "login")
   private WebElement loginTextBox;

   @FindBy(id = "pass")
   private WebElement passwordTextBox;


    public void setLogin(String loginText) {
        this.loginTextBox.sendKeys(loginText);
    }

    public void setPassword(String passText) {
        this.passwordTextBox.sendKeys(passText);
    }

    public boolean login(){
        return true;

    }

    public void logout(){}


    private boolean isLoggedIn(){
        return true;
    }

}
