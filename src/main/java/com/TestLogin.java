package com;
import UI.login.FirstPage;
import UI.login.LoginPageUi;
import UI.practice.PracticePage;
import org.testng.annotations.*;
import utils.BaseTestCase;


public class TestLogin extends BaseTestCase {
    LoginPageUi loginPageUi;
    FirstPage firstPage;
    PracticePage practicePage;



    @BeforeMethod
    public void setUpClass(){
        firstPage =new FirstPage(driver);
        loginPageUi = new LoginPageUi(driver);
        practicePage = new PracticePage(driver);
    }

    @Test
    public void testErroLogin(){
        if (firstPage.isNow()){
            firstPage.GoLogin();
            loginPageUi.loginByPhoneNumber("asdasdasdasdasdasd","ZDSASDASDASD");
            loginPageUi.checkLoginErro();
        }


    }
    @Test
    public void testLogin(){
        if (loginPageUi.isNow()){
            loginPageUi.loginByPhoneNumber();
            loginPageUi.checkLoginSuccess();
        }

    }


}
