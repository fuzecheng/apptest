package UI.practice;

import UI.ObjectPage;
import org.openqa.selenium.By;
import utils.AndroidDriver;

public class EquipmentPage extends ObjectPage {

    public static final String NAME_ID = "cc.bodyplus:id/device_name1";
    public static final String RENAME_ID = "cc.bodyplus:id/txt_rename1";
    public static final String UNBINDE_ID = "cc.bodyplus:id/txt_remove_binding1";


    public static final String TESTING_HEART = "cc.bodyplus:id/rl_heart_assess";
    public static final String TESTING_MUSCLE ="cc.bodyplus:id/rl_muscle_assess";
    public static final String ANALYSIS_ACTION = "cc.bodyplus:id/rl_action_assess";

    public static final String RENAME_TEXT = "cc.bodyplus:id/modify_content";
    public static final String RENAME_CONFIRM = "cc.bodyplus:id/modify_submit";
    public static final String RENAME_CANCEL = "cc.bodyplus:id/modify_cancel";

    public static final String UNBIND_CONFIRM = "cc.bodyplus:id/confirm";
    public static final String UNBIND_CANCEL = "cc.bodyplus:id/cancel";
    public static final String LOGIN_SEARCH_DEVICE = "cc.bodyplus:id/iv_add";

    public EquipmentPage(AndroidDriver driver){
        this.driver = driver ;
    }

    public void deviceRename (String name){
        driver.findElement(By.id(RENAME_ID)).click();
        driver.findElement(By.id(RENAME_TEXT)).clear();
        driver.findElement(By.id(RENAME_TEXT)).sendKeys(name);
        driver.findElement(By.id(RENAME_CONFIRM)).click();

    }
    public String getDevieName (){
        return driver.findElement(By.id(NAME_ID)).getText();
    }
    public void unBind(){
        driver.findElement(By.id(UNBINDE_ID)).click();
        driver.findElement(By.id(UNBIND_CONFIRM)).click();
    }
    public void loginHeartTesting (){
        driver.findElement(By.id(TESTING_HEART)).click();
    }

    public void loginMuscleTesting (){
        driver.findElement(By.id(TESTING_MUSCLE)).click();
    }
    public void loginAnalysisAction (){
        driver.findElement(By.id(ANALYSIS_ACTION)).click();
    }
    public void loginSearchPage(){
        driver.findElement(By.id(LOGIN_SEARCH_DEVICE)).click();
    }
}
