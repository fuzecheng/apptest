package com;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.Connection;
import io.appium.java_client.events.EventFiringWebDriverFactory;
import io.appium.java_client.functions.ExpectedCondition;
import listener.AppiumListener;
import model.AppiumSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.AndroidDevice;
import utils.BaseTestCase;
import utils.LogCatHelper;
import utils.MailUtils;
import javax.annotation.Nullable;
import javax.mail.MessagingException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

/**
 * Created by Administrator on 2017/9/3.
 */


public class AppTest extends BaseTestCase {

    WebElement element;
    List setupeliment;
    List<WebElement> elements;
    HashMap<String, By> map;
    public Logger logger = LoggerFactory.getLogger(AppTest.class);
    LogCatHelper helper;


    @BeforeTest
    public void setUp() throws MalformedURLException, InterruptedException {
        //设置apk的路径
        AppiumSettings appiumSettings=new AppiumSettings();
        appiumSettings.setApk_file_name("2018launcher_v1.4.apk");
        appiumSettings.setPlantform("Android");
        appiumSettings.setDevice_name("192.168.185.101:5555");
        appiumSettings.setPlatform_version("6.0");
        appiumSettings.setAppPackage("com.tct.launcher");
        appiumSettings.setAppActivity(".Launcher");
        driver = new AndroidDevice(new URL("http://127.0.0.1:4723/wd/hub"), settings(appiumSettings));
        action = new TouchAction(driver);
        driver.context("NATIVE_APP");
        setdriver(driver);
        elements = new LinkedList();
        map = new LinkedHashMap<String, By>();
        driver = EventFiringWebDriverFactory.getEventFiringWebDriver(driver, new AppiumListener(driver));
        //初始化日志记录
        helper=new LogCatHelper("com.tct.launcher");
        helper.start();
    }

    /**
     * 初始化APP
     *
     * @throws InterruptedException
     */
    @Test
    public void testInstallsetUp() throws InterruptedException {
        try {
            int i = 0;
            while (i < 3){
                if (isElementExist(By.id("com.tct.launcher:id/instruction_next"))) {
                    driver.findElement(By.id("com.tct.launcher:id/instruction_next")).click();
                    i++;
                }else {
                    break;
                }

            }
        if (isElementExist("new UiSelector().textContains(\"Experience improvement\")"));{
                driver.findElement(By.id("android:id/button1")).click();
            }
            driver.findElement(By.id("com.tct.launcher:id/cling_dismiss_longpress_info")).click();
//            driver.pressKeyCode(AndroidKeyCode.HOME);
//            assertNotNull(driver.findElement(By.className("android.view.ViewGroup")));
        }
        catch (org.openqa.selenium.NoSuchElementException ex) {
            logger.info("====no always elemnet===");
        }
//        driver.pressKeyCode(AndroidKeyCode.HOME);
    }


