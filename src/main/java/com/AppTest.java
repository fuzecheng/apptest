package com;

import com.sun.deploy.security.ValidationState;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;


import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.Connection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.mobile.NetworkConnection;
import org.openqa.selenium.net.NetworkUtils;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.AndroidDevice;
import utils.BaseTestCase;
import watcher.PermissionWatcher;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import static io.appium.java_client.android.AndroidKeyCode.HOME;

/**
 * Created by Administrator on 2017/9/3.
        */


public class AppTest extends BaseTestCase{

        WebElement element;
        TouchAction action;
        List setupeliment;
        public Logger logger = LoggerFactory.getLogger(AppTest.class);
        AndroidDriver driver;


    @BeforeTest
    public  void setUp() throws MalformedURLException, InterruptedException {
        //设置apk的路径
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apps");
        File app = new File(appDir, "2018launcher_v4.apk");

        //设置自动化相关参数
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");
        //PZAIQC5995LJ6HF6 33004c65ac88c2f9 0123456789ABCDEF
        capabilities.setCapability("deviceName", "192.168.185.101:5555");
        capabilities.setCapability("noReset","true");
        //设置安卓系统版本 5.1.1
        capabilities.setCapability("platformVersion", "7.0");
        //设置apk路径
        capabilities.setCapability("app", app.getAbsolutePath());



        //设置app的主包名和主类名
        capabilities.setCapability("appPackage", "com.tct.launcher");
        capabilities.setCapability("appActivity", ".Launcher");



//        capabilities.setCapability("unicodeKeyboard", "True");
//        capabilities.setCapability("resetKeyboard", "True");
        //初始化 需要setDriver,否则会空指针异常
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        setdriver(driver);
        action=new TouchAction(driver);
//        driver.registerWatcher("uiWatcher",new PermissionWatcher(driver));
//        driver.runWatchers();

    }


    @Test
    public void testcase1()  {
      //  androidDriver.pressKeyCode(AndroidKeyCode.HOME);
//        try {
//        WebElement element=driver.findElement(By.name("Add Contact"));
//        element.click();
//        List<WebElement>  text=driver.findElementsByClassName("android.widget.EditText");
//
//        text.get(0).sendKeys("123");
//        text.get(1).sendKeys("456");
//        driver.findElement(By.name("Save")).click();
//        driver.getKeyboard().sendKeys("");

//          element.findElement(By.name("总是"));
//          element.click();

        setupeliment=new LinkedList<>();
        setupeliment.add(By.name("Allow"));
        setupeliment.add(By.name("Allow"));
        setupeliment.add(By.name("GOT IT"));
        setupeliment.add(By.name("Joy Launcher"));
        setupeliment.add(By.name("Always"));
        Iterator iterator=setupeliment.iterator();
        int flag=0;
        while (iterator.hasNext()){
            By by=(By) iterator.next();
            if (flag==3){
                driver.pressKeyCode(AndroidKeyCode.HOME);
                    exsitClick(by);
                    flag++;
                 }else {
                    exsitClick(by);
                flag++;
                     }
             }

    }
    @Test
    public void testcase2(){
        try {
        //操作wifi
        //androidDriver.setConnection(Connection.WIFI);
//          driver.wait(1000);
        //  androidDriver.setConnection(Connection.WIFI);
        element=driver.findElement(By.name("Apps"));
        element.click();
        driver.pressKeyCode(AndroidKeyCode.HOME);
        Thread.sleep(2000);
//        element=androidDriver.findElement(By.id("com.tct.launcher:id/all_app_blur_view"));
//        action.longPress(698,937,Duration.ofSeconds(3000));
//        action.longPress(element,Duration.ofSeconds(3000));
        longClick("com.tct.launcher:id/all_app_blur_view");
        element=driver.findElement(By.id("com.tct.launcher:id/settings_button"));
        element.click();
        List<WebElement> elements=driver.findElements(By.id("android:id/switchWidget"));
        elements.get(1).click();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    
    @AfterTest
    public void tearDown(){
//        driver.removeApp("com.tct.launcher");
        driver.quit();

    }
    public  void longClick(String id){
        WebElement longClick = driver.findElement(By.id(id));
        action.longPress(longClick).waitAction(Duration.ofSeconds(3)).perform();


    }


    public boolean isElementExist(By Locator) {
        try {
            driver.findElement(Locator);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return false;
        }
    }
    public void exsitClick(By element){
        if (isElementExist(element)){
           driver.findElement(element).click();
        }else {

        }

    }
}
