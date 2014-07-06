package com.amos;


import com.amos.tool.HttpParser;
import com.amos.tool.Tools;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amosli on 14-6-23.
 */
public class LoginTaobao {
    private static HttpParser httpParser = new HttpParser();

    public static void main(String args[]) throws Exception {


        long StartTime=System.currentTimeMillis();
        System.out.println("StartTime:"+StartTime);

        HttpClient httpClient = new DefaultHttpClient();

        String loginUrl = "https://login.taobao.com/member/login.jhtml";

        HttpGet httpGet = new HttpGet(loginUrl);

//        for (Header header : taobaoLoginResponse.getAllHeaders()) {
//            System.out.println("header:" + header);
//        }

        //get cookies
//        AbstractHttpClient abstractHttpClient = (AbstractHttpClient) httpClient;
//        CookieStore cookieStore = abstractHttpClient.getCookieStore();
//        for (Cookie cookie : cookieStore.getCookies()) {
//            System.out.println("cookie:" + cookie);
//        }

        String loginHTML = EntityUtils.toString(httpClient.execute(httpGet).getEntity());
        System.out.println("loginHTML:" + loginHTML);

        String TPL_username = "淘宝账号";//现在联系我吧
        String TPL_password = "淘宝密码";

//        TPL_username=URLEncoder.encode(TPL_username,"UTF-8");

        String loginType = httpParser.getValueFromInputByName(loginHTML, "loginType");
        String TPL_checkcode = httpParser.getValueFromInputByName(loginHTML, "TPL_checkcode");
        String loginsite = httpParser.getValueFromInputByName(loginHTML, "loginsite");
        String newlogin = httpParser.getValueFromInputByName(loginHTML, "newlogin");
        String TPL_redirect_url = httpParser.getValueFromInputByName(loginHTML, "TPL_redirect_url");
        String from = httpParser.getValueFromInputByName(loginHTML, "from");
        String fc = httpParser.getValueFromInputByName(loginHTML, "fc");
        String style = httpParser.getValueFromInputByName(loginHTML, "style");
        String css_style = httpParser.getValueFromInputByName(loginHTML, "css_style");
        String tid = httpParser.getValueFromInputByName(loginHTML, "tid");
        String support = httpParser.getValueFromInputByName(loginHTML, "support");
        String CtrlVersion = httpParser.getValueFromInputByName(loginHTML, "CtrlVersion");
        String minititle = httpParser.getValueFromInputByName(loginHTML, "minititle");
        String minipara = httpParser.getValueFromInputByName(loginHTML, "minipara");
        String umto = httpParser.getValueFromInputByName(loginHTML, "umto");
        String pstrong = httpParser.getValueFromInputByName(loginHTML, "pstrong");
        String llnick = httpParser.getValueFromInputByName(loginHTML, "llnick");
        String sign = httpParser.getValueFromInputByName(loginHTML, "sign");
        String need_sign = httpParser.getValueFromInputByName(loginHTML, "need_sign");
        String isIgnore = httpParser.getValueFromInputByName(loginHTML, "isIgnore");
        String full_redirect = httpParser.getValueFromInputByName(loginHTML, "full_redirect");
        String popid = httpParser.getValueFromInputByName(loginHTML, "popid");
        String callback = httpParser.getValueFromInputByName(loginHTML, "callback");
        String guf = httpParser.getValueFromInputByName(loginHTML, "guf");
        String not_duplite_str = httpParser.getValueFromInputByName(loginHTML, "not_duplite_str");
        String need_user_id = httpParser.getValueFromInputByName(loginHTML, "need_user_id");
        String poy = httpParser.getValueFromInputByName(loginHTML, "poy");
        String gvfdcname = httpParser.getValueFromInputByName(loginHTML, "gvfdcname");
        String gvfdcre = httpParser.getValueFromInputByName(loginHTML, "gvfdcre");
        String from_encoding = httpParser.getValueFromInputByName(loginHTML, "from_encoding");
        String sub = httpParser.getValueFromInputByName(loginHTML, "sub");
        String allp = httpParser.getValueFromInputByName(loginHTML, "allp");
        String osVer = httpParser.getValueFromInputByName(loginHTML, "osVer");

        List<NameValuePair> loginParameters = new ArrayList<NameValuePair>();
        loginParameters.add(new BasicNameValuePair("loginsite", loginsite));
        loginParameters.add(new BasicNameValuePair("TPL_username", TPL_username));
        loginParameters.add(new BasicNameValuePair("TPL_password", TPL_password));
        loginParameters.add(new BasicNameValuePair("TPL_checkcode", TPL_checkcode));
        loginParameters.add(new BasicNameValuePair("loginsite", loginsite));
        loginParameters.add(new BasicNameValuePair("newlogin", newlogin));
        loginParameters.add(new BasicNameValuePair("TPL_redirect_url", TPL_redirect_url));
        loginParameters.add(new BasicNameValuePair("from", from));
        loginParameters.add(new BasicNameValuePair("fc", fc));
        loginParameters.add(new BasicNameValuePair("style", style));
        loginParameters.add(new BasicNameValuePair("css_style", css_style));
        loginParameters.add(new BasicNameValuePair("tid", tid));
        loginParameters.add(new BasicNameValuePair("support", support));
        loginParameters.add(new BasicNameValuePair("CtrlVersion", CtrlVersion));
        loginParameters.add(new BasicNameValuePair("loginType", loginType));
        loginParameters.add(new BasicNameValuePair("minititle", minititle));
        loginParameters.add(new BasicNameValuePair("minipara", minipara));
        loginParameters.add(new BasicNameValuePair("umto", umto));
        loginParameters.add(new BasicNameValuePair("pstrong", pstrong));
        loginParameters.add(new BasicNameValuePair("llnick", llnick));
        loginParameters.add(new BasicNameValuePair("sign", sign));
        loginParameters.add(new BasicNameValuePair("need_sign", need_sign));
        loginParameters.add(new BasicNameValuePair("isIgnore", isIgnore));
        loginParameters.add(new BasicNameValuePair("full_redirect", full_redirect));
        loginParameters.add(new BasicNameValuePair("popid", popid));
        loginParameters.add(new BasicNameValuePair("callback", callback));
        loginParameters.add(new BasicNameValuePair("guf", guf));
        loginParameters.add(new BasicNameValuePair("not_duplite_str", not_duplite_str));
        loginParameters.add(new BasicNameValuePair("need_user_id", need_user_id));
        loginParameters.add(new BasicNameValuePair("poy", poy));
        loginParameters.add(new BasicNameValuePair("gvfdcname", gvfdcname));
        loginParameters.add(new BasicNameValuePair("gvfdcre", gvfdcre));
        loginParameters.add(new BasicNameValuePair("from_encoding", from_encoding));
        loginParameters.add(new BasicNameValuePair("sub", sub));
        loginParameters.add(new BasicNameValuePair("allp", allp));
        loginParameters.add(new BasicNameValuePair("oslanguage", "en-US"));
        loginParameters.add(new BasicNameValuePair("sr", "1280*800"));
        loginParameters.add(new BasicNameValuePair("osVer", osVer));
        loginParameters.add(new BasicNameValuePair("naviVer", "firefox|30"));


        String newloginUrl = loginUrl+"?ua=&TPL_username="+TPL_username+"&TPL_password="+TPL_password+"&TPL_checkcode=&loginsite=0&newlogin=1&TPL_redirect_url=&from=tb&fc=default&style=default&css_style=&tid=&support=000001&CtrlVersion=1%2C0%2C0%2C7&loginType=3&minititle=&minipara=&umto="+umto+"&pstrong=1&llnick=&sign=&need_sign=&isIgnore=&full_redirect=&popid=&callback=1&guf=&not_duplite_str=&need_user_id=&poy=&gvfdcname=10&gvfdcre=&from_encoding=&sub=&allp=&oslanguage=en-US&sr=1280*800&osVer=&naviVer=chrome%7C30.01916153";

        HttpPost newloginHttpPost = new HttpPost(newloginUrl);
        HttpResponse newhttpResponse = httpClient.execute(newloginHttpPost);
        String newLoginResult=EntityUtils.toString(newhttpResponse.getEntity());
        System.out.println("newLoginResult:" + newLoginResult);

        //{"state":false,"message":"为了您的账户安全，请输入验证码。","data":{"code":3425,"url":"","needrefresh":false,"ccurl":"https://pin.aliyun.com/get_img?sessionid=d7b1a068e452a8518add90ff47b8c15a&identity=taobao.login&type=150_40"}}
//        src="http://regcheckcode.taobao.com/auction/checkcode?sessionID=ALI95011dd8717b665aea3ef0e0ae4f0c18&umidToken=T5feec633b7c2b65b5de1dff0c35af4ac&nick=中国小哥李"

        if(newLoginResult.contains("输入验证码")){
             JsonParser jsonParser = new JsonParser();
           JsonElement jsonElement = jsonParser.parse(newLoginResult);
           String ccurl = jsonElement.getAsJsonObject().get("ccurl").getAsString();
           System.out.println("ccurl:"+ccurl);
            HttpGet ccurlGEt = new HttpGet(ccurl);
            Tools.saveToLocal(httpClient.execute(ccurlGEt).getEntity(),"taobao.captcha."+System.currentTimeMillis());
            String captcha = JOptionPane.showInputDialog("请输入验证码:");
            System.out.println("captcha:" + captcha);
        }


        //拼接登录post
        String loginResult = "";
//        do{
        HttpPost loginHttpPost = new HttpPost(loginUrl);

        HttpEntity loginHttpEntity = new UrlEncodedFormEntity(loginParameters);
        loginHttpPost.setEntity(loginHttpEntity);

        System.out.println("loginHttpPost.getURI():" + loginHttpPost.getURI());

        loginResult = EntityUtils.toString(httpClient.execute(loginHttpPost).getEntity());

        System.out.println("loginResult:" + loginResult);
        //{"state":false,"message":"为了您的账户安全，请输入验证码。","data":{"code":3425,"url":"","needrefresh":false,"ccurl":"https://pin.aliyun.com/get_img?sessionid=d7b1a068e452a8518add90ff47b8c15a&identity=taobao.login&type=150_40"}}
        // {"state":false,"message":"该账户名不存在","data":{"code":5,"url":"","needrefresh":false}}
        // {"state":false,"message":"您输入的密码和账户名不匹配，请重新输入。","data":{"code":3501,"url":"","needrefresh":false}}

//           JsonParser jsonParser = new JsonParser();
//           JsonElement jsonElement = jsonParser.parse(loginResult);
//           String state = jsonElement.getAsJsonObject().get("state").getAsString();
//           System.out.println("state:"+state);
//       }while(!loginResult.contains("true"));


        //我的淘宝
        String basicURL = "http://i.taobao.com/my_taobao.htm";
        HttpGet basicGET = new HttpGet(basicURL);

        Tools.saveToLocal(httpClient.execute(basicGET).getEntity(), "taobao.basic.html");


        //安全设置页面
        String securityURL = "http://member1.taobao.com/member/fresh/account_security.htm";
        HttpGet securityGET = new HttpGet(securityURL);

        Tools.saveToLocal(httpClient.execute(securityGET).getEntity(), "taobao.security.html");


        //收货地址页面
        String addressURL = "http://member1.taobao.com/member/fresh/deliver_address.htm";
        HttpGet addressGet = new HttpGet(addressURL);


        Tools.saveToLocal(httpClient.execute(addressGet).getEntity(), "taobao.address.html");


        //收货地址页面
        String itemURL = "http://trade.taobao.com/trade/itemlist/list_bought_items.htm";
        HttpGet itemGet = new HttpGet(itemURL);

        int i = 1;
        Tools.saveToLocal(httpClient.execute(itemGet).getEntity(), "taobao.item." + i + ".html");

        //订单详情
        List<String> itemDetailList = new ArrayList<String>();

        String itemResult = EntityUtils.toString(httpClient.execute(itemGet).getEntity());
        Document itemdoc = Jsoup.parse(itemResult);
        String totalPage = Tools.GetNumber(itemdoc.select("div.total").first().text().trim());

        for (Element element : itemdoc.select("a:containsOwn(订单详情)")) {
            String detailUrl = element.attr("href");
            itemDetailList.add(detailUrl);
        }


        if (totalPage != null && totalPage != "" && Integer.parseInt(totalPage) > 1) {
            String itemNextUrl = itemdoc.getElementsByClass("num").get(1).attr("href");
            itemNextUrl = itemNextUrl.lastIndexOf("=") != -1 ? itemNextUrl.substring(0, itemNextUrl.lastIndexOf("=")) : null;
            while (i < Integer.parseInt(totalPage)) {
                i++;
                String NextUrl = itemNextUrl + "=" + i;

                System.out.println("NextUrl:"+NextUrl);
                itemGet = new HttpGet(NextUrl);
                Tools.saveToLocal(httpClient.execute(itemGet).getEntity(), "taobao.item." + i + ".html");

                itemdoc = Jsoup.parse(EntityUtils.toString(httpClient.execute(itemGet).getEntity()));

                for (Element element : itemdoc.select("a:containsOwn(订单详情)")) {
                    String detailUrl = element.attr("href");
                    itemDetailList.add(detailUrl);
                }

            }

        }

        //download detail pages
        int listSize=itemDetailList.size();

        for(int kk=0;kk<listSize;kk++){
            System.out.println("url."+kk+":"+itemDetailList.get(kk));
            HttpGet detailGet = new HttpGet(itemDetailList.get(kk));
            Tools.saveToLocal(httpClient.execute(detailGet).getEntity(), "taobao.detail." + kk + ".html");
        }

        long endTime=System.currentTimeMillis();
        System.out.println("EndTime:"+endTime);
        System.out.print("sub:"+(endTime-StartTime));

    }

}


