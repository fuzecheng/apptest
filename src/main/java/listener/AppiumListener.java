package listener;

import io.appium.java_client.events.api.general.AppiumWebDriverEventListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import utils.AndroidDevice;
import utils.BaseTestCase;

public class AppiumListener implements AppiumWebDriverEventListener {
    private Logger logger = LoggerFactory.getLogger(AppiumListener.class);

    AndroidDevice androidDevice;

    public AppiumListener(AndroidDevice androidDevice) {
        this.androidDevice = androidDevice;

    }


    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver) {

    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver) {

    }

    @Override
    public void beforeAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {

    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {

    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {

    }

    @Override
    public void afterNavigateBack(WebDriver driver) {

    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {

    }

    @Override
    public void afterNavigateForward(WebDriver driver) {

    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {

    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {

    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        while (true) {
            if (BaseTestCase.isElementExist("new UiSelector().text(\"Allow\")")) {
                logger.info("=================Allow click================");
                androidDevice.findElementByAndroidUIAutomator("new UiSelector().text(\"Allow\")").click();
            } else if (BaseTestCase.isElementExist("new UiSelector().text(\"Always\")") &&
                    (BaseTestCase.isElementExist("new UiSelector().text(\"Use Joy Launcher as Home\")"))) {
                androidDevice.findElementByAndroidUIAutomator("new UiSelector().text(\"Always\")").click();
                logger.info("================Always click================");
                break;
            } else if (BaseTestCase.isElementExist("new UiSelector().text(\"Always\")") &&
                    BaseTestCase.isElementExist("new UiSelector().text(\"Joy Launcher\")")) {
                androidDevice.findElementByAndroidUIAutomator("new UiSelector().text(\"Joy Launcher\")").click();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                androidDevice.findElementByAndroidUIAutomator("new UiSelector().text(\"Always\")").click();
                logger.info("=================Joy Launcher&&Always click======================");
                break;
            } else if (BaseTestCase.isElementExist("new UiSelector().textContains(\"Unfortunately\")")) {
                logger.error("-------------crash--------------");
                androidDevice.findElementByAndroidUIAutomator("new UiSelector().text(\"OK\")").click();
                Assert.assertTrue(false, "-------------crash--------------");
                break;
            } else {
                break;
            }
        }
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
//        System.out.println("afterFindBy:" + by.toString());


    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        // System.out.println("beforeClickOn:");


    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        // System.out.println("afterClickOn:");
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    @Override
    public void beforeScript(String script, WebDriver driver) {

    }

    @Override
    public void afterScript(String script, WebDriver driver) {

    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {

    }
}