    /**
     * 检查应用抽屉
     */
    @Test
    public void testLoginApps() throws InterruptedException {
//        driver.pressKeyCode(AndroidKeyCode.HOME);
//        driver.findElement(By.id("com.tct.launcher:id/launcher"));
//        sleep(1000);
        element = driver.findElementByAccessibilityId("Apps");
        element.click();
        map.put("com.tct.launcher:id/launcher", By.id("com.tct.launcher:id/launcher"));
        map.put("com.tct.launcher:id/drag_layer", By.id("com.tct.launcher:id/drag_layer"));
        map.put("com.tct.launcher:id/workspace", By.id("com.tct.launcher:id/workspace"));
        map.put("com.tct.launcher:id/apps_view", By.id("com.tct.launcher:id/apps_view"));
        map.put("com.tct.launcher:id/main_content", By.id("com.tct.launcher:id/main_content"));
        map.put("com.tct.launcher:id/search_container", By.id("com.tct.launcher:id/search_container"));
        map.put("com.tct.launcher:id/apps_list_view", By.id("com.tct.launcher:id/apps_list_view"));

        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            By by = (By) entry.getValue();
            if (!isElementExist(by)) {
                assertFalse(true, "NULL:" + entry.getKey());
            }
        }
        element = (new WebDriverWait(driver, 60))
                .until(new ExpectedCondition<MobileElement>() {
                    @Override
                    public MobileElement apply(WebDriver d) {

                        int width = driver.manage().window().getSize().width;
                        int height = driver.manage().window().getSize().height;
                        driver.swipe(width / 2, height * 9 / 20, width / 2, height / 20, 500);
                        logger.info("scroll=======" + height * 3 / 4 + " " + height / 8);
                        WebElement settings =
                                driver.findElementByAccessibilityId("Settings");
                        return (MobileElement) settings;
                    }
                });
        element.click();
        sleep(2000);
        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Home\")").click();
        driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.view.ViewGroup/" +
                "android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/" +
                "android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/" +
                "android.widget.ListView/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.RadioButton").click();
        driver.pressKeyCode(AndroidKeyCode.HOME);
    }

    /**
     * 测试默认 -1屏
     *
     * @throws InterruptedException
     */
    @Test
    public void testDefaultIscreen() throws InterruptedException {
        sleep(1000);
        driver.swipeRight();
        element=(new WebDriverWait(driver,2)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            @Override
            public WebElement apply(@Nullable WebDriver input) {
                return driver.findElement(By.id("com.tct.launcher:id/ok"));
            }
        });
         element.click();
        if (isElementExist(By.id("com.tct.launcher:id/workspace"))) {
            Assert.assertTrue(true);
        } else {
            assertFalse(true, "ISCREEN IS null");
        }
        driver.pressKeyCode(AndroidKeyCode.HOME);
    }

    /**
     * -1屏 关
     */
    @Test
    public void testIscreenClose() {
        try {
            //操作wifi
            //androidDriver.setConnection(Connection.WIFI);
            sleep(2000);
            controlSettingsButton();
            sleep(1000);
            driver.swipeRight();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isElementExist(By.id("com.tcl.mie.launcher.lscreen:id/layPopWin"))) {
            assertFalse(true, "ISCREEN IS exist");
        } else {
            assertTrue(true);
        }


    }

    /**
     * -1屏 开
     */
    @Test
    public void testIscreenOpen() throws InterruptedException {
        // driver.swipe(381,818,694,814,300);
        controlSettingsButton();
        sleep(1000);
        driver.swipeRight();
        if (isElementExist(By.id("com.tcl.mie.launcher.lscreen:id/layPopWin"))) {
            assertTrue(true);
        } else {
            assertFalse(true, "ISCREEN IS null,can not find com.tcl.mie.launcher.lscreen:id/layPopWin");
        }

    }

    /**
     * 替换壁纸
     *
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    public void testChangeWallpaper() throws InterruptedException, IOException {
        loginWallpaper();
        elements = driver.findElements(By.id("com.tct.launcher:id/wallpaper_image"));
        elements.get(2).click();
        element = driver.findElement(By.id("com.tct.launcher:id/set_wallpaper_button"));
        element.click();
        sleep(4000);
        assertTrue("Wallpaper is different", imgCompare("wallpaper", "wallpaper"));
        //还原
        loginWallpaper();
        elements = driver.findElements(By.id("com.tct.launcher:id/wallpaper_image"));
        elements.get(1).click();
        element = driver.findElement(By.id("com.tct.launcher:id/set_wallpaper_button"));
        element.click();

        sleep(4000);
        assertTrue("Default paper is different", imgCompare("default_paper", "default_paper"));
    }

    /**
     * 测试小部件
     *
     * @throws InterruptedException
     */
    @Test
    public void testWidget() throws InterruptedException {

        loginWidget();
        elements = driver.findElements(By.id("com.tct.launcher:id/widget_preview"));
        element = driver.findElement(By.className("android.view.ViewGroup"));
        driver.slide(elements.get(2), element);
        sleep(2000);
        if (isElementExist(By.className("android.widget.CheckBox"))) {
            driver.findElement(By.className("android.widget.CheckBox")).click();
        }
        if (isElementExist("new UiSelector().text(\"Create\")")) {
            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Create\")").click();
        }
