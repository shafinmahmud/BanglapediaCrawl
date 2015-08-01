/*
 */
package me.shafin.banglapediacrawl;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author SHAFIN
 */
public class JsoupParser {

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21";
    private static final int TIME_OUT_VALUE = 10000;
    public static String getHtmlAsString(String url) throws IOException {
        Document htmlDoc = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .timeout(TIME_OUT_VALUE).get();
        return htmlDoc.html();
    }
    
    public static Document getHtmlAsDocument(String url) throws IOException{
        return Jsoup.connect(url).userAgent(USER_AGENT)
                .timeout(TIME_OUT_VALUE).get();
    }

    public static String getEnglishUrl(String url) throws IOException, NullPointerException {
        Document htmlDoc = Jsoup.connect(url).userAgent(USER_AGENT)
                .timeout(TIME_OUT_VALUE).get();
        Element div = htmlDoc.select("div#other_language_link").first();
        return div.select("a").first().attr("href");
    }
    
    public static String getEnglishUrlFromDocument(Document htmlDoc) throws IOException, NullPointerException {
        Element div = htmlDoc.select("div#other_language_link").first();
        return div.select("a").first().attr("href");
    }

}
