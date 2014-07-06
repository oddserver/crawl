package com.amos;
import com.amos.tool.DownUtil;


/**
 * Created by amosli on 14-7-2.
 */
public class DownUtilTest {

    public static void main(String[] args) throws Exception {
        // 初始化DownUtil对象

      final DownUtil downUtil = new DownUtil("http://mirrors.cnnic.cn/apache/tomcat/tomcat-7/v7.0.54/bin/apache-tomcat-7.0.54.zip", "tomcat-7.0.54.zip", 2);

//        final com.amos.DownUtil downUtil = new com.amos.DownUtil("http://pic.cnitblog.com/avatar/534352/20131215160918.png", "amosli.png", 2);

        // 开始下载
        downUtil.download();
        new Thread() {
            public void run() {
                while (downUtil.getCompleteRate() < 1) {
                    // 每隔0.1秒查询一次任务的完成进度，
                    // GUI程序中可根据该进度来绘制进度条
                    System.out.println("已完成："
                            + downUtil.getCompleteRate());
                    try {
                        Thread.sleep(10);
                    } catch (Exception ex) {
                    }
                }
            }
        }.start();
    }
}
