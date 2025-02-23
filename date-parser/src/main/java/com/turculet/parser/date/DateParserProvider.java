package com.turculet.parser.date;

import com.turculet.parser.api.Parser;
import com.turculet.parser.api.ParserProviderInterface;

public class DateParserProvider implements ParserProviderInterface {

    @Override
    public Parser getParser() {
        return new DateParser();
    }

}
