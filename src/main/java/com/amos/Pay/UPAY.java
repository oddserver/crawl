package com.amos.Pay;

import com.amos.tool.Tools;
import org.apache.http.Consts;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by amosli on 14-7-31.
 */
public class UPAY {

    public static void main(String args[]) throws Exception {

        String phone = "13167010000";
        String passCode = "123 4588 9908 7786 889".replaceAll("\\s", "");


        CloseableHttpClient client = Tools.createSSLClientDefault();
        String url = "https://upay.10010.com/npfweb/npfcellweb/phone_recharge_fill.htm";
        String result = EntityUtils.toString(client.execute(new HttpGet(url)).getEntity());
        Matcher matcher = Pattern.compile("name=\"secstate.state\" type=\"hidden\" value=\"(.*?)\"").matcher(result);
        String secstate = "3mCBuETgA/YTbuZO79gHFA==^@^0.0.1";
        if(matcher.find()){
            secstate = matcher.group(1);
        }


        url = MessageFormat.format("https://upay.10010.com/npfweb/getArea/init?callback=&phoneNo={0}", phone);
        result = EntityUtils.toString(client.execute(new HttpGet(url)).getEntity(), Consts.UTF_8);

//        ({'prov' : '038X389X福建X三明'})
//        ({'prov' : '11X110'})
//        ({'prov' : '031X310X上海X上海'})

        matcher=Pattern.compile("X").matcher(result);
        int count =0;
        while(matcher.find()){
            count++;
        }
        if(count!=3){
            System.out.println("手机格式有问题!");
            return;
        }

        String provinceCode ="";
        String cityCode = "";

        matcher = Pattern.compile("\'prov\' : \'(.*?)X(.*?)X(.*?)X(.*?)\'").matcher(result);
        if(matcher.find()){
            provinceCode = matcher.group(1);
            cityCode = matcher.group(2);

            System.out.println("provinceCode:"+provinceCode);
            System.out.println("cityCode:"+cityCode);
            System.out.println("matcher.group(3):"+matcher.group(3));
            System.out.println("matcher.group(4):"+matcher.group(4));
        }

        url="https://upay.10010.com/npfweb/NpfCellWeb/reCharge/reChargeCheck";
        String  param= String.format("secstate.state=%s&commonBean.userChooseMode=0&commonBean.phoneNo=%s&commonBean.provinceCode=%s&commonBean.cityCode=%s&rechargeMode=%s&cardBean.cardValueCode=04&offerPriceStrHidden=98.50&cardBean.cardValue=100&cardBean.minCardNum=1&cardBean.maxCardNum=3&MaxThreshold01=150&MinThreshold01=1&MaxThreshold02=10&MinThreshold02=1&MaxThreshold03=6&MinThreshold03=1&MaxThreshold04=3&MinThreshold04=1&MaxThreshold05=1&MinThreshold05=1&actionPayFeeInfo.actionConfigId=&commonBean.payAmount=98.50&invoiceBean.need_invoice=1&invoiceBean.invoice_type=&invoiceBean.is_mailing=1&saveflag=false&invoiceBean.post_list=&invoiceBean.invoice_list=&rechargeBean.cardPwd=%s&commonBean.bussineType="
        ,secstate,phone,provinceCode,cityCode,"用充值卡",passCode);

        url=url+"?"+param;
        URL encodeURL = new URL(url);
        URI uri = new URI(encodeURL.getProtocol(),encodeURL.getUserInfo(),encodeURL.getHost(),encodeURL.getPort(),encodeURL.getPath(),encodeURL.getQuery(),"");

        result = EntityUtils.toString(client.execute(new HttpGet(uri)).getEntity(),Consts.UTF_8);
        //{"secstate":"3mCBuETgA/YTbuZO79gHFA==^@^0.0.1","out":"尊敬的用户，您好！为了更好的为您提供服务，我公司计划于2014年07月31日22：20至2014年08月01日06：30对系统进行升级。由此给您带来的不便，敬请谅解，感谢您对我们的一贯支持！"}
        //{"secstate":"3mCBuETgA/YTbuZO79gHFA==^@^0.0.1","out":"请正确输入充值卡密码。"}
        //{"secstate":"3mCBuETgA/YTbuZO79gHFA==^@^0.0.1","out":"请正确输入手机号码。"}
        System.out.print("result:"+result);



    }

}
