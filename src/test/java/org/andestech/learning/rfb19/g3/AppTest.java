package org.andestech.learning.rfb19.g3;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class AppTest
{
    private WebDriver wd = null;
    private ChromeOptions chromeOptions;
    private String loginName, loginPass;
    private Wait<WebDriver> wait1;

    @BeforeClass
    public void initData(){
    System.setProperty("webdriver.chrome.driver",
            "E:\\selenium_drivers\\chromedriver_2.46.628402.exe");

//        System.setProperty("webdriver.chrome.driver",
//                "E:\\selenium_drivers\\chromedriver_74.0.3729.6.exe");
    // chromedriver_2.46.628402.exe
    System.out.println("+++ Class: " + this);

    chromeOptions = new ChromeOptions();

    //chromeOptions.addArguments("--user-data-dir=C:\\Users\\and\\AppData\\Local\\Google\\Chrome\\User Data");

     chromeOptions.setBinary("E:\\progs\\chrome-win\\chrome.exe");
     chromeOptions.addArguments("--user-data-dir=C:\\Users\\and\\AppData\\Local\\Chromium\\User Data");

     chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
     //chromeOptions.setHeadless(true);


    }

    @Test
    public void testCaseChrome01() throws InterruptedException
    {
        wd = new ChromeDriver(chromeOptions);

        Wait<WebDriver> wait1 = new WebDriverWait(wd,5);

        Wait<WebDriver> wait2 = new FluentWait<>(wd).withTimeout(Duration.ofSeconds(5)).
                pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);

        wd.get("http://andestech.org/learning/rfb18");

        WebElement webElement = wait1.until( (WebDriver x) -> x.findElement(By.linkText("Home")));

        String bc = webElement.getCssValue("background-color");
        String c = webElement.getCssValue("color");
        String fontSize = webElement.getCssValue("font-size");
        String fontFam = webElement.getCssValue("font-family");



        System.out.println("color: "+ c);
        Assert.assertEquals(c, "rgba(0, 0, 238, 1)");


        System.out.println("backgroud-color: "+ bc);

        System.out.println("font-size: " + fontSize);
        System.out.println("font-family: " + fontFam);

        // button

        wd.navigate().to("http://andestech.org/learning/rfb18/newcustomer.html");

        WebElement button =  wd.findElement(By.cssSelector("#table_new_user input[type='reset']"));


        String bWidth = button.getCssValue("width");
        String bHeight = button.getCssValue("height");

        System.out.println("Button width: " + bWidth);
        System.out.println("Button height: " + bHeight);

    }


    private void saveScreenShot(){

        File screen =  ((TakesScreenshot)wd).getScreenshotAs(OutputType.FILE);

        String fileName = "E:\\screens\\screen_"+ System.currentTimeMillis() + ".png";

        try {
            Files.copy(new FileInputStream(screen), Paths.get(fileName),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ex){ex.printStackTrace();

        }

    }


    @Test
    public void loginScreenTest() throws InterruptedException
    {
        wd = new ChromeDriver(chromeOptions);

        Wait<WebDriver> wait1 = new WebDriverWait(wd,5);

        wd.get("http://andestech.org/learning/rfb18");
        Thread.sleep(1000);

        //wd.navigate().to("http://andestech.org/learning/rfb18/login.html");
        wait1.until(x -> x.findElement(By.linkText("Login"))).click();


        saveScreenShot();


        Thread.sleep(1000);

    }


    private void loginInit()
    {

        wd = new ChromeDriver(chromeOptions);
        wait1 = new WebDriverWait(wd,5);

        wd.get("http://andestech.org/learning/rfb18/");
        wait1.until( x -> x.findElement(By.linkText("Login"))).click();

        wd.findElement(By.name("reset")).click();

        WebElement login = wd.findElement(By.id("login"));
        login.sendKeys(loginName);
        wd.findElement(By.id("pass")).sendKeys(loginPass);


        login.submit();

    }

    private boolean isAlertPresent(){

        WebDriverWait wait = new WebDriverWait(wd, 2000);
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }


    @Test(groups = "positive")
    public void positiveLoginTest() throws InterruptedException
    {

        loginName = "ppetrov";
        loginPass = "P@ssw0rd";

        loginInit();

        if(isAlertPresent()) {
        Alert alert = wd.switchTo().alert();
        String info = alert.getText();
        alert.accept();
        Assert.fail(info);

    }
        else{saveScreenShot();}

        // проверка на успешный вход

        String headerText = wd.findElement(By.tagName("header")).getText();
        System.out.println(headerText);

        Cookie cookie = wd.manage().getCookieNamed("loginOk");
        String cookieData = cookie.getValue();

        Assert.assertTrue(cookieData.indexOf(loginName) != -1 && headerText.indexOf(loginName) != -1, "Smth wrong");

        Thread.sleep(2000);

        wait1.until( x -> x.findElement(By.linkText("Logout"))).click();

    }






    @AfterClass
    public void tearDown()
    {
      if(wd != null) wd.quit();
      System.out.println("--- Class: " + this);
    }

}
