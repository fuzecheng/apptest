package utils;


import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AndroidDriver extends io.appium.java_client.android.AndroidDriver {
    Logger logger = LoggerFactory.getLogger(AndroidDriver.class);

    public AndroidDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
    }

    public AndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
    }

    public AndroidDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory, desiredCapabilities);
    }

    public AndroidDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, desiredCapabilities);
    }

    public AndroidDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(service, httpClientFactory, desiredCapabilities);
    }

    public AndroidDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, desiredCapabilities);
    }

    public AndroidDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(builder, httpClientFactory, desiredCapabilities);
    }

    public AndroidDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, desiredCapabilities);
    }

    public AndroidDriver(Capabilities desiredCapabilities) {
        super(desiredCapabilities);
    }

    public void doSwipe(int startx, int starty, int endx, int endy, int duration) {
        TouchAction touchAction = new TouchAction(this);
        // appium converts press-wait-moveto-release to a swipe action
        touchAction.press(startx, starty).waitAction(Duration.ofMillis(duration)).moveTo(endx, endy).release();
        touchAction.perform();
    }
    public void swipe(int startx, int starty, int endx, int endy, int duration) {
        doSwipe(startx, starty, endx, endy, duration);
    }
    public void swipeUp() {
        swipeUp(0.15f, 0.15f);
    }

    public void swipeUp(float topPad, float bottomPad) {
        Dimension size = manage().window().getSize();
        logger.debug("Window size is " + size);
        swipeUp(new Point(0, 0), size, 250, topPad, bottomPad);
    }

    public void swipeUp(Point rootLocation, Dimension rootSize, int duration, float topPad, float bottomPad) {
        int offset = 1;
        int topOffset = Math.round(rootSize.getHeight() * topPad);
        int bottomOffset = Math.round(rootSize.getHeight() * bottomPad);
        Point center = new Point(rootLocation.x + rootSize.getWidth() / 2, rootLocation.y + rootSize.getHeight() / 2);
        logger.debug("Swiping up at" +
                " x1: " + center.getX() +
                " y1:" + (rootLocation.getY() + rootSize.getHeight() - bottomOffset + offset) +
                " x2:" + center.getX() +
                " y2:" + (rootLocation.getY() + topOffset));
        swipe(center.getX(),
                rootLocation.getY() + rootSize.getHeight() - bottomOffset + offset,
                center.getX(),
                rootLocation.getY() + topOffset,
                duration);
    }

    public void swipeDown() {
        swipeDown(0.15f, 0.15f);
    }

    public void swipeDown(float topPad, float bottomPad) {
        Dimension size = manage().window().getSize();
        logger.debug("Window size is " + size);
        swipeDown(new Point(0, 0), size, 250, topPad, bottomPad);
    }

    public void swipeDown(Point rootLocation, Dimension rootSize, int duration, float topPad, float bottomPad) {
        int offset = 1;
        int topOffset = Math.round(rootSize.getHeight() * topPad);
        int bottomOffset = Math.round(rootSize.getHeight() * bottomPad);
        Point center = new Point(rootLocation.x + rootSize.getWidth() / 2, rootLocation.y + rootSize.getHeight() / 2);
        logger.debug("Swiping down at" +
                " x1: " + center.getX() +
                " y1:" + (rootLocation.getY() + topOffset) +
                " x2:" + center.getX() +
                " y2:" + (rootLocation.getY() + rootSize.getHeight() - bottomOffset + offset));
        swipe(center.getX(),
                (rootLocation.getY() + topOffset),
                center.getX(),
                (rootLocation.getY() + rootSize.getHeight() - bottomOffset + offset),
                duration);
    }

    public  void swipeLeft() {
        swipeLeft(0.15f, 0.15f);
    }

    public void swipeLeft(float leftPad, float rightPad) {
        Dimension size = manage().window().getSize();
        logger.debug("Window size " + size);
        swipeLeft(new Point(0,0), size, 250, leftPad, rightPad);
    }

    public void swipeLeft(Point rootLocation, Dimension rootSize, int duration, float leftPad, float rightPad) {
        int offset = 1;
        int leftOffset = Math.round(rootSize.getWidth() * leftPad);
        int rightOffset = Math.round(rootSize.getWidth() * rightPad);
        Point center = new Point(rootLocation.x + rootSize.getWidth() / 2, rootLocation.y + rootSize.getHeight() / 2);
        logger.debug("Swiping left at" +
                " x1: " + (rootLocation.getX() + rootSize.getWidth() - rightOffset + offset) +
                " y1:" + center.getY() +
                " x2:" + (rootLocation.getX() + leftOffset) +
                " y2:" + center.getY());
        swipe((rootLocation.getX() + rootSize.getWidth() - rightOffset + offset),
                center.getY(),
                (rootLocation.getX() + leftOffset),
                center.getY(),
                duration);
    }

    public void swipeRight() {
        swipeRight(0.15f, 0.15f);
    }

    public void swipeRight(float leftPad, float rightPad) {
        Dimension size = manage().window().getSize();
        swipeRight(new Point(0,0), size, 250, leftPad, rightPad);
    }

    public void swipeRight(Point rootLocation, Dimension rootSize, int duration, float leftPad, float rightPad) {
        int offset = 1;
        int leftOffset = Math.round(rootSize.getWidth() * leftPad);
        int rightOffset = Math.round(rootSize.getWidth() * rightPad);
        Point center = new Point(rootLocation.x + rootSize.getWidth() / 2, rootLocation.y + rootSize.getHeight() / 2);
        logger.debug("Swiping right at" +
                " x1: " + (rootLocation.getX() + leftOffset) +
                " y1:" + center.getY() +
                " x2:" + (rootLocation.getX() + rootSize.getWidth() - rightOffset + offset) +
                " y2:" + center.getY());
        swipe((rootLocation.getX() + leftOffset),
                center.getY(),
                (rootLocation.getX() + rootSize.getWidth() - rightOffset + offset),
                center.getY(),
                duration);
    }

    public void slide(WebElement origin_el, WebElement destination_el)
    {
        // appium converts longPress-moveto-release to a slide action
        TouchAction touchAction = new TouchAction(this);
        touchAction.longPress(origin_el).moveTo(destination_el).release().perform();
    }

    public WebElement findByUiautomator_text(String name) {
        return findElementByAndroidUIAutomator("new UiSelector().text(\""+name+"\")");
    }
    public WebElement findByUiautomator_textContains(String contains) {
        return findElementByAndroidUIAutomator("new UiSelector().textContains(\""+contains+"\")");
    }
    //desc
    public WebElement findByUiautomator_desc(String description) {
        return findElementByAndroidUIAutomator("new UiSelector().description(\""+description+"\")");
    }
    /**
     *
     * @param text
     * @param type 0 ：text
     *             1： id
     *             2：contains
     *             3：classname
     *             4: desc
     * @return
     */
    public WebElement switchcase(int type,String text){
        WebElement mushroom=null;
        switch (type){
            case 0:
                mushroom =
                       findByUiautomator_text(text);
                break;
            case 1:
                mushroom =
                       findElement(By.id(text));
                break;
            case 2:
                mushroom =
                      findElement(By.xpath("//*[contains(@text,\""+text+"\")]"));
                break;
            case 3:
                mushroom =
                        findElementByClassName(text);
                break;
            case 4:
                mushroom =
                        findByUiautomator_desc(text);
                break;
        }

        return  mushroom;
    }

    public WebElement waitElement(int time,int type,String text){
        WebElement element = (new WebDriverWait(this, time))
                .until(new ExpectedCondition<MobileElement>() {
                    @Override
                    public MobileElement apply(WebDriver d) {
                        return (MobileElement)switchcase(type,text);
                    }
                });
        return element;
    }

    public WebElement findElementInScrollView(String text,int duration,int type){
        WebElement element = (new WebDriverWait(this, 20))
                .until(new ExpectedCondition<MobileElement>() {
                    @Override
                    public MobileElement apply(WebDriver d) {
                        int width = manage().window().getSize().width;
                        int height = manage().window().getSize().height;
                        swipe(width / 2, height * 9 / 20, width / 2, height / 20, duration);
                        logger.info("scroll=======" + height * 3 / 4 + " " + height / 8);

                        return (MobileElement)switchcase(type,text);
                    }
                });
        return element;
    }


    public  void longClick(String id){
        WebElement longClick = findElement(By.id(id));
        new TouchAction(this).longPress(longClick).waitAction(Duration.ofSeconds(3)).perform();
    }

    /**
     *
     * @param text
     * @param duration
     * @param type
     *             0 ：text
     *             1： id
     *             2：contains
     *             3：classname
     *             4: desc
     */


    public void longClick(String text,int duration,int type){
        WebElement longClick=null;
        switch (type){
            case 0:
                longClick= findByUiautomator_text(text);
                break;
            case 1:
                longClick=findElement(By.id(text));
                break;
            case 2:
                longClick=findByUiautomator_textContains(text);
                break;
            case 3:
                longClick=findElement(By.className(text));
                break;
            case 4:
                longClick=findByUiautomator_desc(text);
                break;
        }
        new TouchAction(this).longPress(longClick).waitAction(Duration.ofSeconds(duration)).perform();
    }



}
