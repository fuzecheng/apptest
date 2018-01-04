package utils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import listener.AppiumListener;
import model.AppiumSettings;
import model.DriverInstance;
import model.MailsProperties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;

import javax.mail.MessagingException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.assertFalse;

public class BaseTestCase {
    List<WebElement> elements;
    public static AndroidDriver driver;
    public static Logger logger= LoggerFactory.getLogger(BaseTestCase.class);
    public static TouchAction action;
    @BeforeSuite
    public void setUp() throws MalformedURLException {
        driver= DriverInstance.getInstance().getAndroidDriver();
        action = new TouchAction(driver);
        setdriver(driver);
        elements = new LinkedList();
    }

    public void setdriver(AndroidDriver driver){
        BaseTestCase.driver =driver;
    }


    public void takescreen(String filename){
        ScreenScr.getScreen(driver, filename);

    }
    public static void sleep(int seconds) throws InterruptedException {
        Thread.sleep(seconds);
    }




    public static boolean isElementExist(AndroidDriver driver, By Locator) {
        try {
            driver.findElement(Locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public static boolean isElementExist(By Locator) {
        try {
            driver.findElement(Locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
    public static boolean isElementExist(String uiautomator){
        try {
            driver.findElementByAndroidUIAutomator(uiautomator);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }
    public static boolean isElementExist(AndroidDriver driver, String uiautomator){
        try {
            driver.findElementByAndroidUIAutomator(uiautomator);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }



    public boolean imgCompare(String define_name,String screen_shot_name) throws IOException {

        File f1 = new File("queryimages/"+define_name+".png");
        BufferedImage img1 = ScreenUtil.getImageFromFile(f1);
        File f2 = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(f2, new File("target/reports/screenshots/"+screen_shot_name+".png"));
        BufferedImage img2 = ScreenUtil.getImageFromFile(f2);
        Boolean same = ScreenUtil.sameAs(img1, img2, 0.98);
        return same ;
    }
    public void tearDown() throws IOException, MessagingException {

    }
    public void sendMail() throws UnsupportedEncodingException, MessagingException {
        MailUtils mailUtils=new MailUtils();
        MailsProperties properties=new MailsProperties();
        String[] cc={"zecheng.fu@tcl.com","405960648@qq.com"};
        properties.setCc(cc);
        properties.setMail_host("smtp.qq.com");
        properties.setKey("");
        properties.setMail_user("2776119050@qq.com");
        properties.setPort(25);
        properties.setReceiver(cc);
        mailUtils.sendMail(properties);
    }

        public  boolean toastIsExist(int time,String toast){
                try {
                    WebDriverWait driverWait = new WebDriverWait(driver, time);
                    driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@text,'" + toast + "')]")));
                    return true;
                }catch (Exception e){
                    return false;
                }
        }

        public void erroCall(){
            if( !AppiumListener.erro_list.isEmpty()){
                String erro= AppiumListener.erro_list.toString();
                AppiumListener.erro_list.clear();
                assertFalse(true,erro);

            }
        }


}
