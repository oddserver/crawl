package com.amos;

import java.text.MessageFormat;

/**
 * Created by amosli on 14-7-24.
 */
public class Test {

    public static void main(String args[]) {

        //拼接一段string 常用的方法
        String name="amosli";
        //方法1:
        String result = "hello,"+name;
        System.out.println(result);//hello,amosli

        //方法2:
        result = "hello,".concat(name);
        System.out.println(result);//hello,amosli


        //方法3:
        result = String.format("%d%s", 200, "元");
        System.out.println(result);//200元

        //方法4:
        result = MessageFormat.format("hi,{0},I am {1}", "Jack", "Amosli");
        System.out.println(result);//hi,Jack,I am Amosli


    }

}
