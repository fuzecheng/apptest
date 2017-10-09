package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BaseTestCase {
    public AndroidDevice driver;
    public static Logger logger= LoggerFactory.getLogger(BaseTestCase.class);
    public static TouchAction action;



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
    public static void sleep(double seconds) throws Exception {
        log("Waiting for " + seconds + " sec");
        seconds = seconds * 1000;
        Thread.sleep((int) seconds);
    }

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
