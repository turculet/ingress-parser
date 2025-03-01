package com.turculet.parser.date;

import com.turculet.parser.api.Result;

public class DateParserResult extends Result {

    private String result;

    public DateParserResult(String result, boolean success) {
        this.result = result;
        super.success = success;
    }

    @Override
    public String toString() {
        return result;
    }
}
