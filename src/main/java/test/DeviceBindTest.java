package test;

import UI.practice.EquipmentBindPage;
import UI.practice.EquipmentPage;
import UI.practice.PracticePage;
import UI.practice.SearchDevicePage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.BaseTestCase;

public class DeviceBindTest extends BaseTestCase{
    PracticePage practicePage ;
    EquipmentPage equipmentPage ;
    EquipmentBindPage equipmentBindPage ;
    SearchDevicePage searchDevicePage ;
    @BeforeMethod
    public void setUpClass(){
         practicePage = new PracticePage(driver);
         equipmentPage = new EquipmentPage(driver);
         equipmentBindPage = new EquipmentBindPage(driver);
         searchDevicePage = new SearchDevicePage(driver);
    }
    @Test
    public void bindDevice(){
        practicePage.loginEquipmentPage();
        if (equipmentBindPage.isNow()){
            equipmentBindPage.startBind();
            searchDevicePage.selectDevice("则成");
            searchDevicePage.bindDevie();
            searchDevicePage.checkBindSucess();
        }else {
            equipmentPage.unBind();
        }
    }
    @Test
    public void unBindDevice(){
        equipmentPage.unBind();
    }
    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
