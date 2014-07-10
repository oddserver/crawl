package com.amos.crawl;

/**
 * Created by amosli on 14-7-10.
 */
public interface LinkFilter {

    public boolean accept(String url);

}
