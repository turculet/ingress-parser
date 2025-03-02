package com.turculet.parser.webtalk.page;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.turculet.parser.webtalk.page.WebUtils.getHtmlFromUrl;

class WebTalkPageParserTest {

    WebTalkPageParser parser = new WebTalkPageParser();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldParseATopic() throws IOException {
        String link = "https://feeder.webtalk.ru/viewtopic.php?id=78";

        String html = getHtmlFromUrl(link);

        System.out.println("HTML size: " + html.length());


        WebTalkPageParserResult parse = parser.parse(html);


        System.out.println(parse);
    }
}