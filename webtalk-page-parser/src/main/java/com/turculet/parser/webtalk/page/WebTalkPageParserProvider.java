package com.turculet.parser.webtalk.page;

import com.turculet.parser.api.Parser;
import com.turculet.parser.api.ParserProviderInterface;

public class WebTalkPageParserProvider implements ParserProviderInterface {
    @Override
    public Parser getParser() {
        return new WebTalkPageParser();
    }
}
