package UI.practice;

import UI.ObjectPage;
import org.openqa.selenium.By;
import utils.AndroidDriver;

public class PracticePage extends ObjectPage {

    public static final String EQUIPMENT_PAGE_BUTTON="cc.bodyplus:id/image_equipment";
    public static final String PRACTICE_PLAN_XPATH="//*[contains(@text,\"训练计划\")]";
    public static final String PRACTICE_FREE_XPATH="//*[contains(@text,\"自由训练\")]";
    public static final String PRACTICE_INSINDE_XPATH="//*[contains(@text,\"室内团课\")]";
    public static final String PRACTICE_OUTSIDE_XPATH="//*[contains(@text,\"户外运动\")]";

    public PracticePage(AndroidDriver driver){
        this.driver = driver;
    }

    public void loginEquipmentPage(){
        driver.findElement(By.id(EQUIPMENT_PAGE_BUTTON)).click();
    }
    public void loginPracticePlanPage(){
        driver.findElement(By.xpath(PRACTICE_PLAN_XPATH)).click();
    }
    public void loginPracticeFreePage(){
        driver.findElement(By.xpath(PRACTICE_FREE_XPATH)).click();
    }
    public void loginPracticeInsidePage(){
        driver.findElement(By.xpath(PRACTICE_INSINDE_XPATH)).click();
    }
    public void loginPracticeOutSidePage(){
        driver.findElement(By.xpath(PRACTICE_OUTSIDE_XPATH)).click();
    }
}
