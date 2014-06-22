package com.amos;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    public static void main(String args[]) throws  Exception{
        InputStream inputStream = System.in;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        //输入验证码
        String capthca = bufferedReader.toString();
        System.out.println(bufferedReader.readLine());
        System.out.println(capthca);


        Scanner scanner = new Scanner(System.in);
        capthca = scanner.next();
        System.out.println(capthca);


    }



}