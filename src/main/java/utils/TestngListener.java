package utils;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestngListener extends TestListenerAdapter {
    public Logger logger = LoggerFactory.getLogger(TestngListener.class);
//    protected ExtentReports extent;
//    protected ExtentTest test;

    @Override
    public void onTestStart(ITestResult tr) {
        super.onTestStart(tr);
        logger.info("【" + tr.getName() + " Start】");
        LogCatHelper.msg="======================================"+tr.getName()+"========================================================";
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        logger.info("【" + tr.getName() + " Failure】");
        takeScreenShot(tr);
    }

    public void takeScreenShot(ITestResult tr){
        BaseTestCase baseTestcase=(BaseTestCase)tr.getInstance();
        baseTestcase.takescreen(tr.getName());


    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        takeScreenShot(tr);
        logger.info("【" + tr.getName() + " Skipped】");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        logger.info("【" + tr.getName() + " Success】");
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);

    }
}
