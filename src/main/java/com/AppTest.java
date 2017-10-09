package com;

import com.sun.deploy.security.ValidationState;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
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
import org.testng.Assert;
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
import java.util.*;


import static io.appium.java_client.android.AndroidKeyCode.HOME;

/**
 * Created by Administrator on 2017/9/3.
        */


public class AppTest extends BaseTestCase{

        WebElement element;
        List setupeliment;
        List elements;
        HashMap<String,By> map;
        public Logger logger = LoggerFactory.getLogger(AppTest.class);



    @BeforeTest
    public  void setUp() throws MalformedURLException, InterruptedException {
        //设置apk的路径
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apps");
        File app = new File(appDir, "2018launcher_v5.apk");

        //设置自动化相关参数
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, "");
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
        driver = new AndroidDevice(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        action=new TouchAction(driver);
        driver.context("NATIVE_APP");
        setdriver(driver);
        elements=new LinkedList();
        map=new LinkedHashMap<String,By>();

//        driver.registerWatcher("uiWatcher",new PermissionWatcher(driver));
//        driver.runWatchers();


    }


    @Test
    public void testInstallsetUp()  {
      //  androidDriver.pressKeyCode(AndroidKeyCode.HOME);
//
//        text.get(0).sendKeys("123");
//        text.get(1).sendKeys("456");
//        driver.findElement(By.name("Save")).click();
//        driver.getKeyboard().sendKeys("");

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
    public void testLoginApps(){
        driver.pressKeyCode(AndroidKeyCode.HOME);
        element=driver.findElement(By.name("Apps"));
        element.click();
        map.put("com.tct.launcher:id/launcher",By.id("com.tct.launcher:id/launcher"));
        map.put("com.tct.launcher:id/drag_layer",By.id("com.tct.launcher:id/drag_layer"));
        map.put("com.tct.launcher:id/workspace",By.id("com.tct.launcher:id/workspace"));
        map.put("com.tct.launcher:id/apps_view",By.id("com.tct.launcher:id/apps_view"));
        map.put("com.tct.launcher:id/main_content",By.id("com.tct.launcher:id/main_content"));
        map.put("com.tct.launcher:id/search_container",By.id("com.tct.launcher:id/search_container"));
        map.put("com.tct.launcher:id/apps_list_view",By.id("com.tct.launcher:id/apps_list_view"));

        Iterator iterator=map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            By by=(By) entry.getValue();
            if (!isElementExist(by)){
              Assert.assertFalse(true,"NULL:"+entry.getKey());
            }
        }
        driver.pressKeyCode(AndroidKeyCode.HOME);
    }
    @Test
    public  void testDefineIscreen() throws InterruptedException {
        driver.pressKeyCode(AndroidKeyCode.HOME);
        sleep(1000);
        driver.swipeRight();
        allow();
        if (isElementExist(By.id("com.tcl.mie.launcher.lscreen:id/layPopWin"))){
            Assert.assertTrue(true);
        }else {
            Assert.assertFalse(true,"ISCREEN IS null");
        }
        driver.pressKeyCode(AndroidKeyCode.HOME);
    }



    @Test
    public void testIscreenClose(){
        try {
        //操作wifi
        //androidDriver.setConnection(Connection.WIFI);

        Thread.sleep(2000);
        controlSettingsButton();
        sleep(1000);
        driver.swipeRight();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isElementExist(By.id("com.tcl.mie.launcher.lscreen:id/layPopWin"))){
            Assert.assertFalse(true,"ISCREEN IS exist");
        }else {
            Assert.assertTrue(true);
        }


    }



    @Test
    public void testIscreenOpen() throws InterruptedException {
       // driver.swipe(381,818,694,814,300);


//        sleep(1000);
//        driver.swipeDown();
//        sleep(1000);
//        driver.swipeUp();
//        sleep(1000);
//        driver.swipeLeft();
//        sleep(1000);
//        driver.swipeRight();
//        sleep(1000);
        controlSettingsButton();
        sleep(1000);
        driver.swipeRight();
        allow();
        if (isElementExist(By.id("com.tcl.mie.launcher.lscreen:id/layPopWin"))){
            Assert.assertTrue(true);
        }else {
            Assert.assertFalse(true,"ISCREEN IS null,can not find com.tcl.mie.launcher.lscreen:id/layPopWin");
        }

    }

    public void loginWallpapers(){
        longClick("com.tct.launcher:id/all_app_blur_view");
        element.findElement(By.id("com.tct.launcher:id/wallpaper_button")).click();

    }


    public void controlSettingsButton() throws InterruptedException {
        longClick("com.tct.launcher:id/all_app_blur_view");
        element=driver.findElement(By.id("com.tct.launcher:id/settings_button"));
        element.click();
        List<WebElement> elements=driver.findElements(By.id("android:id/switchWidget"));
        elements.get(1).click();
        sleep(500);
        driver.pressKeyCode(AndroidKeyCode.HOME);

    }


    
    @AfterTest
    public void tearDown(){
        driver.removeApp("com.tct.launcher");
        driver.quit();


    }

    public void allow(){
        while(isElementExist(By.name("Allow"))){
            driver.findElement(By.name("Allow")).click();
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
