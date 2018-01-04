package com;

import io.appium.java_client.android.AndroidKeyCode;

import listener.AppiumListener;

import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import utils.BaseTestCase;
import javax.mail.MessagingException;
import java.io.IOException;


public class BPStrudentClientTest extends BaseTestCase {

    public Logger logger = LoggerFactory.getLogger(AppTest.class);
    WebDriverWait webDriverWait;


    //电话

//        driver.context("NATIVE_APP");


    @Test
    public void  testLogin() throws InterruptedException {
        webDriverWait=new WebDriverWait(driver,10);
        driver.findElement(By.id("cc.bodyplus:id/tv_login")).click();
        driver.findElement(By.id("cc.bodyplus:id/edit_login_phone")).sendKeys("13728963515");
        driver.findElement(By.id("cc.bodyplus:id/edit_login_password")).sendKeys("fzc8802653");
        driver.findElement(By.id("cc.bodyplus:id/tv_login")).click();
        String toast="聊天服务器登录失败";
        if (toastIsExist(5,toast)){
            AppiumListener.erro_list.add("Chat Server Erro");
        }
        erroCall();
    }
    @Test
    public void testAddCourse() throws InterruptedException {
        driver.findElement(By.id("cc.bodyplus:id/tab_menu_train_view"));
        driver.findByUiautomator_text("训练计划").click();
        if (isElementExist("new UiSelector().text(\"添加课程\")")){
            driver.findByUiautomator_text("添加课程").click();
        }else {
            driver.findElementInScrollView("添加课程",800,0).click();
        }
        driver.findElement(By.id("cc.bodyplus:id/image_bg")).click();
        driver.findElement(By.xpath("//*[contains(@text,\"跑步核心训练\")]")).click();
        driver.findElement(By.id("cc.bodyplus:id/image_button_join_plan")).click();
        driver.findElement(By.id("cc.bodyplus:id/text_sub")).click();
        //
        driver.findElement(By.id("cc.bodyplus:id/relative_load_play")).click();
        //cc.bodyplus:id/image_del
        driver.findElement(By.id("cc.bodyplus:id/image_del")).click();
//        webDriverWait=new WebDriverWait(driver,10);
//        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("cc.bodyplus:id/image_pause")));
        driver.waitElement(20,1,"cc.bodyplus:id/image_pause").click();
        sleep(5000);

        driver.findElement(By.id("cc.bodyplus:id/linear_over")).click();
        while (true){
            if (isElementExist(By.id("cc.bodyplus:id/tab_menu_train_view"))){
                break;
            }
            driver.pressKeyCode(AndroidKeyCode.BACK);
            sleep(1000);
        }
    }





    @Test
    public void testDeleteCourse(){
        driver.longClick("跑步核心训练",3,2);
        driver.findElement(By.id("cc.bodyplus:id/confirm")).click();



    }
    @Test
    public void testBindEquipment(){
        driver.findElement(By.id("cc.bodyplus:id/tab_menu_train_view")).click();
        driver.findElement(By.id("cc.bodyplus:id/image_equipment")).click();
        driver.findElement(By.id("cc.bodyplus:id/bt_start")).click();
        driver.waitElement(7,0,"则成").click();
        driver.findElement(By.id("cc.bodyplus:id/tv_bond")).click();
        if (!toastIsExist(6,"绑定成功")){
            AppiumListener.erro_list.add("Bind Core erro");
        }
        driver.findElement(By.id("cc.bodyplus:id/imageButton_back")).click();
        erroCall();
    }



    @Test
    public void testFreeSports() throws InterruptedException {
        driver.findElement(By.id("cc.bodyplus:id/tab_menu_train_view")).click();
        driver.findByUiautomator_text("自由训练").click();
        driver.findElement(By.id("cc.bodyplus:id/free_normal")).click();
        if (isElementExist(By.id("cc.bodyplus:id/image_del"))){
            driver.findElement(By.id("cc.bodyplus:id/image_del")).click();
        }
        Thread.sleep(40000);
        if (isElementExist("new UiSelector().text(\"- -\")")){
            AppiumListener.erro_list.add("No data");
            driver.findElement(By.id("cc.bodyplus:id/image_sub")).click();
            driver.findElement(By.id("cc.bodyplus:id/dialog_confirm_btn")).click();
            driver.findElement(By.id("cc.bodyplus:id/img_exit")).click();
        }else {
            driver.findElement(By.id("cc.bodyplus:id/image_sub")).click();
            driver.findElement(By.id("cc.bodyplus:id/dialog_confirm_btn")).click();
            driver.findElement(By.id("cc.bodyplus:id/text_count")).click();
            driver.waitElement(3,2,"什么是心率区间").click();
            driver.findElement(By.id("cc.bodyplus:id/base_title_left_imageButton")).click();
            driver.findElement(By.id("cc.bodyplus:id/base_title_right_imageButton")).click();
        }
        driver.findElement(By.id("cc.bodyplus:id/tab_menu_me_view")).click();
        driver.findElement(By.id("cc.bodyplus:id/view_history_record")).click();
        try {
            driver.waitElement(2,0,"自由训练");
        }catch (Exception e){
            AppiumListener.erro_list.add("No history");
        }
       driver.findElement(By.id("cc.bodyplus:id/base_title_left_imageButton")).click();
        erroCall();
    }
    @Test
    public void testDeleteFreeSport(){
        driver.findElement(By.id("cc.bodyplus:id/tab_menu_train_view")).click();
        driver.longClick("初级",3,0);
        driver.findElement(By.id("cc.bodyplus:id/confirm")).click();
        if (!toastIsExist(3,"删除成功")){
            AppiumListener.erro_list.add("Delete FreeSport Erro");
        }
        erroCall();
    }


    @Test
    public  void  testUnBindEquipment(){
        driver.findElement(By.id("cc.bodyplus:id/tab_menu_train_view")).click();
        driver.findElement(By.id("cc.bodyplus:id/image_equipment")).click();
        driver.findElement(By.id("cc.bodyplus:id/txt_remove_binding1")).click();
        driver.findElement(By.id("cc.bodyplus:id/confirm")).click();
        if (!toastIsExist(3,"解绑成功")){
            AppiumListener.erro_list.add("UnBind Equipment Erro");
        }
        erroCall();
    }
    @AfterTest
    public void tearDown() throws IOException, MessagingException {
//        driver.removeApp("cc.bodyplus");
//        driver.quit();
//        sendMail();
    }

}
