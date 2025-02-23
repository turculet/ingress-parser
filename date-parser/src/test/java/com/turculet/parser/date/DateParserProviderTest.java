package com.turculet.parser.date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DateParserProviderTest {

    @Test
    void shoutReturnDateParserImplementation() {
        DateParser parser = (DateParser) new DateParserProvider().getParser();
        assertNotNull(parser);
    }
}