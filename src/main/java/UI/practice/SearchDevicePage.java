package UI.practice;

import UI.ObjectPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.AndroidDriver;

import java.util.Iterator;
import java.util.List;

public class SearchDevicePage extends ObjectPage {

    public static final String CANCEL_ID = "cc.bodyplus:id/base_title_left_textView";
    public static final String BIND_ID = "cc.bodyplus:id/tv_bond";
    public static final String DEVICE_NAME_LIST = "cc.bodyplus:id/device_name";

    public SearchDevicePage(AndroidDriver driver){
        this.driver = driver;

    }

    public void selectDevice(String deviceName){
        waitForElement(By.id(BIND_ID));
        List<WebElement> elements = driver.findElements(By.id(DEVICE_NAME_LIST));
        Iterator iterator= elements.iterator();
        while (iterator.hasNext()){
            WebElement element = (WebElement) iterator.next();
            if (element.getText().equals(deviceName)){
                element.click();
                break;
            }
        }

    }

    public void bindDevie(){
        driver.findElement(By.id(BIND_ID)).click();
    }
    public void cancel (){
        driver.findElement(By.id(CANCEL_ID)).click();
    }

    public boolean checkBindSucess(){
       return waitForElement(By.xpath(".//*[contains(@text,\"绑定成功\")]"));
    }
}
