package com.amos.tool;

import java.io.*;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

/**
 * Created by amosli on 14-6-25.
 */
public class Tools {


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

    /**
     * 使用ssl通道并设置请求重试处理
     * @return
     */
    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain,String authType) throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            //设置请求重试处理,重试机制,这里如果请求失败会重试5次
            HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
                @Override
                public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                    if (executionCount >= 5) {
                        // Do not retry if over max retry count
                        return false;
                    }
                    if (exception instanceof InterruptedIOException) {
                        // Timeout
                        return false;
                    }
                    if (exception instanceof UnknownHostException) {
                        // Unknown host
                        return false;
                    }
                    if (exception instanceof ConnectTimeoutException) {
                        // Connection refused
                        return false;
                    }
                    if (exception instanceof SSLException) {
                        // SSL handshake exception
                        return false;
                    }
                    HttpClientContext clientContext = HttpClientContext.adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                    if (idempotent) {
                        // Retry if the request is considered idempotent
                        return true;
                    }
                    return false;
                }
            };



            return HttpClients.custom().setSSLSocketFactory(sslsf)
                    .setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36")
                    .setMaxConnPerRoute(25).setMaxConnPerRoute(256)
                    .setRetryHandler(retryHandler).build();

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * 提取数字
     * @param value
     * @return
     */
    public static String GetNumber(String value){
        return value.replaceAll("\\D", "");
    }

//    Pattern compile = Pattern.compile("\\d+\\.\\d+");
//    Matcher matcher = compile.matcher(abc);
//    matcher.find();
//    String string = matcher.group();//提取匹配到的结果
//    System.out.println(string);//0.00

}
