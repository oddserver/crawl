package com.amos;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by amosli on 14-7-8.
 */
public class LoginSCMobile {


    public static void main(String args[]) throws Exception {

        String s="asdfdfdfdsfafdgsdfasf123435";
        char[] cs=s.toCharArray();
        for(int i:cs)System.out.println(i);

//        HttpClient httpClient = Tools.createSSLClientDefault();

//        String loginURL = "https://sc.ac.10086.cn/login/";//sc
//        String loginURL = "https://sh.ac.10086.cn/login";//sh

        HttpClient httpClient = new DefaultHttpClient();
        String loginURL = "https://login.taobao.com/member/login.jhtml";

        HttpGet httpGet = new HttpGet(loginURL);
        HttpResponse loginResponse = httpClient.execute(httpGet);
        String loginString = EntityUtils.toString(loginResponse.getEntity());
        System.out.println("loginString:\n"+loginString);
        Matcher matcher = Pattern.compile("var UA_Opt =(.*?)</script>").matcher(loginString.replaceAll("\\r|\\t|\\n|\\a",""));

        while(matcher.find()){
            System.out.println(matcher.group(1));
        }
        httpGet.releaseConnection();

//        Matcher matcher = Pattern.compile("var keyStr =(.*?)\\}").matcher(loginString.replaceAll("\\r|\\t|\\n|\\a",""));

//        使用java截取js方法,首先,将换行符制表符回车符报警符都替换掉,这样在截取时就不会出问题了
//        Matcher matcher = Pattern.compile("initdata=\\{(.*?)\\}").matcher(loginString.replaceAll("\\r|\\t|\\n|\\a",""));





//        String strLine="<a id=\"utilAllProd\" class=\"utilAllProdAct\"  href=\"javascript:;\"><span class=\"utilNumbProd\">(500+)</span></a>\r\n"
//                +"\r\n        <a id=\"utilSearsProd\" class=\"cursorPointer\" onclick=\"sellerTabsOmniture('View Sears Only Products');\" href=\"/search=digital camera&Sears?filter=storeOrigin&keywordSearch=false&vName=Cameras+%26+Camcorders&catalogId=12605&previousSort=ORIGINAL_SORT_ORDER&viewItems=50&storeId=10153&cName=Digital+Cameras\"><span class=\"utilNumbProd\">(286)</span></a>";
//
////        <span class="utilNumbProd">(500+)</span>
//
//        Pattern pa = Pattern.compile("<span class=\"utilNumbProd\">\\((\\d+[\\+]*)\\)</span>");
//        Matcher ma = pa.matcher(strLine);
//        while (ma.find()) {
//            String text=ma.group(1);
//            System.out.println(text);
//        }





    }



}
