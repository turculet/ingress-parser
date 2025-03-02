package com.turculet.parser.infra;

import com.turculet.parser.api.ParserProviderInterface;
import lombok.extern.slf4j.Slf4j;

import java.util.ServiceLoader;

@Slf4j
public class ParserServiceLoader {

    private static final String DEFAULT_PROVIDER = "com.turculet.parser.date.DateParserProvider";

    public static ParserProviderInterface defaultProvider() {
        return provider(DEFAULT_PROVIDER);
    }

    public static ParserProviderInterface provider(final String providerName) {
        var providers = ServiceLoader.load(ParserProviderInterface.class);

        log.info("Providers found: {}", providers.stream().count());

        for (ParserProviderInterface provider : providers) {
            if (provider.getClass().getName().equals(providerName)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("No ParserProviderInterface found with name " + providerName);
    }

}
