package com;

import java.util.ArrayList;
import java.util.List;

public class TestCoachCase {

    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        list.add("123");
        list.add("456");
        if (list.contains("45")){
            System.out.println("yes");
        }

        System.out.println(list.toString());
    }
}
