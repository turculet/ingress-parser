package com.turculet.parser.date;

import org.junit.jupiter.api.Test;

class DateParserTest {

    @Test
    void shouldParseInput() {
        DateParser dateParser = new DateParser();
        String date = "2015-11-11";
        DateParserResult parsedDate = dateParser.parse(date);
    }
}