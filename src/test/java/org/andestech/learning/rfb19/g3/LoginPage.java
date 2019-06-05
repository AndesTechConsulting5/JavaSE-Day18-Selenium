package org.andestech.learning.rfb19.g3;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.LocalDateTime;

public class LoginPage {

    private static final String LOGIN_PAGE = "http://andestech.org/learning/rfb18/login.html";

    private WebDriver wd;
    private String loginText;
    private String passText;



    public LoginPage(WebDriver wd)
    {
        wd.get(LOGIN_PAGE);
        this.wd = wd;

        PageFactory.initElements(wd, this);
    }

   @FindBy(id = "login")
   private WebElement loginTextBox;

   @FindBy(id = "pass")
   private WebElement passwordTextBox;

   @FindBy(name = "submit")
    private WebElement submitButton;

   @FindBy(name = "reset")
   private WebElement resetButton;

   // Нужно переместить на ProfilePageObject
   @FindBy(tagName = "header")
   private WebElement header;

   @FindBy(linkText = "Logout")
   private WebElement logOutLink;

   @Override
   protected void finalize()
   {

   }

   private void resetLoginForm() throws InterruptedException{

      // Thread.sleep(4000);
       if(!wd.getCurrentUrl().equals(LOGIN_PAGE)){
       wd.navigate().to(LOGIN_PAGE);}

       Wait wait = new WebDriverWait(wd, 3);
       wait.until(ExpectedConditions.elementToBeClickable(resetButton));

       resetButton.click();

   }

   private void setLogin() {
        loginTextBox.sendKeys(loginText);
    }

    private void setPassword() {
        passwordTextBox.sendKeys(passText);
    }


    public boolean login(String loginText, String passText) throws InterruptedException{
        this.loginText = loginText;
        this.passText = passText;

        logout();
        resetLoginForm();
        setLogin();
        setPassword();
        submitButton.submit();

        try{

           wd.switchTo().alert().accept();

        }
        catch (NoAlertPresentException ex){
            // проверить страницу профиля и кукисы
            //....
            if (!isLoggedIn()) {return false;}
            return true;
        }

        return false;
    }

    private void logout(){
       if (isLoggedIn()) logOutLink.click();

    }


    private boolean isLoggedIn(){

       try {
           String headerText = header.getText();
           String cookieData = wd.manage().getCookieNamed("loginOk").getValue();
           if(cookieData.indexOf(loginText) != -1 && headerText.indexOf(loginText) != -1)
           {
               return true;
           }
           else{return  false;}
       }
       catch(NoSuchElementException | NoSuchCookieException | NullPointerException ex){
           return false;
       }

    }

}
