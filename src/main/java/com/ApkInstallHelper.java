package com;

import model.FilePath;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApkInstallHelper {


    public static void main(String[] args) throws IOException {
        String cmd="adb install -r "+ FilePath.APP_PATH+ File.separator+FilePath.APP_NAME;
        System.out.println(cmd);
        Process process=Runtime.getRuntime().exec(cmd);
        BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line="";
        while ((line=reader.readLine())!=null){
            System.out.println(line);
        }

    }


}
