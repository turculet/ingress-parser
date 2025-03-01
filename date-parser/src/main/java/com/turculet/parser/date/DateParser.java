package com.turculet.parser.date;

import com.turculet.parser.api.Parser;

public class DateParser implements Parser {

    @Override
    public DateParserResult parse(String data) {
        return new DateParserResult(data + "to date", true);
    }
}
