package com.amos;

import com.amos.tool.Tools;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author amosli
 *         登录并抓取中国联通数据
 */

public class LoginChinaUnicom {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String name =  "中国联通";
        String pwd = "手机号码";

        //https://uac.10010.com/portal/Service/MallLogin?callback=jQuery17208151653951499611_1404661522215&redirectURL=http%3A%2F%2Fwww.10010.com&userName=13167081006&password=0077450&pwdType=01&productType=01&redirectType=01&rememberMe=1&_=1404661572740
        String url = "https://uac.10010.com/portal/Service/MallLogin?callback=jQuery17202691898950318097_1403425938090&redirectURL=http%3A%2F%2Fwww.10010.com&userName=" + name + "&password=" + pwd + "&pwdType=01&productType=01&redirectType=01&rememberMe=1";

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse loginResponse = httpClient.execute(httpGet);

        if (loginResponse.getStatusLine().getStatusCode() == 200) {
            for (Header head : loginResponse.getAllHeaders()) {
                System.out.println(head);
            }
            HttpEntity loginEntity = loginResponse.getEntity();
            String loginEntityContent = EntityUtils.toString(loginEntity);
            System.out.println("登录状态:" + loginEntityContent);

            //jQuery17208151653951499611_1404661522215({resultCode:"7007",redirectURL:"http://www.10010.com",errDesc:"null",msg:'用户名或密码不正确。<a href="https://uac.10010.com/cust/resetpwd/inputName" target="_blank" style="color: #36c;cursor: pointer;text-decoration:underline;">忘记密码？</a>',needvode:"1"});
            //如果登录成功
            if (loginEntityContent.contains("resultCode:\"0000\"")) {

                //月份
                String months[] = new String[]{"201401", "201402", "201403", "201404", "201405"};

                for (String month : months) {
                    //http://iservice.10010.com/ehallService/static/historyBiil/execute/YH102010002/QUERY_YH102010002.processData/QueryYH102010002_Data/201405/undefined?_=1404661790076&menuid=000100020001
                    String billurl = "http://iservice.10010.com/ehallService/static/historyBiil/execute/YH102010002/QUERY_YH102010002.processData/QueryYH102010002_Data/" + month + "/undefined";

                    HttpPost httpPost = new HttpPost(billurl);
                    HttpResponse billresponse = httpClient.execute(httpPost);
                    if (billresponse.getStatusLine().getStatusCode() == 200) {
                        Tools.saveToLocal(billresponse.getEntity(), "chinaunicom.bill." + month + ".2.html");
                    }
                }

            }
        }

    }





}
