
package edu.memphis.nlp.tools;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nabin
 */
public class HtmlLinkExtractor {
    public static void main(String[] args) throws MalformedURLException, URISyntaxException{
        //String text = "jsfdlasjfkla <a href='www.abc.com/abc.txt' > fasfd </a> fdsafas";
        String text = "jsfdlasjfkla <a href='#' > fasfd </a> fdsafas";
        String pattern = "<a(?:\\s+[a-zA-Z0-9_]+=('|\")[a-zA-Z0-9_./\\\\-]+\\1)*?\\s+href=('|\")([%&#:a-zA-Z0-9~_./\\\\-]+)\\2(?:\\s+[a-zA-Z0-9_]+=('|\")[a-zA-Z0-9_./\\\\-]+\\4)*?\\s*>.*?<\\s*/a>";
        Pattern regex = Pattern.compile(pattern);
        
        Matcher matcher = regex.matcher(text);
        while(matcher.find()){
            System.out.println(matcher.group(3));
        }
        String baseUrl = "http://www.abc.com";
        String relativeUrl = "abc.txt";
        //URL mergedURL = new URL(baseUrl);
        URL mergedURL = new URL(new URL(baseUrl), relativeUrl);
        System.out.println(mergedURL.toURI().toString());
    }
}
