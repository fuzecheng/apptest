package listener;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.AndroidDevice;
import utils.BaseTestCase;

public class ElementEventListener implements io.appium.java_client.events.api.general.ElementEventListener
{
//    AndroidDevice device;
//    WebElement element;
//    public ElementEventListener( AndroidDevice device,WebElement element){
//        this.device=device;
//        this.element=element;
//    }


    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        if (BaseTestCase.isElementExist(By.name("Allow"))){
            driver.findElement(By.name("Allow")).click();
        }else if (BaseTestCase.isElementExist("new UiSelector().textContains(\"Add note\")")){
            driver.findElement(By.name("OK")).click();
        }
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {

    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver) {

    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver) {

    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }
}
