package UI;

import org.openqa.selenium.By;
import utils.AndroidDriver;

public class MainPage extends ObjectPage{
    public static final String XUNLIAN_BUTTON="cc.bodyplus:id/tab_menu_train_view";
    public static final String TONGJI_BUTTON="cc.bodyplus:id/tab_menu_chart_view";
    public static final String JULEBU_BUTTON="cc.bodyplus:id/tab_menu_equipment_view";
    public static final String FAXIAN_BUTTON="cc.bodyplus:id/tab_menu_find_view";
    public static final String ME_BUTTON="cc.bodyplus:id/tab_menu_me_view";

    public MainPage(AndroidDriver driver){
        this.driver = driver;

    }
    public void loginPracticePage(){
        driver.findElement(By.id(XUNLIAN_BUTTON)).click();
    }
    public void loginStatisticsPage(){
        driver.findElement(By.id(TONGJI_BUTTON)).click();
    }
    public void loginClubPage(){
        driver.findElement(By.id(JULEBU_BUTTON)).click();
    }
    public void loginDiscoverPage(){
        driver.findElement(By.id(FAXIAN_BUTTON)).click();
    }
    public void loginMinePage(){
        driver.findElement(By.id(ME_BUTTON)).click();
    }
}
