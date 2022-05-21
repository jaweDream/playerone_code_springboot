package com.jawe.test.generator;

import org.junit.jupiter.api.Test;


public class demo {

    public static void main(String[] args) {

//        System.out.println('1'-'0');
        System.out.println((300&0b1000)/0b1000==1);
        String str = "hello";
        int n = str.codePointCount(0,str.length());
        System.out.println(n);
        System.out.println(str.length());

        str = "\uD835\uDD46";
        System.out.println("\uD835\uDD46");
        System.out.println(str.charAt(1));
        System.out.println(Integer.toHexString(str.codePointAt(0)));
        System.out.println(str.offsetByCodePoints(0, 1));
    }
}
