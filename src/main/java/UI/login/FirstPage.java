package UI.login;

import UI.ObjectPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import utils.AndroidDriver;

public class FirstPage extends ObjectPage{
    public static final String REGIST_ID="cc.bodyplus:id/tv_register";
    public static final String LOGIN_ID="cc.bodyplus:id/tv_login";
    public static final String LOGO = "cc.bodyplus:id/rl_logo";
    public FirstPage(AndroidDriver driver){
        this.driver=driver;
    }
    public void Goregist(){
        driver.findElement(By.id(REGIST_ID)).click();
    }
    public void GoLogin(){
        System.out.println( driver.findElement(By.id(LOGIN_ID)).getText());
        driver.findElement(By.id(LOGIN_ID)).click();
    }
    public boolean isNow(){
        return isElementExist(By.id(LOGO));
    }

}
