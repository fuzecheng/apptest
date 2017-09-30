package utils;

import io.appium.java_client.android.AndroidDriver;

public class BaseTestCase {
    public AndroidDriver driver;

    public void setdriver(AndroidDriver driver){
        this.driver=driver;
    }

    public void takescreen(String filename){
        ScreenScr.getScreen(driver, filename);
    }





}
