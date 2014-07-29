package com.amos;

import com.amos.tool.Tools;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String name = "中国联通";
        String pwd = "密码";

        String url = "https://uac.10010.com/portal/Service/MallLogin?callback=jQuery17202691898950318097_1403425938090&redirectURL=http%3A%2F%2Fwww.10010.com&userName=" + name + "&password=" + pwd + "&pwdType=01&productType=01&redirectType=01&rememberMe=1";

        HttpClient httpClient = Tools.createSSLClientDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse loginResponse = httpClient.execute(httpGet);

        for (Header head : loginResponse.getAllHeaders()) {
            System.out.println(head);
        }
        HttpEntity loginEntity = loginResponse.getEntity();
        String loginEntityContent = EntityUtils.toString(loginEntity);
        System.out.println("登录状态:" + loginEntityContent);

        //如果登录成功
        if (loginEntityContent.contains("resultCode:\"0000\"")) {

            //验证是否登录成功
            url = "https://upay.10010.com/npfweb/NpfBuycardWeb/judgeLogin/isLogin.action?redirectKey=BuyCardLoginReturnUrl&time=&callback=isLogin";
            String result = EntityUtils.toString(httpClient.execute(new HttpGet(url)).getEntity());
            if (!result.contains("isLogin\":true")) {
                return;
            }
            //发送短信验证码
            url = "https://upay.10010.com/npfweb/NpfBuycardWeb/buyCard/sendPhoneVerifyCode?callback=sendSuccess&commonBean.phoneNo=13167081006&timeStamp=";
            result = EntityUtils.toString(httpClient.execute(new HttpGet(url)).getEntity());
            if (!result.contains("sendSuccess('true')")) {
                return;
            }
            String smsCode = "";
            do {
                smsCode = JOptionPane.showInputDialog("请输入短信验证码!");
                url = "https://upay.10010.com/npfweb/NpfBuycardWeb/buyCard/checkPhoneVerifyCode?callback=checkSuccess&commonBean.phoneNo=%s&phoneVerifyCode=%s&timeStamp=";
                url = String.format(url, name, smsCode);
                result = EntityUtils.toString(httpClient.execute(new HttpGet(url)).getEntity());
            } while (!result.contains("true"));//手机验证码已失效

            url = "https://upay.10010.com/npfweb/NpfCellWeb/needCode?pageType=03";
            result = EntityUtils.toString(httpClient.execute(new HttpGet(url)).getEntity());

            //查询卡密
            url = "https://upay.10010.com/npfweb/npfcellweb/phone_recharge_fill.htm?orignalresource=2";
            result = EntityUtils.toString(httpClient.execute(new HttpGet(url)).getEntity(), Consts.UTF_8);

            Matcher m = Pattern.compile("name=\"secstate.state\" type=\"hidden\" value=\"(.*?)\"").matcher(result);
            m.find();
            String secstate = m.group(1);

            String cardPwd = "778 8993 3221 1000 8888".replaceAll("\\D+", "");
            url = "https://upay.10010.com/npfweb/NpfCellWeb/reCharge/reChargeCheck";
            String param = "secstate.state=%s&commonBean.userChooseMode=0&commonBean.phoneNo=%s&commonBean.provinceCode=%s&commonBean.cityCode=%s&rechargeMode=%s&cardBean.cardValueCode=04&offerPriceStrHidden=98.50&cardBean.cardValue=100&cardBean.minCardNum=1&cardBean.maxCardNum=3&MaxThreshold01=15&MinThreshold01=1&MaxThreshold02=10&MinThreshold02=1&MaxThreshold03=6&MinThreshold03=1&MaxThreshold04=3&MinThreshold04=1&MaxThreshold05=1&MinThreshold05=1&actionPayFeeInfo.actionConfigId=&commonBean.payAmount=98.50&invoiceBean.need_invoice=1&invoiceBean.invoice_type=&invoiceBean.is_mailing=1&saveflag=false&invoiceBean.post_list=&invoiceBean.invoice_list=&commonBean.bussineType=&rechargeBean.cardPwd=%s";
            param = String.format(param, secstate, name, "", "", "用充值卡", cardPwd);
            url = url + "?" + param;
            URL uRL = new URL(url);
            URI uri = new URI(uRL.getProtocol(), uRL.getHost(), uRL.getPath(), uRL.getQuery(), null);

            result = EntityUtils.toString(httpClient.execute(new HttpGet(uri)).getEntity());
            url = "https://upay.10010.com/npfweb/NpfCellWeb/reCharge/reChargeCheck?secstate.state=3mCBuETgA/YTbuZO79gHFA==^@^0.0.1&commonBean.userChooseMode=0&commonBean.phoneNo=13167081006&commonBean.provinceCode=&commonBean.cityCode=&rechargeMode=用充值卡&cardBean.cardValueCode=04&offerPriceStrHidden=98.50&cardBean.cardValue=100&cardBean.minCardNum=1&cardBean.maxCardNum=3&MaxThreshold01=15&MinThreshold01=1&MaxThreshold02=10&MinThreshold02=1&MaxThreshold03=6&MinThreshold03=1&MaxThreshold04=3&MinThreshold04=1&MaxThreshold05=1&MinThreshold05=1&actionPayFeeInfo.actionConfigId=&commonBean.payAmount=98.50&invoiceBean.need_invoice=1&invoiceBean.invoice_type=&invoiceBean.is_mailing=1&saveflag=false&invoiceBean.post_list=&invoiceBean.invoice_list=&commonBean.bussineType=&rechargeBean.cardPwd=7788993322110008888";

           //https://upay.10010.com/npfweb/NpfCellWeb/reCharge/reChargeCheck?secstate.state=3mCBuETgA%2FYTbuZO79gHFA%3D%3D%5E%40%5E0.0.1&commonBean.userChooseMode=0&commonBean.phoneNo=13167081006&commonBean.provinceCode=031&commonBean.cityCode=310&rechargeMode=%E7%94%A8%E5%85%85%E5%80%BC%E5%8D%A1&cardBean.cardValueCode=04&offerPriceStrHidden=98.50&cardBean.cardValue=100&cardBean.minCardNum=1&cardBean.maxCardNum=3&MaxThreshold01=15&MinThreshold01=1&MaxThreshold02=10&MinThreshold02=1&MaxThreshold03=6&MinThreshold03=1&MaxThreshold04=3&MinThreshold04=1&MaxThreshold05=1&MinThreshold05=1&actionPayFeeInfo.actionConfigId=&commonBean.payAmount=98.50&invoiceBean.need_invoice=1&invoiceBean.invoice_type=&invoiceBean.is_mailing=1&saveflag=false&invoiceBean.post_list=&invoiceBean.invoice_list=&rechargeBean.cardPwd=7788993322110008888&commonBean.bussineType=
//                secstate.state:3mCBuETgA/YTbuZO79gHFA==^@^0.0.1
//                commonBean.userChooseMode:0
//                commonBean.phoneNo:13167081006
//                commonBean.provinceCode:031
//                commonBean.cityCode:310
//                rechargeMode:用充值卡
//                cardBean.cardValueCode:04
//                offerPriceStrHidden:98.50
//                cardBean.cardValue:100
//                cardBean.minCardNum:1
//                cardBean.maxCardNum:3
//                MaxThreshold01:15
//                MinThreshold01:1
//                MaxThreshold02:10
//                MinThreshold02:1
//                MaxThreshold03:6
//                MinThreshold03:1
//                MaxThreshold04:3
//                MinThreshold04:1
//                MaxThreshold05:1
//                MinThreshold05:1
//                actionPayFeeInfo.actionConfigId:
//                commonBean.payAmount:98.50
//                invoiceBean.need_invoice:1
//                invoiceBean.invoice_type:
//                invoiceBean.is_mailing:1
//                saveflag:false
//                invoiceBean.post_list:
//                invoiceBean.invoice_list:
//                rechargeBean.cardPwd:7788993322110008888
//                commonBean.bussineType:


        }


    }

}
