package com.amos.tool;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

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

            File dir = new File("/home/amosli/workspace/crawl/");
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
     * 使用ssl通道
     * @param cookieStore
     * @return
     */
    public static CloseableHttpClient createSSLClientDefault(CookieStore cookieStore) {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain,String authType) throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setDefaultCookieStore(cookieStore).setSSLSocketFactory(sslsf).build();

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
