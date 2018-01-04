package UI;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AndroidDriver;

public class ObjectPage {


       public AndroidDriver driver;


        public boolean waitForElement(final By elementLocator) {
            WebDriverWait wait=new WebDriverWait(driver,10);
        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
            return true;
        }
        catch (NoSuchElementException | ElementNotVisibleException | org.openqa.selenium.TimeoutException e) {
            return false;
        }

    }
    public boolean isElementExist(String xpath){
        try {
            driver.findElement(By.xpath("//*[contains(@text,\""+xpath+"\")]"));
            return true;
        } catch (Exception ex) {
            return false;
        }

    }
    public  boolean isElementExist(By Locator) {
        try {
            driver.findElement(Locator);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }



}
