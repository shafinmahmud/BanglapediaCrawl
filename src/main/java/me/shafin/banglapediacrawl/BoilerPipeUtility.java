/*
 */
package me.shafin.banglapediacrawl;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;

/**
 *
 * @author SHAFIN
 */
public class BoilerPipeUtility {

    public static String getArticleFromHtml(String inputText) {
        try {
            return CommonExtractors.ARTICLE_EXTRACTOR.getText(inputText);
        } catch (BoilerpipeProcessingException ex) {
            Logger.getLogger(BoilerPipeUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String getArticleFromUrl(String pageUrl) {
        try {
            URL url = new URL(pageUrl);
            BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;

            InputSource is = new InputSource();
            is.setEncoding("UTF-8");
            is.setByteStream(url.openStream());

            return extractor.getText(is);

        } catch (BoilerpipeProcessingException | MalformedURLException ex) {

        } catch (IOException ex) {

        }
        return "";
    }

}
