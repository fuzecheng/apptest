//package pageutil;
//
//import com.esotericsoftware.yamlbeans.YamlException;
//import model.CheckPoint;
//import model.TestCase;
//import org.openqa.selenium.WebDriver;
//import utils.AndroidDriver;
//import utils.OperateElement;
//import utils.YamlReader;
//
//import java.io.FileNotFoundException;
//import java.util.List;
//import java.util.Map;
//
//public class LoginPage {
//    YamlReader yamlReader;
//    OperateElement operateElement;
//    protected AndroidDriver driver;
//    private boolean isOperate=true;
//
//    public LoginPage(AndroidDriver driver,String path){
//        this.driver=driver;
//        yamlReader=new YamlReader(path);
//        operateElement= new OperateElement(this.driver);
//
//    }
//
//    public void operate() throws YamlException, FileNotFoundException, InterruptedException {
//        List list = (List) yamlReader.getYaml().get("testcase");
//        for(Object item: list){
//            TestCase testCase = new TestCase();
//            testCase.setFind_type((String) ((Map)item).get("find_type"));
//            testCase.setElement_info((String) ((Map)item).get("element_info"));
//            testCase.setText((String) ((Map)item).get("text"));
//            testCase.setOperate_type((String) ((Map)item).get("operate_type"));
//            if (!operateElement.operate(testCase)) {
//                isOperate = false;
//                System.out.println("操作失败");
//                break;
//            }
//
//        }
//
//    }
//
//
//    public boolean checkpoint() throws YamlException, FileNotFoundException, InterruptedException {
//        if (!isOperate) { // 如果操作步骤失败，检查点也就判断失败
//            System.out.println("前置条件失败");
//            return false;
//        }
//        List list = (List) yamlReader.getYaml().get("checkpoint");
//        for(Object item: list){
//            CheckPoint checkPoint = new CheckPoint();
//            checkPoint.setElement_info((String) ((Map)item).get("element_info"));
//            checkPoint.setFind_type((String) ((Map)item).get("find_type"));
//            if (!operateElement.checkElement(checkPoint)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//}
