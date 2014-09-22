package com.amos;

import com.amos.tool.Tools;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by amosli on 14-9-17.
 */
public class LoginShanDongTelecom {


    public static void main(String args[]) throws Exception {

        CloseableHttpClient httpClient = Tools.createSSLClientDefault();
        String url = "https://uam.ct10000.com/ct10000uam/login?service=http://www.189.cn/dqmh/Uam.do?method=loginJTUamGet&returnURL=1&register=register2.0&UserIp=139.226.36.139,%2058.68.232.166,%20127.0.0.1";
        url="http://www.189.cn/dqmh/Uam.do?method=loginUamSendJT&logintype=telephone&shopId=null&loginRequestURLMark=http://www.189.cn/dqmh/login/loginJT.jsp?UserUrlto=/dqmh/frontLink.do?method=linkTo&shopId=10016&toStUrl=http://sd.189.cn/selfservice/account/returnwt?columnId=00&date=";

        String result = "";
        String phone = "15318719560";
        String password = "007745";

        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        result = EntityUtils.toString(httpResponse.getEntity());

        String lt = "";
        Matcher matcher = Pattern.compile("name=\"lt\" value=\"(.*?)\"").matcher(result);
        if (matcher.find()) {
            lt = matcher.group(1);
        }

        url = "https://uam.ct10000.com/ct10000uam/FindPhoneAreaServlet";
        HttpPost httpPost = new HttpPost(url);
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("username", phone));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        result = EntityUtils.toString(httpClient.execute(httpPost).getEntity());

        url = "https://uam.ct10000.com/ct10000uam/validateImg.jsp";
        httpGet = new HttpGet(url);
        Tools.saveToLocal(httpClient.execute(httpGet).getEntity(), "sdtelecom.png");
        System.out.println("请输入图片验证码:");
        String imagCaptcha = new BufferedReader(new InputStreamReader(System.in)).readLine();

        url = "https://uam.ct10000.com/ct10000uam/login?service=http://www.189.cn/dqmh/Uam.do?method=loginJTUamGet&returnURL=1&register=register2.0&UserIp=139.226.36.139,%2058.68.232.166,%20127.0.0.1";
        params.clear();
        params.add(new BasicNameValuePair("forbidpass", null));
        params.add(new BasicNameValuePair("forbidaccounts", null));
        params.add(new BasicNameValuePair("authtype", "c2000004"));
        params.add(new BasicNameValuePair("customFileld02", "16"));
        params.add(new BasicNameValuePair("areaname", "山东"));
        params.add(new BasicNameValuePair("username", phone));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("randomId", imagCaptcha));
        params.add(new BasicNameValuePair("lt", lt));
        params.add(new BasicNameValuePair("_eventId", "submit"));
        params.add(new BasicNameValuePair("open_no", "c2000004"));
        httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        result = EntityUtils.toString(httpClient.execute(httpPost).getEntity());

        System.out.println("result:" + result);

        RequestConfig requestConfig =  RequestConfig.custom().setRedirectsEnabled(false).build();
        httpPost.setConfig(requestConfig);






//        https://uam.ct10000.com/ct10000uam/login?service=http://www.189.cn/dqmh/Uam.do?method=loginJTUamGet&returnURL=1&register=register2.0&UserIp=139.226.36.139,%2058.68.232.166,%20127.0.0.1
//        service:http://www.189.cn/dqmh/Uam.do?method
//        returnURL:1
//        register:register2.0
//        UserIp:139.226.36.139, 58.68.232.166, 127.0.0.1

//        forbidpass:null
//        forbidaccounts:null
//        authtype:c2000004
//        customFileld02:16
//        areaname:山东
//        username:15318719560
//        customFileld01:3
//        password:007745
//        randomId:7640
//        lt:_c26A96E09-588F-DA80-C87D-4CCD0306D28E_k750C0EDD-C3FB-8747-C52C-5E39681EBB81
//        _eventId:submit
//        open_no:c2000004


    }

}
