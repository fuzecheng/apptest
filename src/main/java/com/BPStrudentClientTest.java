package com;

import io.appium.java_client.TouchAction;
import io.appium.java_client.events.EventFiringWebDriverFactory;
import listener.AppiumListener;
import model.AppiumSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.AndroidDevice;
import utils.BaseTestCase;
import utils.LogCatHelper;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.assertNotNull;

public class BPStrudentClientTest extends BaseTestCase {



    WebElement element;
    List setupeliment;
    List<WebElement> elements;
    HashMap<String, By> map;
    public Logger logger = LoggerFactory.getLogger(AppTest.class);
    LogCatHelper helper;
    WebDriverWait webDriverWait;


    //电话
    @BeforeTest
    public void setUp() throws MalformedURLException, InterruptedException {
        AppiumSettings appiumSettings=new AppiumSettings();
        appiumSettings.setApk_file_name("cc.bodyplus.v3.6.0.apk");
        appiumSettings.setPlantform("Android");
        //0715f7bdaaec1938 192.168.185.101:5555
        appiumSettings.setDevice_name("0715f7bdaaec1938");
        appiumSettings.setPlatform_version("7.0");
        appiumSettings.setAppPackage("cc.bodyplus");
        appiumSettings.setAppActivity(".mvp.view.login.activity.SplashActivity");

        driver = new AndroidDevice(new URL("http://127.0.0.1:4723/wd/hub"), settings(appiumSettings));
        action = new TouchAction(driver);
        driver.context("NATIVE_APP");
        setdriver(driver);
        elements = new LinkedList();
        map = new LinkedHashMap<String, By>();
        driver = EventFiringWebDriverFactory.getEventFiringWebDriver(driver, new AppiumListener(driver));
        //初始化日志记录
        helper=new LogCatHelper("cc.bodyplus");
        helper.start();

    }
    @Test
    public void  testLogin() throws InterruptedException {
        webDriverWait=new WebDriverWait(driver,10);
        assertNotNull(webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("cc.bodyplus:id/tv_login"))));
        assertNotNull( webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("cc.bodyplus:id/tv_register"))));
        driver.findElement(By.id("cc.bodyplus:id/tv_login")).click();
        driver.findElement(By.id("cc.bodyplus:id/edit_login_phone")).sendKeys("13728963515");
        driver.findElement(By.id("cc.bodyplus:id/edit_login_password")).sendKeys("fzc8802653");
        driver.findElement(By.id("cc.bodyplus:id/tv_login")).click();
        sleep(3000);
    }
    @Test
    public void testAddCourse(){
        elements=driver.findElements(By.id("cc.bodyplus:id/tab_menu_view"));
        elements.get(2).click();
        driver.findByUiautomator_text("训练计划").click();
        driver.findByUiautomator_text("添加课程").click();
        driver.findElement(By.id("cc.bodyplus:id/image_bg")).click();
        driver.findByUiautomator_text("跑步核心训练（三）").click();
        driver.findByUiautomator_text("加入计划").click();



    }
    @Test
    public  void testAutoSports(){
        elements=driver.findElements(By.id("cc.bodyplus:id/tab_menu_view"));
        elements.get(2).click();
        driver.findByUiautomator_text("自由训练").click();
        driver.findByUiautomator_text("普通模式").click();
    }

    @AfterTest
    public void tearDown() throws IOException, MessagingException {
        helper.stop();
//        driver.removeApp("cc.bodyplus");
        driver.quit();
//        sendMail();
    }

}
