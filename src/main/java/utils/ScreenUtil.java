package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenUtil {

    public static boolean sameAs(BufferedImage myImage, BufferedImage otherImage, double percent)
    {
        //BufferedImage otherImage = other.getBufferedImage();
        //BufferedImage myImage = getBufferedImage();


        if (otherImage.getWidth() != myImage.getWidth()) {
            return false;
        }
        if (otherImage.getHeight() != myImage.getHeight()) {
            return false;
        }

        int[] otherPixel = new int[1];
        int[] myPixel = new int[1];

        int width = myImage.getWidth();
        int height = myImage.getHeight();

        int numDiffPixels = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (myImage.getRGB(x, y) != otherImage.getRGB(x, y)) {
                    numDiffPixels++;
                }
            }
        }
        double numberPixels = height * width;
        double diffPercent = numDiffPixels / numberPixels;
        return percent <= 1.0D - diffPercent;
    }

    public static BufferedImage getSubImage(BufferedImage image,int x, int y, int w, int h)
    {
        return image.getSubimage(x, y, w, h);
    }


    public static BufferedImage getImageFromFile(File f) {

        BufferedImage img = null;

        try {
            img = ImageIO.read(f);

        } catch (IOException e) {
            //if failed, then copy it to local path for later check:TBD
            //FileUtils.copyFile(f, new File(p1));
            e.printStackTrace();
            System.exit(1);
        }
        return img;
    }

    public static boolean compare(String define_name,String screen_shot_name){
        File f1 = new File("queryimages/"+define_name+".png");
        BufferedImage img1 = ScreenUtil.getImageFromFile(f1);
        File f2 = new File("target/reports/screenshots/"+screen_shot_name+".png");
        BufferedImage img2 = ScreenUtil.getImageFromFile(f2);
        Boolean same =ScreenUtil.sameAs(img1, img2, 0.98);
        return same ;
    }






}
