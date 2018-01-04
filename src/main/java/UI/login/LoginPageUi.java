package UI.login;

import UI.MainPage;
import UI.ObjectPage;
import org.openqa.selenium.By;
import utils.AndroidDriver;

public class LoginPageUi extends ObjectPage {
    /**
     * 登录页面
     */
    public static final String TITLE = "cc.bodyplus:id/base_title_center_textView";
    public static final String LOGIN_ACCOUNT_TEXTVIEW="cc.bodyplus:id/edit_login_phone";
    public static final String LOGIN_PASSWORD_TEXTVIEW="cc.bodyplus:id/edit_login_password";
    public static final String LOGIN_SUBMIT_BUTTON="cc.bodyplus:id/tv_login";
    public static final String FORGET_PASSWORD="cc.bodyplus:id/tv_forgot_password";
    public static final String WECHAT_BUTTON="cc.bodyplus:id/view_wechat";
    public static final String QQ_BUTTON="cc.bodyplus:id/view_qq";
    public static final String WEIBO_BUTTON="cc.bodyplus:id/view_microblog";
    private static final String ACCOUNT="13728963515";
    private static final String PASSWORD="fzc8802653";
    private static final String ERROR_TOAST=".//*[contains(@text,'请输入有效的手机号')]";

    public LoginPageUi(AndroidDriver driver){
        this.driver=driver;
    }

    public void loginByPhoneNumber(){
        viewClear();
        driver.findElement(By.id(LOGIN_ACCOUNT_TEXTVIEW)).sendKeys(ACCOUNT);
        driver.findElement(By.id(LOGIN_PASSWORD_TEXTVIEW)).sendKeys(PASSWORD);
        driver.findElement(By.id(LOGIN_SUBMIT_BUTTON)).click();
    }
    public void loginByPhoneNumber(String account,String password){
        viewClear();
        driver.findElement(By.id(LOGIN_ACCOUNT_TEXTVIEW)).sendKeys(account);
        driver.findElement(By.id(LOGIN_PASSWORD_TEXTVIEW)).sendKeys(password);
        driver.findElement(By.id(LOGIN_SUBMIT_BUTTON)).click();
    }
    public void forgetPassWord(){
        driver.findElement(By.id(FORGET_PASSWORD)).click();
    }
    public void logingByWechat(){
        driver.findElement(By.id(WECHAT_BUTTON)).click();
    }
    public void logingByQQ(){
        driver.findElement(By.id(QQ_BUTTON)).click();
    }
    public void loginByWeibo(){
        driver.findElement(By.id(WEIBO_BUTTON)).click();
    }
    public void viewClear(){
        driver.findElement(By.id(LOGIN_ACCOUNT_TEXTVIEW)).clear();
        driver.findElement(By.id(LOGIN_PASSWORD_TEXTVIEW)).clear();
    }
    public boolean checkLoginSuccess(){
        return waitForElement(By.id(MainPage.XUNLIAN_BUTTON));
    }
    public boolean checkLoginErro(){
        return waitForElement(By.xpath(ERROR_TOAST));
    }
    public boolean isNow(){return isElementExist(By.id(TITLE));}
}
