/*
 */
package me.shafin.banglapediacrawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.jsoup.nodes.Document;

/**
 *
 * @author SHAFIN
 */
public class Controller {

    private static final String FILE_SAVE_LOCATION = "E:\\crawl\\parallel\\";

    public static void main(String[] args) {

        List<String> links = FileHandler.readFile("E:\\all.txt");
        iterateList(links);
        int i = 0;
//        for (String url : links) {
//            try {
//                i++;
//                //parallel english link extract
//                Document htmlBnDoc = JsoupParser.getHtmlAsDocument(url);
//                String urlBn = url;
//                String urlEn = JsoupParser.getEnglishUrlFromDocument(htmlBnDoc);
//                //System.out.println(i + ": BN-" + urlBn);
//                System.out.println(i + ": EN-" + urlEn);
//
////                //writing english file
////                Document htmlEnDoc = JsoupParser.getHtmlAsDocument(urlEn);
////                String titleEn = htmlEnDoc.title();
////                String txtEn = BoilerPipeUtility.getArticleFromHtml(htmlEnDoc.html());
////                FileHandler.writeFile(FILE_SAVE_LOCATION+i+"_"+titleEn+"_EN.txt", txtEn);
////                
////                //writing bangla file
////                String txtBn = BoilerPipeUtility.getArticleFromHtml(htmlBnDoc.html());
////                FileHandler.writeFile(FILE_SAVE_LOCATION+i+"_"+titleEn+"_BN.txt", txtBn);
//            } catch (IOException ex) {
//                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (NullPointerException ex) {
//                String x = i + " > ERROR 404: Page contais No English Article!";
//                lost.add(x);
//                System.out.println(x);
//            }
//        }
        // FileHandler.writeListToFile(FILE_SAVE_LOCATION + "lost.txt", (ArrayList<String>) lost);
    }

    public static void iterateList(List<String> links) {
        ListIterator<String> iter = links.listIterator();

        int successCount = 0;
        int accessedCount = 0;

        while (iter.hasNext()) {
            String url = iter.next();

            ++accessedCount;
            try {
                //parallel english link extract
                Document htmlBnDoc = JsoupParser.getHtmlAsDocument(url);
                String urlEn = JsoupParser.getEnglishUrlFromDocument(htmlBnDoc);
                System.out.println("INFO: "+iter.nextIndex() + ": (" + successCount + "/" + accessedCount + ") EN-" + urlEn);
                getParallelText(urlEn, htmlBnDoc);
                ++successCount;

                iter.remove();
            } catch (IOException ex) {
                System.err.println("INFO: "+" > ERROR 500: Network Error. " + ex.toString());
            } catch (NullPointerException ex) {
                String x = " > ERROR 404: Page contais No English Article!";
                System.out.println("INFO: "+x);
                iter.remove();
            }
        }
        FileHandler.writeListToFile(FILE_SAVE_LOCATION + "_unattemptedLinks.txt", links);
    }

    public static void getParallelText(String urlEn, Document htmlBnDoc) throws IOException {
        // writing english file
        Document htmlEnDoc = JsoupParser.getHtmlAsDocument(urlEn);
        String titleEn = htmlEnDoc.title();
        String txtEn = BoilerPipeUtility.getArticleFromHtml(htmlEnDoc.html());
        FileHandler.writeFile(FILE_SAVE_LOCATION +titleEn + "_EN.txt", txtEn);

        //  writing bangla file
        String txtBn = BoilerPipeUtility.getArticleFromHtml(htmlBnDoc.html());
        FileHandler.writeFile(FILE_SAVE_LOCATION +titleEn + "_BN.txt", txtBn);
    }
}
