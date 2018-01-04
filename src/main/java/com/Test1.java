package com;

import model.DriverInstance;
import utils.AndroidDriver;

import java.net.MalformedURLException;

public class Test1 {
    public static void main(String[] args) throws MalformedURLException {
        AndroidDriver driver1= DriverInstance.getInstance().getAndroidDriver();
        AndroidDriver driver2= DriverInstance.getInstance().getAndroidDriver();

        if (driver1==driver2){
            System.out.println("yes");
        }
        else {
            System.out.println("NO");
        }
    }
}
