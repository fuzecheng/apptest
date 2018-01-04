//package utils;
//
//import com.esotericsoftware.yamlbeans.YamlException;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//
//public class YamlReader {
//    String path="";
//    public YamlReader(String path){
//        this.path=path;
//    }
//    public LinkedList getYaml() throws YamlException, FileNotFoundException {
//        String path=getClass().getResource(this.path).getPath();
//        com.esotericsoftware.yamlbeans.YamlReader reader=new com.esotericsoftware.yamlbeans.YamlReader(new FileReader(path));
//        Object object=reader.read();
//        System.out.println(object.toString());
//        return  (LinkedList) object;
//    }
//
//    public static void main(String[] args) throws YamlException, FileNotFoundException {
//        YamlReader yamlReader=new YamlReader("/Login.yaml");
//        yamlReader.getYaml();
//    }
//    public Map getYamlMapBy
//
//
//
//}
