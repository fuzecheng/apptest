package UI.practice;

import UI.ObjectPage;
import org.openqa.selenium.By;
import utils.AndroidDriver;

public class EquipmentBindPage extends ObjectPage{
    public static final String START_BIND1 = "cc.bodyplus:id/bt_start";
    public static final String START_BIND2 = "cc.bodyplus:id/iv_add";
    public static final String TITLE = "绑定您的智能服装";
    public EquipmentBindPage(AndroidDriver driver){
        this.driver = driver;
    }
    public void startBind(){
        driver.findElement(By.id(START_BIND1)).click();
    }
    public  boolean isNow(){
        return isElementExist(TITLE);
    }
}