//            driver.findElement(By.name("OK")).click();
        sleep(5000);
        if (isElementExist("new UiSelector().text(\"Sample\")")) {
            removeWidget("Sample");
            assertTrue(true);
        } else {
            assertTrue("===Can not find widget===", false);
        }
    }

    /**
     * 测试主题替换
     *
     * @throws InterruptedException
     */
    @Test
    public void testChangeTheme() throws InterruptedException, IOException {
        driver.pressKeyCode(AndroidKeyCode.HOME);
        sleep(2000);
        longClick("com.tct.launcher:id/workspace");
        if (isElementExist(By.id("com.tct.launcher:id/theme_button"))) {
            driver.findElement(By.id("com.tct.launcher:id/theme_button")).click();
//            WebDriverWait webDriverWait=new WebDriverWait(driver,5);
//            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.tcl.hawk.ts:id/dialog_tv_cancel"))).click();
            sleep(3000);
            if(!isElementExist(By.id("com.tcl.hawk.ts:id/base_activity_root_view"))){
                assertFalse(true);
            }
            element = (new WebDriverWait(driver, 60))
                    .until(new ExpectedCondition<MobileElement>() {
                        @Override
                        public MobileElement apply(WebDriver d) {

                            int width = driver.manage().window().getSize().width;
                            int height = driver.manage().window().getSize().height;
                            driver.swipe(width / 2, height * 9 / 20, width / 2, height / 20, 500);
                            logger.info("scroll=======" + height * 3 / 4 + " " + height / 8);
                            WebElement mushroom =
                                    driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.wi" +
                                            "dget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/" +
                                            "android.widget.FrameLayout[2]/android.widget.RelativeLayout/" +
                                            "android.support.v4.view.ViewPager/android.widget.FrameLayout/android.view.ViewGroup" +
                                            "/android.support.v7.widget.RecyclerView/android.widget.LinearLayout[2]/android.widget.TextView[1]");
                            return (MobileElement) mushroom;
                        }
                    });
            element.click();
            sleep(5000);
            driver.findElement(By.id("com.tcl.hawk.ts:id/pb_progress")).click();
            element = (new WebDriverWait(driver, 40)).until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver input) {
                    if (isElementExist("new UiSelector().text(\"Paused\")")) {
                        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Paused\")").click();
                    }
                    WebElement apply = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Apply\")");
                    return apply;
                }
            });
            element.click();
            sleep(10000);
            driver.pressKeyCode(AndroidKeyCode.HOME);
            if (isElementExist(By.id("com.tct.launcher:id/workspace"))) {
                assertTrue(imgCompare("theme", "theme"));
            } else {
                assertTrue("-------Not BACK-----", false);
            }
        } else {
            assertTrue("-------Not installed ThemeStore-----", false);
        }


    }



    /**
     *
     * 意见反馈
     *
     * @throws IOException
     * @throws MessagingException
     */
    @Test
    public void testFeedBack() throws InterruptedException {
        loginFeedBack();
        driver.setConnection(Connection.WIFI);
        driver.findElement(By.id("com.tct.launcher:id/rating_bar")).click();
        driver.findElement(By.id("com.tct.launcher:id/feedback_title_submit")).click();
        WebDriverWait webDriverWait=new WebDriverWait(driver,2);
        /**
         * 通过Xpath的方式解决Toast断言问题
         */
        String toast="Problem description cannot be empty!";
        assertNotNull(webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath((".//*[contains(@text,'"+ toast + "')]")))));


    }
    @Test
    public void testSearch() throws InterruptedException {
        driver.pressKeyCode(AndroidKeyCode.HOME);
        sleep(3000);
        driver.swipeUp();
        if (!map.isEmpty()){
            map.clear();
        }
        map.put("com.tct.launcher:id/search_editText",By.id("com.tct.launcher:id/search_editText"));
        map.put("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.FrameLayout[1]",By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.FrameLayout[1]"));
        map.put("com.tct.launcher:id/hot_word_title",By.id("com.tct.launcher:id/hot_word_title"));
        map.put("com.tct.launcher:id/title",By.id("com.tct.launcher:id/title"));
        Iterator iterator=map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            By value=(By) entry.getValue();
            String key=(String) entry.getKey();
            if (!isElementExist(value)){
                logger.error("no element"+key);
            }
        }
        driver.findElementById("com.tct.launcher:id/search_editText").sendKeys("english");
        driver.findElementById("com.tct.launcher:id/search_appcenter_item_name").click();
    }







    @AfterTest
    public void tearDown() throws IOException, MessagingException {
        helper.stop();
        Runtime.getRuntime().exec("adb shell pm clear com.tcl.hawk.ts");
        driver.removeApp("com.tct.launcher");
        driver.quit();
//        sendMail();
    }


    /**
     * 进入背景界面
     */
    public void loginWallpaper() {
        driver.pressKeyCode(AndroidKeyCode.HOME);
        longClick("com.tct.launcher:id/workspace");
        element = driver.findElement(By.id("com.tct.launcher:id/wallpaper_button"));
        element.click();

    }
    /**
     * 操作-1屏开关按钮
     *
     * @throws InterruptedException
     */

    public void controlSettingsButton() throws InterruptedException {
        longClick("com.tct.launcher:id/workspace");
        element = driver.findElement(By.id("com.tct.launcher:id/settings_button"));
        element.click();
        elements = driver.findElements(By.id("android:id/switchWidget"));
        elements.get(1).click();
        sleep(500);
        driver.pressKeyCode(AndroidKeyCode.HOME);

    }



    /**
     * 进入小部件
     */
    public void loginWidget() {
        driver.pressKeyCode(AndroidKeyCode.HOME);
        longClick("com.tct.launcher:id/workspace");
        element = driver.findElement(By.id("com.tct.launcher:id/widget_button"));
        element.click();
//        List<LogEntry> crashEntries = driver.manage().logs().get("crashlog").getAll();
//        if (crashEntries.size() > 0) {
//            logger.info("CRASH ENTRY DETECTED"+crashEntries.get(0).getMessage());
//        }
    }
    /**
     * 删除小部件
     *
     * @param widget
     */
    public void removeWidget(String widget) {
        element = driver.findElementByAccessibilityId("Sample");
        action.longPress(element).perform().moveTo(driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"Remove\")")).release().perform();
        //action.longPress(element).perform().moveTo(driver.findElementByAccessibilityId("Remove")).release().perform();
        driver.pressKeyCode(AndroidKeyCode.HOME);
    }
    /**
     *  进入反馈页面
     */

    public void loginFeedBack() throws InterruptedException {
        longClick("com.tct.launcher:id/workspace");
        driver.findElement(By.id("com.tct.launcher:id/settings_button")).click();
        sleep(4000);
        driver.findByUiautomator_text("Feedback").click();
        sleep(500);
    }

}
