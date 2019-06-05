package org.andestech.learning.rfb19.g3;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.openqa.selenium.html5.*;


public class PageObjectTest {

    private WebDriver wd = null;
    private ChromeOptions chromeOptions;
    private String loginName, loginPass;
    private Wait<WebDriver> wait1;
    private LoginPage loginPage;

    @BeforeClass
    public void initData(){
        System.setProperty("webdriver.chrome.driver",
                "E:\\selenium_drivers\\chromedriver_2.46.628402.exe");

        System.out.println("+++ Class: " + this);

        chromeOptions = new ChromeOptions();

        chromeOptions.setBinary("E:\\progs\\chrome-win\\chrome.exe");
        chromeOptions.addArguments("--user-data-dir=C:\\Users\\and\\AppData\\Local\\Chromium\\User Data");

        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        wd = new ChromeDriver(chromeOptions);
        loginPage = new LoginPage(wd);

    }



    @Test
    public void loginTest() throws InterruptedException{

        Assert.assertTrue(loginPage.login("ppetrov", "P@ssw0rd"));
        Assert.assertTrue(loginPage.login("bpetrov", "P@ssw0rd"));
     }

    @Test
    public void login2Test() throws InterruptedException
    {
        Assert.assertTrue(loginPage.login("bpetrov2", "P@ssw0rd"));
    }

    @AfterClass
    public void tearDown()
    {
        if(wd != null) wd.quit();
        System.out.println("--- Class: " + this);
    }

}
