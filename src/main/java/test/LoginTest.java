//package test;
//
//import com.AppTest;
//import com.esotericsoftware.yamlbeans.YamlException;
//import io.appium.java_client.TouchAction;
//import io.appium.java_client.events.EventFiringWebDriverFactory;
//import listener.AppiumListener;
//import model.AppiumSettings;
//import model.FilePath;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.testng.Assert;
//import org.testng.annotations.AfterTest;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Test;
//import pageutil.LoginPage;
//import utils.AndroidDriver;
//import utils.BaseTestCase;
//import utils.LogCatHelper;
//
//import java.io.FileNotFoundException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//public class LoginTest extends BaseTestCase{
//
//    HashMap<String, By> map;
//    public Logger logger = LoggerFactory.getLogger(AppTest.class);
//    LogCatHelper helper;
//    @BeforeTest
//    public void setUp() throws MalformedURLException, InterruptedException {
//        AppiumSettings appiumSettings=new AppiumSettings();
//        appiumSettings.setApkPath(FilePath.APP_PATH);
//        appiumSettings.setApk_file_name(FilePath.APP_NAME);
//        appiumSettings.setPlantform("Android");
//        //0715f7bdaaec1938 192.168.93.101:5555
//        appiumSettings.setDevice_name("0715f7bdaaec1938");
//        appiumSettings.setPlatform_version("7.0");
//        appiumSettings.setAppPackage("cc.bodyplus");
//        appiumSettings.setAppActivity(".mvp.view.login.activity.SplashActivity");
//
//        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), settings(appiumSettings));
//        action = new TouchAction(driver);
//        driver.context("NATIVE_APP");
//        setdriver(driver);
//        map = new LinkedHashMap<String, By>();
//        driver = EventFiringWebDriverFactory.getEventFiringWebDriver(driver, new AppiumListener(driver));
//        //初始化日志记录
//        helper=new LogCatHelper("cc.bodyplus");
//        helper.start();
//
//    }
//    @Test
//    public  void testLogin() throws YamlException, InterruptedException, FileNotFoundException {
//        LoginPage loginPage = new LoginPage(driver, "/Login.yaml");
//        loginPage.operate();
//
//        Assert.assertTrue(loginPage.checkpoint(), "检查点不通过");
//
//    }
//    @Test void testLogout() {
//
//
//    }
//
//    @AfterTest
//    public void teardown(){
//        helper.stop();
//        driver.quit();
//
//
//    }
//
//}
