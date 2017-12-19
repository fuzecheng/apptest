package com;

import model.FilePath;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestNGCase {
    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().exec("adb start-server");
        FileUtils.deleteFileByFormat(FilePath.TEST_OUTPUT);
        TestListenerAdapter tla = new TestListenerAdapter();
        List<String> testFilesList = new ArrayList<String>();
        TestNG testng=new TestNG();
        testFilesList.add(FilePath.TESTNG_FILE_NAME);
        testng.setTestSuites(testFilesList);
        testng.setUseDefaultListeners(false);
        testng.addListener(tla);
        testng.run();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
