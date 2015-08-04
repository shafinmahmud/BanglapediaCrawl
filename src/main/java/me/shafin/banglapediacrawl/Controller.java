/*
 */
package me.shafin.banglapediacrawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import org.jsoup.nodes.Document;

/**
 *
 * @author SHAFIN
 */
public class Controller {

    private static final String URL_FILE_LOCATION = "D:\\all_eng_link.txt";
    private static final String FILE_SAVE_LOCATION = "D:\\crawl\\test\\";

    private static Integer index = 0;
    
    public static void main(String[] args) {

        List<String> links = FileHandler.readFile(URL_FILE_LOCATION);
        index = Integer.parseInt(links.get(0));
        System.out.println(index);
        links.remove(0);
        //iterateBanglaList(links);
        iterateEnglishList(links);

    }

    public static void iterateBanglaList(List<String> links) {
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
                System.out.println("INFO: " + iter.nextIndex() + ": (" + successCount + "/" + accessedCount + ") EN-" + urlEn);
                getParallelText("BN", "EN", urlEn, htmlBnDoc);
                ++successCount;

                iter.remove();
            } catch (IOException ex) {
                System.err.println("INFO: " + " > ERROR 500: Network Error. " + ex.toString());
            } catch (NullPointerException ex) {
                String x = " > ERROR 404: Page contais No English Article! : " + ex;
                System.out.println("INFO: " + x);
                iter.remove();
            }
        }
        FileHandler.writeListToFile(FILE_SAVE_LOCATION + "_unattemptedLinks.txt", links);
    }

    public static void iterateEnglishList(List<String> links) {
        ListIterator<String> iter = links.listIterator();
        List<String> discardedLinks = new ArrayList<>();
        List<String> noBanglaLinks = new ArrayList<>();

        int successCount = 0;
        int accessedCount = 0;

        while (iter.hasNext()) {
            String url = iter.next();

            ++accessedCount;
            try {
                //parallel bangla link extract
                Document htmlEnDoc = JsoupParser.getHtmlAsDocument(url);
                if (!JsoupParser.isTheDocReferingToAnother(htmlEnDoc)) {
                    String urlBn = JsoupParser.getBanglaUrlFromDocument(htmlEnDoc);

                    System.out.println("INFO: " + iter.nextIndex() + ": (" + successCount + "/" + accessedCount + ") " + url);
                    getParallelText("EN", "BN", urlBn, htmlEnDoc);
                    ++successCount;
                } else {
                    discardedLinks.add(url);
                    System.out.println("INFO: " + iter.nextIndex() + ": (" + successCount + "/" + accessedCount + ") DISCARDED " + url);
                }
                iter.remove();
            } catch (IOException ex) {
                System.err.println("INFO: " + " > ERROR 500: Network Error. " + ex.toString());
            } catch (NullPointerException ex) {
                noBanglaLinks.add(url);
                String x = " > ERROR 404: Page contais No Bangla Article! : " + ex;
                System.out.println("INFO: " + x);
                iter.remove();
            }
        }
        links.add(index.toString());//adding the current index value for next Run
        Collections.swap(links, 0, links.size()-1 );
        FileHandler.writeListToFile(FILE_SAVE_LOCATION + "_unattemptedLinks.txt", links);
        FileHandler.writeListToFile(FILE_SAVE_LOCATION + "_discarded.txt", discardedLinks);
        FileHandler.writeListToFile(FILE_SAVE_LOCATION + "_parallelNotFound.txt", noBanglaLinks);
    }

    public static void getParallelText(String localLangTag, String otherLangTag,
            String otherLangUrl, Document htmlDocOfLocalLangUrl) throws IOException {
        // writing otherLangDoc file
        Document htmlotherLangDoc = JsoupParser.getHtmlAsDocument(otherLangUrl);
        String titleFromLocalLang = htmlDocOfLocalLangUrl.title();
        String txtOtherLang = BoilerPipeUtility.getArticleFromHtml(htmlotherLangDoc.html());
        
        if(!FileHandler.validateFileName(titleFromLocalLang)){
            titleFromLocalLang = FileHandler.getValidFileName(titleFromLocalLang);
        }
        System.out.println(titleFromLocalLang);
        FileHandler.writeFile(FILE_SAVE_LOCATION +index.toString()+"_"+ titleFromLocalLang + "_" + otherLangTag + ".txt", txtOtherLang);

        //  writing localLang file
        String txtLocalLang = BoilerPipeUtility.getArticleFromHtml(htmlDocOfLocalLangUrl.html());
        FileHandler.writeFile(FILE_SAVE_LOCATION +index.toString()+"_"+ titleFromLocalLang + "_" + localLangTag + ".txt", txtLocalLang);
        index++;
    }
}
