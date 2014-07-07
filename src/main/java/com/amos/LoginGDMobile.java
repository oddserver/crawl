package com.amos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.amos.tool.Tools;

/**
 * Created by amosli on 14-6-25.
 */
public class LoginGDMobile {

    public static void main(String args[]) throws Exception {

        String phone="广东移动账号";
        String password="广东移动密码";

        org.apache.http.client.CookieStore cookieStore = new BasicCookieStore();

        BasicClientCookie cookie = new BasicClientCookie("_mobile",phone);
        cookie.setDomain("gd.ac.10086.cn");
        cookie.setPath("/ucs/login");
        cookieStore.addCookie(cookie);

        BasicClientCookie cookie_loginType = new BasicClientCookie("__loginType", "2");
        cookie_loginType.setDomain("gd.ac.10086.cn");
        cookie_loginType.setPath("/ucs/login");
        cookieStore.addCookie(cookie_loginType);

        HttpClient httpClient = Tools.createSSLClientDefault();



        //"_mobile",phone
        //"__loginType","2"


        //1.java中执行js方法,返回加密后的密码
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine=scriptEngineManager.getEngineByExtension("js");

        FileReader reader = new FileReader("/home/amosli/workspace/RSA.js");
        scriptEngine.eval(reader);
        Invocable invocable = (Invocable) scriptEngine;

        //095f8227a64a1cc7792ad26e0a923342871e6bdd640b202a4b380b26d337e5d1a07040e30e84f7a54e603a906b4f7728abc76bbea580763a5d508cb28a65d100
        Object encrypt_password = invocable.invokeFunction("encrypt_password", password);

        //2.获取登录时的图片验证码
        String imagCaptchaUrl="https://gd.ac.10086.cn/ucs/captcha/image/reade.jsps";
        HttpGet httpGet= new HttpGet(imagCaptchaUrl);
        HttpResponse imagCaptchaResponse = httpClient.execute(httpGet);
        Tools.saveToLocal(imagCaptchaResponse.getEntity(),"GDMOBile.imagCaptcha."+System.currentTimeMillis()+".png");
        System.out.println("请输入图片验证码:");
        String imagCaptcha = new  BufferedReader(new InputStreamReader(System.in)).readLine();

        //3.登录网站
        String loginUrl = "https://gd.ac.10086.cn/ucs/login/register.jsps";
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("gd.ac.10086.cn")
                .setPath("/ucs/captcha/image/reade.jsps")
                .addParameter("loginType", "2")
                .setParameter("mobile", phone)
                .setParameter("password", encrypt_password.toString())
                .setParameter("imagCaptcha", imagCaptcha)
                .setParameter("cookieMobile", "on")
                .setParameter("bizagreeable", "true")
                .setParameter("exp", null)
                .setParameter("cid", null)
                .setParameter("area", "ROOT")
                .setParameter("resource", null)
                .setParameter("channel", "0")
                .setParameter("reqType", "0")
                .setParameter("backURL", null)
                .build();

        System.out.println(EntityUtils.toString(httpClient.execute(new HttpPost(uri)).getEntity()));


        List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
        loginParams.add(new BasicNameValuePair("loginType", "2"));
        loginParams.add(new BasicNameValuePair("mobile",phone));
        loginParams.add(new BasicNameValuePair("password",encrypt_password.toString()));
        loginParams.add(new BasicNameValuePair("imagCaptcha",imagCaptcha));
        loginParams.add(new BasicNameValuePair("cookieMobile","on"));
        loginParams.add(new BasicNameValuePair("channel","0"));
        loginParams.add(new BasicNameValuePair("reqType","0"));
        loginParams.add(new BasicNameValuePair("exp",null));
        loginParams.add(new BasicNameValuePair("cid",null));
        loginParams.add(new BasicNameValuePair("resource",null));
        loginParams.add(new BasicNameValuePair("cid",null));
        loginParams.add(new BasicNameValuePair("backURL",phone));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(loginParams, Consts.UTF_8);

        HttpPost httppost = new HttpPost(loginUrl);
        httppost.setEntity(entity);

        System.out.println(EntityUtils.toString(httpClient.execute(httppost).getEntity()));

        HttpPost directPost = new HttpPost("https://gd.ac.10086.cn/ucs/login/register.jsps?loginType=2&mobile="+phone+"&password="+encrypt_password+"&imagCaptcha="+imagCaptcha+"&cookieMobile=on&bizagreeable=true&exp=&cid=&area=ROOT&resource=&channel=0&reqType=0&backURL=");

        System.out.println(EntityUtils.toString(httpClient.execute(directPost).getEntity()));






//        loginType:2
//        mobile:13560178078
//        password:095f8227a64a1cc7792ad26e0a923342871e6bdd640b202a4b380b26d337e5d1a07040e30e84f7a54e603a906b4f7728abc76bbea580763a5d508cb28a65d100
//        imagCaptcha:s45d
//        cookieMobile:on
//        bizagreeable:true
//        exp:
//        cid:
//        area:ROOT
//        resource:
//        channel:0
//        reqType:0
//        backURL:



    /*    HttpPost loginPost = new HttpPost();
        HttpEntity entity = new BasicHttpEntity();

        loginPost.setEntity();
        loginPost.*/

    }


}
