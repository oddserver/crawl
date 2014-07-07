package com.amos;


import com.amos.tool.Tools;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by amosli on 14-6-22.
 */
public class LoginChinaUnicomWithCaptcha {

    public static void main(String args[]) throws Exception {

        String name =  "13167081006";
        String pwd = "007745";

        //生成验证码的链接
        String createCaptchaUrl = "http://uac.10010.com/portal/Service/CreateImage";
        HttpClient httpClient = new DefaultHttpClient();

        //这里可自定义所需要的cookie
        CookieStore cookieStore = new BasicCookieStore();

        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();

        //get captcha,获取验证码
        HttpGet captchaHttpGet = new HttpGet(createCaptchaUrl);
        HttpResponse capthcaResponse = httpClient.execute(captchaHttpGet);

        if (capthcaResponse.getStatusLine().getStatusCode() == 200) {
            //将验证码写入本地
            Tools.saveToLocal(capthcaResponse.getEntity(), "chinaunicom.capthca." + System.currentTimeMillis()+".png");
        }


        //手工输入验证码并验证
        HttpResponse verifyResponse = null;
        String capthca = null;
        String uvc = null;

        do {
            //输入验证码,读入键盘输入
            //1)
//            InputStream inputStream = System.in;
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            System.out.println("请输入验证码:");
//            capthca = bufferedReader.readLine();

            capthca=JOptionPane.showInputDialog("请输入图片验证码:");

            //2)
            //Scanner scanner = new Scanner(System.in);
            //capthca = scanner.next();
           // http://uac.10010.com/portal/Service/CtaIdyChk?callback=jsonp1404716227598&verifyCode=4m3e&verifyType=1
            String verifyCaptchaUrl = "http://uac.10010.com/portal/Service/CtaIdyChk?verifyCode=" + capthca + "&verifyType=1";
            HttpGet verifyCapthcaGet = new HttpGet(verifyCaptchaUrl);
            verifyResponse = httpClient.execute(verifyCapthcaGet);
            AbstractHttpClient abstractHttpClient = (AbstractHttpClient) httpClient;
            for (Cookie cookie : abstractHttpClient.getCookieStore().getCookies()) {
                System.out.println(cookie.getName() + ":" + cookie.getValue());
                if (cookie.getName().equals("uacverifykey")) {
                    uvc = cookie.getValue();
                }
            }
        } while (!EntityUtils.toString(verifyResponse.getEntity()).contains("true"));

//        https://uac.10010.com/portal/Service/MallLogin?callback=jsonp1404716227600&userName=13167081002&password=001122&pwdType=01&productType=01&verifyCode=xhx3&redirectType=03&areaCode=&arrcity=%E5%9C%B0%E5%8C%BA&uvc=3zm50fcae32b5fa8cb5795d5b335cd85d2cv4c


        //登录
        String loginurl = "https://uac.10010.com/portal/Service/MallLogin?userName=" + name + "&password=" + pwd + "&pwdType=01&productType=01&verifyCode=" + capthca + "&redirectType=03&uvc=" + uvc;
        HttpGet loginGet = new HttpGet(loginurl);
        CloseableHttpResponse loginResponse = httpclient.execute(loginGet);
        System.out.print("loginResponse:" + EntityUtils.toString(loginResponse.getEntity()));

        //抓取基本信息数据
        //jsonp1404663560635({resultCode:"7072",redirectURL:"http://www.10010.com",errDesc:"null",msg:'系统忙，请稍后再试。',needvode:"1"});
        HttpPost basicHttpGet = new HttpPost("http://iservice.10010.com/ehallService/static/acctBalance/execute/YH102010005/QUERY_AcctBalance.processData/Result");
        Tools.saveToLocal(httpclient.execute(basicHttpGet).getEntity(), "chinaunicom.basic.html");

    }

}
