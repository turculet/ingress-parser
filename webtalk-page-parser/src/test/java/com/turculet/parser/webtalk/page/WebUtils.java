package com.turculet.parser.webtalk.page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class WebUtils {

    private static Logger logger = Logger.getLogger(WebUtils.class.getName());

    public static String getHtmlFromUrl(String url) throws IOException {
        //https://feeder.webtalk.ru/viewtopic.php?id=31918

        String cachedHtml = readFile(resolveFileName(url));
        if (cachedHtml != null) {
            logger.info("Got cached HTML");
            return cachedHtml;
        }

        Document doc = Jsoup.connect(url).get();
        String htmlContent = doc.html();
        saveIt(htmlContent, url);

        return htmlContent;
    }

    private static void saveIt(String htmlContent, String url) throws IOException {
        String fileName = resolveFileName(url);

        // Get the classpath directory
        URL resourceUrl = WebUtils.class.getClassLoader().getResource("");
        if (resourceUrl == null) {
            System.err.println("Classpath resource folder not found!");
            return;
        }
        String classpathDir = resourceUrl.getPath();  // Get classpath directory

        // Create file object
        File file = new File(classpathDir, fileName);
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(htmlContent);
        }

        System.out.println("HTML content saved successfully at: " + file.getAbsolutePath());
    }

    private static String readFile(String filePath) throws IOException {

        try {
            // Locate file inside classpath
            URL resource = WebUtils.class.getClassLoader().getResource(filePath);
            if (resource == null) {
                System.err.println("File not found in classpath!");
                return null;
            }
            String htmlContent = Files.readString(Path.of(resource.toURI()), StandardCharsets.UTF_8);
            logger.info("HTML content read successfully at: " + filePath);
            return htmlContent;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String resolveFileName(String url) {
        String[] verbs = url.split("=");
        String pageTitle = verbs[verbs.length - 1];
        String fileName = pageTitle + ".html";
        return fileName;
    }

}
