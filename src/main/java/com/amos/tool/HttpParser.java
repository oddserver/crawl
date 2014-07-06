package com.amos.tool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by amosli on 14-7-6.
 */
public class HttpParser {



    public String getValueFromInputByName(String html,String name){

        Document doc = Jsoup.parse(html);

        String inputValue = doc.select("input[name=" + name + "]").first().attr("value").trim();

        return inputValue;
    }

    public  static void main(String args[]){

        String gvfdcname = new HttpParser().getValueFromInputByName("<input type=\"hidden\" id=\"gvfdc\" name=\"gvfdcname\" value=\"10\">", "gvfdcname");

        System.out.println(gvfdcname);

    }
}
