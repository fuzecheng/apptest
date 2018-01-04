package model;

import io.appium.java_client.events.EventFiringWebDriverFactory;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import listener.AppiumListener;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.AndroidDriver;
import utils.LogCatHelper;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverInstance {
    private static volatile DriverInstance instance=new DriverInstance();
    private static AndroidDriver androidDriver;
    private static LogCatHelper helper;

    public static DriverInstance getInstance() throws MalformedURLException {
        synchronized (DriverInstance.class){
            if (instance==null){
                instance=new DriverInstance();
            }if (androidDriver==null){
                File app = new File(FilePath.APP_PATH+"\\"+FilePath.APP_NAME);
                DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
                desiredCapabilities.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, "");
                desiredCapabilities.setCapability("platformName","Android");
                desiredCapabilities.setCapability("deviceName", "0715f7bdaaec1938");
                desiredCapabilities.setCapability("noReset", "True");
                desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
                desiredCapabilities.setCapability("platformVersion", "7.0");
                desiredCapabilities.setCapability("app", app.getAbsolutePath());
                desiredCapabilities.setCapability("appPackage", "cc.bodyplus");
                desiredCapabilities.setCapability("appActivity",".mvp.view.login.activity.SplashActivity" );
                desiredCapabilities.setCapability("unicodeKeyboard", "True");
                desiredCapabilities.setCapability("resetKeyboard", "True");
                androidDriver=new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),desiredCapabilities);
                androidDriver = EventFiringWebDriverFactory.getEventFiringWebDriver(androidDriver, new AppiumListener(androidDriver));
                helper=new LogCatHelper("cc.bodyplus");
                helper.start();
            }
        }
        return instance;
    }
    private DriverInstance(){
    }
    public AndroidDriver getAndroidDriver(){
        return androidDriver ;
    }


    public void quit(){
        try {
            helper.stop();
            getInstance().getAndroidDriver().quit();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


}

