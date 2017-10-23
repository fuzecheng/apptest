package utils;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;

public class Akaze {

    public static void main(String[] args) {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat src1 = Imgcodecs.imread("queryimages/theme.png");
            Mat src2 = Imgcodecs.imread("target/reports/screenshots/theme.png");
            if (src1.empty() || src2.empty()) {
                throw new Exception("no file");
            }

            MatOfKeyPoint keypoint1 = new MatOfKeyPoint();
            MatOfKeyPoint keypoint2 = new MatOfKeyPoint();
            FeatureDetector sifDetector = FeatureDetector.create(FeatureDetector.AKAZE);
            sifDetector.detect(src1, keypoint1);

            sifDetector.detect(src2, keypoint2);

            DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.AKAZE);

            Mat descriptor1 = new Mat(src1.rows(), src1.cols(), src1.type());
            extractor.compute(src1, keypoint1, descriptor1);
            Mat descriptor2 = new Mat(src2.rows(), src2.cols(), src2.type());
            extractor.compute(src2, keypoint2, descriptor2);

            MatOfDMatch matches = new MatOfDMatch();
            DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);

            matcher.match(descriptor1, descriptor2, matches);

            Mat dst = new Mat();
            Features2d.drawMatches(src1, keypoint1, src2, keypoint2, matches, dst);

            Imgcodecs.imwrite("img/akaze.png", dst);
        } catch (Exception e) {
            System.out.println("例外:" + e);
        }

    }

}
