package com.amos.crawl;

import java.util.Set;

/**
 * Created by amosli on 14-7-10.
 */
public class MyCrawler {
    /**
     * 使用种子初始化URL队列
     *
     * @param seeds
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++) {
            LinkQueue.addUnvisitedUrl(seeds[i]);
        }
    }

    public void crawling(String[] seeds) {
        //定义过滤器,提取以http://news.fudan.edu.cn/的链接
        LinkFilter filter = new LinkFilter() {
            @Override
            public boolean accept(String url) {
                if (url.startsWith("http://news.fudan.edu.cn")) {
                    return true;
                }
                return false;
            }
        };
        //初始化URL队列
        initCrawlerWithSeeds(seeds);
        //循环条件:待抓取的链接不且抓取的网页不多100
        while (!LinkQueue.isUnvisitedUrlsEmpty() && LinkQueue.getVisitedUrlNum() <= 100) {
            //附头URL出队列
            String visitURL = (String) LinkQueue.unVisitedUrlDeQueue();
            DownLoadFile downloader = new DownLoadFile();
            //下载网页
            downloader.downloadFile(visitURL);
            //该URL放入怩访问的URL中
            LinkQueue.addVisitedUrl(visitURL);
            //提取出下载网页中的URL
            Set<String> links = HtmlParserTool.extractLinks(visitURL, filter);

            //新的未访问的URL入列
            for (String link : links) {
                LinkQueue.addUnvisitedUrl(link);
            }
        }

    }

    public static void main(String args[]) {
        MyCrawler myCrawler = new MyCrawler();
        myCrawler.crawling(new String[]{"http://news.fudan.edu.cn/news/"});

        int k=0;
        //循环条件:待抓取的链接不且抓取的网页不多100
        while (!LinkQueue.isUnvisitedUrlsEmpty() && LinkQueue.getVisitedUrlNum() <= 100) {
            //附头URL出队列
            String visitURL = (String) LinkQueue.unVisitedUrlDeQueue();
            DownLoadFile downloader = new DownLoadFile();
            //下载网页
            downloader.downloadFile(visitURL);
            //该URL放入怩访问的URL中
            LinkQueue.addVisitedUrl(visitURL);

            System.out.println("visitURL:"+(k++)+visitURL);
        }

    }


}
