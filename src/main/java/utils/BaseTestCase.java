package utils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.events.EventFiringWebDriverFactory;
import io.appium.java_client.events.api.general.ElementEventListener;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import model.AppiumSettings;
import model.MailsProperties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.time.Duration;

public class BaseTestCase {
    public static AndroidDevice driver;
    public static Logger logger= LoggerFactory.getLogger(BaseTestCase.class);
    public static TouchAction action;


    public void setdriver(AndroidDevice driver){
        BaseTestCase.driver =driver;
    }


    public void takescreen(String filename){
        ScreenScr.getScreen(driver, filename);

    }
    public static void sleep(int seconds) throws InterruptedException {
        Thread.sleep(seconds);
    }



    public  void longClick(String id){
        WebElement longClick = driver.findElement(By.id(id));
        new TouchAction(driver).longPress(longClick).waitAction(Duration.ofSeconds(3)).perform();
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
                longClick=driver.findByUiautomator_text(text);
                break;
            case 1:
                longClick=driver.findElement(By.id(text));
                break;
            case 2:
                longClick=driver.findByUiautomator_textContains(text);
                break;
            case 3:
                longClick=driver.findElement(By.className(text));
                break;
            case 4:
                longClick=driver.findByUiautomator_desc(text);
                break;
        }
        new TouchAction(driver).longPress(longClick).waitAction(Duration.ofSeconds(duration)).perform();
    }



    public static boolean isElementExist(AndroidDevice driver,By Locator) {
        try {
            driver.findElement(Locator);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return false;
        }
    }

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
        } catch (Exception ex) {
            return false;
        }

    }
    public static boolean isElementExist(AndroidDevice driver,String uiautomator){
        try {
            driver.findElementByAndroidUIAutomator(uiautomator);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }
    public DesiredCapabilities settings(AppiumSettings appiumSettings){
        File classpathRoot = new File(System.getProperty("user.dir"));
        //File appDir = new File(classpathRoot, "apps");
        File app = new File(appiumSettings.getApkPath()+"\\"+appiumSettings.getApk_file_name());
        logger.info(appiumSettings.getApkPath()+"\\"+appiumSettings.getApk_file_name());
        DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
        desiredCapabilities.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, "");
        desiredCapabilities.setCapability("platformName",appiumSettings.getPlantform());
        desiredCapabilities.setCapability("deviceName", appiumSettings.getDevice_name());
        desiredCapabilities.setCapability("noReset", "True");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        desiredCapabilities.setCapability("platformVersion", appiumSettings.getPlatform_version());
        desiredCapabilities.setCapability("app", app.getAbsolutePath());
        desiredCapabilities.setCapability("appPackage", appiumSettings.getAppPackage());
        desiredCapabilities.setCapability("appActivity", appiumSettings.getAppActivity());
        desiredCapabilities.setCapability("unicodeKeyboard", "True");
        desiredCapabilities.setCapability("resetKeyboard", "True");
        return desiredCapabilities;
    }


    public boolean imgCompare(String define_name,String screen_shot_name) throws IOException {

        File f1 = new File("queryimages/"+define_name+".png");
        BufferedImage img1 = ScreenUtil.getImageFromFile(f1);
        File f2 = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(f2, new File("target/reports/screenshots/"+screen_shot_name+".png"));
        BufferedImage img2 = ScreenUtil.getImageFromFile(f2);
        Boolean same =ScreenUtil.sameAs(img1, img2, 0.98);
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
        properties.setKey("lsslyuequtbodgah");
        properties.setMail_user("2776119050@qq.com");
        properties.setPort(25);
        properties.setReceiver(cc);
        mailUtils.sendMail(properties);
    }

    /**
     *
     * @param text
     * @param duration
     * @param type 0 ：text
     *             1： id
     *             2：contains
     *             3：classname
     *             4: desc
     * @return
     */

    public WebElement findElementInScrollView(String text,int duration,int type){
        MobileElement element = (new WebDriverWait(driver, 20))
                .until(new ExpectedCondition<MobileElement>() {
                    @Override
                    public MobileElement apply(WebDriver d) {
                        int width = driver.manage().window().getSize().width;
                        int height = driver.manage().window().getSize().height;
                        driver.swipe(width / 2, height * 9 / 20, width / 2, height / 20, duration);
                        logger.info("scroll=======" + height * 3 / 4 + " " + height / 8);
                        WebElement mushroom=null;
                        switch (type){
                            case 0:
                                mushroom =
                                        driver.findByUiautomator_text(text);
                                break;
                            case 1:
                                mushroom =
                                        driver.findElement(By.id(text));
                                break;
                            case 2:
                                mushroom =
                                        driver.findByUiautomator_textContains(text);
                                break;
                            case 3:
                                mushroom =
                                        driver.findElementByClassName(text);
                                break;
                            case 4:
                                mushroom =
                                        driver.findByUiautomator_desc(text);
                                break;
                        }
                        return (MobileElement) mushroom;
                    }
                });
            return element;
    }

        public boolean toastIsExist(int time,String toast){
                try {
                    WebDriverWait driverWait = new WebDriverWait(driver, time);
                    driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@text,'" + toast + "')]")));
                    return true;
                }catch (Exception e){
                    return false;
                }
        }


}
