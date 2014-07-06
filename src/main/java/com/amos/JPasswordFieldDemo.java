package com.amos;

/**
 * Created by amosli on 14-6-30.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;


public class JPasswordFieldDemo {

    public static void main(String[] argv) {
        String inputText = JOptionPane.showInputDialog("请输入的短信验证码:");
        System.out.println(inputText);
        String inputText２ = JOptionPane.showInputDialog("请再次输入的短信验证码:");
        System.out.println(inputText２);
        JOptionPane.showInputDialog("yes","justtest");


    }

}