/*
 */
package me.shafin.banglapediacrawl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public static Document getHtmlAsDocument(String url) throws IOException {
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

    public static String getBanglahUrl(String url) throws IOException, NullPointerException {
        Document htmlDoc = Jsoup.connect(url).userAgent(USER_AGENT)
                .timeout(TIME_OUT_VALUE).get();
        Element div = htmlDoc.select("div#other_language_link").first();
        return div.select("a").first().attr("href");
    }

    public static String getBanglaUrlFromDocument(Document htmlDoc) throws IOException, NullPointerException {
        Element div = htmlDoc.select("div#other_language_link").first();
        return div.select("a").first().attr("href");
    }

//    public static void main(String[] args) {
//        try {
//            String url = "http://en.banglapedia.org/index.php?title=Arts_and_Crafts";
//            Document doc = getHtmlAsDocument(url);
//            System.out.println(isTheDocReferingToAnother(doc));
//        } catch (IOException ex) {
//            Logger.getLogger(JsoupParser.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

    public static boolean isTheDocReferingToAnother(Document htmlDoc) {
        String redirectSymptom = "see<ahref=";
        Element div = htmlDoc.select("div#mw-content-text").first();
        Element firstPtag = div.select("p").first();
        String firstTagString = firstPtag.toString().replace("&nbsp;",""); //removing &nbsp
        firstTagString = firstTagString.replaceAll("\\s", "");

        return firstTagString.contains(redirectSymptom);
    }

}
