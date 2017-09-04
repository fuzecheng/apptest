package com;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;



import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2017/9/3.
        */


public class AppTest {
        private AppiumDriver driver;

    @BeforeTest
    public  void setUp() throws MalformedURLException {
        //设置apk的路径
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apps");
        File app = new File(appDir, "ContactManager.apk");

        //设置自动化相关参数
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "b8f8b836");

        //设置安卓系统版本
        capabilities.setCapability("platformVersion", "6.0");
        //设置apk路径
        capabilities.setCapability("app", app.getAbsolutePath());



        //设置app的主包名和主类名
        capabilities.setCapability("appPackage", "com.example.android.contactmanager");
        capabilities.setCapability("appActivity", ".ContactManager");

        //初始化
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void test(){
        WebElement element=driver.findElement(By.name("Add Contact"));
        element.click();
        List<WebElement>  text=driver.findElementsByClassName("android.widget.EditText");

        text.get(0).sendKeys("123");
        text.get(1).sendKeys("456");
        driver.findElement(By.name("Save")).click();

    }
    @AfterTest
    public void tearDown(){
        driver.quit();
    }

}
