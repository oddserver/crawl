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
//        InputStream inputStream = System.in;
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        //输入验证码
//        String capthca = bufferedReader.toString();
//        System.out.println(bufferedReader.readLine());
//        System.out.println(capthca);
//
////        02105007710
////        02105007710
////        Hello123World
////        892479
//
//        Scanner scanner = new Scanner(System.in);
//        capthca = scanner.next();
//        System.out.println(capthca);


        //http://trade.taobao.com/trade/itemlist/listBoughtItems.htm?spm=a1z09.2.9.304.7hO4v2&action=itemlist/QueryAction&event_submit_do_query=1&prePageNo=1&clickMore=0&pageNum=2
        String itemNextUrl = "http://trade.taobao.com/trade/itemlist/listBoughtItems.htm?spm=a1z09.2.9.304.7hO4v2&action=itemlist/QueryAction&event_submit_do_query=1&prePageNo=1&clickMore=0&pageNum=2";
        itemNextUrl=itemNextUrl.lastIndexOf("=")!=-1?itemNextUrl.substring(0,itemNextUrl.lastIndexOf("=")):null;

        System.out.println(itemNextUrl);


    }



}