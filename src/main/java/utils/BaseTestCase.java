package utils;

import io.appium.java_client.TouchAction;
import io.appium.java_client.events.EventFiringWebDriverFactory;
import io.appium.java_client.events.api.general.ElementEventListener;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BaseTestCase {
    public static AndroidDevice driver;
    public static Logger logger= LoggerFactory.getLogger(BaseTestCase.class);
    public static TouchAction action;



//    public void setupLisetenr(){
//        driver= EventFiringWebDriverFactory.getEventFiringWebDriver(driver, new ElementEventListener() {
//
//
//            @Override
//            public void beforeClickOn(WebElement element, WebDriver driver) {
//                if (isElementExist(By.name("Allow"))){
//                    driver.findElement(By.name("Allow")).click();
//                }else if (isElementExist("new UiSelector().textContains(\"Add note\")")){
//                    driver.findElement(By.name("OK")).click();
//                }
//            }
//
//            @Override
//            public void afterClickOn(WebElement element, WebDriver driver) {
//
//            }
//
//            @Override
//            public void beforeChangeValueOf(WebElement element, WebDriver driver) {
//
//            }
//
//            @Override
//            public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
//
//            }
//
//            @Override
//            public void afterChangeValueOf(WebElement element, WebDriver driver) {
//
//            }
//
//            @Override
//            public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
//
//            }
//        });
//    }


    public void setdriver(AndroidDevice driver){
        this.driver=driver;
    }

    public void takescreen(String filename){
        ScreenScr.getScreen(driver, filename);

    }
    public static void sleep(int seconds) throws InterruptedException {
        Thread.sleep(seconds);
    }

    //Stops the script for the given amount of seconds.


    public static void log(String message) {
        logger.info(message);
    }


    public  void longClick(String id){
        WebElement longClick = driver.findElement(By.id(id));
        new TouchAction(driver).longPress(longClick).waitAction(Duration.ofSeconds(3)).perform();


    }
//    public void swipeTest(int startX,int startY,int deltaX,int deltaY){
//        int offsetx=deltaX-startX;
//        int offsety=deltaY-startY;
//        driver.swipe();
//    }


    public static boolean isElementExist(By Locator) {
        try {
            driver.findElement(Locator);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return false;
        }
    }
    public static boolean isElementExist(String uiautomator){
        try {
            driver.findElementByAndroidUIAutomator(uiautomator);
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
    public boolean imgCompare(String define_name,String screen_shot_name) throws IOException {

        File f1 = new File("queryimages/"+define_name+".png");
        BufferedImage img1 = ScreenUtil.getImageFromFile(f1);
        File f2 = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(f2, new File("target/reports/screenshots/"+screen_shot_name+".png"));
        BufferedImage img2 = ScreenUtil.getImageFromFile(f2);
        Boolean same =ScreenUtil.sameAs(img1, img2, 0.9);
        return same ;
    }


}
