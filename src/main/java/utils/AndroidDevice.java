package utils;

import interfaceUtil.UiWatcher;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import jdk.internal.instrumentation.Tracer;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AndroidDevice extends AndroidDriver {
    Logger logger = LoggerFactory.getLogger(AndroidDevice.class);
    private boolean mInWatcherContext = false;
    private final List<String> mWatchersTriggers = new ArrayList<String>();
    private final HashMap<String, UiWatcher> mWatchers = new HashMap<String, UiWatcher>();

    public void registerWatcher(String name, UiWatcher watcher) {

        if (mInWatcherContext) {
            throw new IllegalStateException("Cannot register new watcher from within another");
        }
        mWatchers.put(name, watcher);
    }
    public void removeWatcher(String name) {
        if (mInWatcherContext) {
            throw new IllegalStateException("Cannot remove a watcher from within another");
        }
        mWatchers.remove(name);
    }
    public void runWatchers() {
        if (mInWatcherContext) {
            return;
        }

        for (String watcherName : mWatchers.keySet()) {
            UiWatcher watcher = mWatchers.get(watcherName);
            if (watcher != null) {
                try {
                    mInWatcherContext = true;
                    if (watcher.checkForCondition()) {
                        setWatcherTriggered(watcherName);
                    }
                } catch (Exception e) {
                    logger.info( "Exceuting watcher: " + watcherName, e);
                } finally {
                    mInWatcherContext = false;
                }
            }
        }
    }

    public boolean hasWatcherTriggered(String watcherName) {

        return mWatchersTriggers.contains(watcherName);
    }

    public void resetWatcherTriggers() {

        mWatchersTriggers.clear();
    }
    public boolean hasAnyWatcherTriggered() {

        return mWatchersTriggers.size() > 0;
    }
    private void setWatcherTriggered(String watcherName) {

        if (!hasWatcherTriggered(watcherName)) {
            mWatchersTriggers.add(watcherName);
        }
    }





    boolean isInWatcherContext() {
        return mInWatcherContext;
    }

    public AndroidDevice(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
    }

    public AndroidDevice(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
    }

    public AndroidDevice(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory, desiredCapabilities);
    }

    public AndroidDevice(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, desiredCapabilities);
    }

    public AndroidDevice(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(service, httpClientFactory, desiredCapabilities);
    }

    public AndroidDevice(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, desiredCapabilities);
    }

    public AndroidDevice(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(builder, httpClientFactory, desiredCapabilities);
    }

    public AndroidDevice(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, desiredCapabilities);
    }

    public AndroidDevice(Capabilities desiredCapabilities) {
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
}
