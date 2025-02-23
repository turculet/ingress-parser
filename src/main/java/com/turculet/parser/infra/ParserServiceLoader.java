package com.turculet.parser.infra;

import com.turculet.parser.api.ParserProviderInterface;

import java.util.ServiceLoader;

public class ParserServiceLoader {

    private static final String DEFAULT_PROVIDER = "com.turculet.parser.date.DateParserProvider";

    public static ParserProviderInterface defaultProvider() {
        return provider(DEFAULT_PROVIDER);
    }

    public static ParserProviderInterface provider(final String providerName) {
        var providers = ServiceLoader.load(ParserProviderInterface.class);

        for (ParserProviderInterface provider : providers) {
            if (provider.getClass().getName().equals(providerName)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("No ParserProviderInterface found with name " + providerName);
    }

}
