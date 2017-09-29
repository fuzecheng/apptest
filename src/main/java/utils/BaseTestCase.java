package utils;

import io.appium.java_client.AppiumDriver;

public class BaseTestCase {
    public AppiumDriver driver;

    public void setdriver(AppiumDriver driver){
        this.driver=driver;
    }

    public void takescreen(String filename){
        ScreenScr.getScreen(driver, filename);
    }
}
