package utils;

import model.FilePath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static void main(String[]args){
        deleteFileByFormat(FilePath.TEST_OUTPUT);
    }
    public static boolean forbidFormat(String threeLetter){
        List<String> fileFormat = new ArrayList<String>();
        fileFormat.add("png");
        if(fileFormat.contains(threeLetter)){
            return true;
        }else{
            return false;
        }
    }

    public  static void deleteFileByFormat(String filepath){
        File file = new File(filepath);
        boolean isDirectory = file.isDirectory();
        if (isDirectory) {
            String[] files = file.list();
            for (String path : files) {
                String afterPointLetter = path.substring(path.lastIndexOf(".")+1,path.length());
                if(forbidFormat(afterPointLetter)){
                    System.out.println(filepath+path);
                    File deleteFile = new File(filepath+path);
                    if(deleteFile.exists()){
                        deleteFile.delete();
                    }
                }
            }
        }else{
            if(file.isFile()){
                file.delete();
            }
        }

    }
}
