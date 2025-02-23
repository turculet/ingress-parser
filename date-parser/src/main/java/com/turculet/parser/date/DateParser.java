package com.turculet.parser.date;

import com.turculet.parser.api.Parser;

public class DateParser implements Parser {

    @Override
    public String parse(String data) {
        return data + "to date";
    }
}
