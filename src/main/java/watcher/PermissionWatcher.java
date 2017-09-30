package watcher;

import interfaceUtil.UiWatcher;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AndroidDevice;




public class PermissionWatcher implements UiWatcher {
    AndroidDevice device;
    Logger logger= LoggerFactory.getLogger(PermissionWatcher.class);
    public PermissionWatcher(AndroidDevice device){
        this.device=device;

    }
    @Override
    public boolean checkForCondition() {
      device.findElement(By.name("Allow")).click();
      logger.info("PermissionWatcher");
      return true;
    }
}
