package watcher;

import utils.BaseTestCase;
import utils.FingerPrint;
import utils.ScreenUtil;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Test {
//"target/reports/screenshots/theme.png"
    public static void main(String[] args) throws IOException {
        FingerPrint fp1 = new FingerPrint(ImageIO.read(new File("queryimages/wallpaper.png")));
        FingerPrint fp2 =new FingerPrint(ImageIO.read(new File("target/reports/screenshots/wallpaper.png")));
        System.out.println(fp1.toString(true));
        System.out.printf("sim=%f",fp1.compare(fp2));
    }
}
