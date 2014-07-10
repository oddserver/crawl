package com.amos.crawl;

import com.amos.tool.Configuration;
import com.amos.tool.Tools;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.AutoRetryHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.UnknownHostException;


/**
 * Created by amosli on 14-7-9.
 */
public class DownLoadFile {

    public String getFileNameByUrl(String url, String contentType) {
        //移除http http://
        url = url.contains("http://") ? url.substring(7) : url.substring(8);

        //text/html类型
        if (url.contains(".html")) {
            url = url.replaceAll("[\\?/:*|<>\"]", "_");
        } else if (contentType.indexOf("html") != -1) {
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
        } else {
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + "." + contentType.substring(contentType.lastIndexOf("/") + 1);
        }
        return url;
    }

//    /**
//     * 将网页写入到本地
//     * @param data
//     * @param filePath
//     */
//    private void saveToLocal(byte[] data, String filePath) {
//
//        try {
//            DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(filePath)));
//            for(int i=0;i<data.length;i++){
//                out.write(data[i]);
//            }
//            out.flush();
//            out.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * 写文件到本地
     *
     * @param httpEntity
     * @param filename
     */
    public static void saveToLocal(HttpEntity httpEntity, String filename) {

        try {

            File dir = new File(Configuration.FILEDIR);
            if (!dir.isDirectory()) {
                dir.mkdir();
            }

            File file = new File(dir.getAbsolutePath() + "/" + filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            InputStream inputStream = httpEntity.getContent();

            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(bytes)) > 0) {
                fileOutputStream.write(bytes, 0, length);
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String downloadFile(String url)  {

        //文件路径
        String filePath=null;

        //1.生成HttpClient对象并设置参数
        HttpClient httpClient = Tools.createSSLClientDefault();
        //设置http连接超时6s
//        try {
//            httpClient.getConnectionManager().wait(6000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //2.HttpGet对象并设置参数
        HttpGet httpGet = new HttpGet(url);

        //设置get请求超时5s
        //方法1
        //httpGet.getParams().setParameter("connectTimeout",5000);
        //方法2
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).build();
        httpGet.setConfig(requestConfig);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(statusCode!= HttpStatus.SC_OK){
                System.err.println("Method failed:"+httpResponse.getStatusLine());
                filePath=null;
            }

            filePath=getFileNameByUrl(url,httpResponse.getEntity().getContentType().getValue());
            saveToLocal(httpResponse.getEntity(),filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath;

    }



    public static void main(String args[]) throws IOException {
        String url = "http://websearch.fudan.edu.cn/search_dep.html";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        Header contentType = httpResponse.getEntity().getContentType();

        System.out.println("name:" + contentType.getName() + "value:" + contentType.getValue());
        System.out.println(new DownLoadFile().getFileNameByUrl(url, contentType.getValue()));

    }


}
