package com.turculet.parser.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

@Configuration
@EnableKafkaStreams
@Slf4j
public class ApplicationStreamConfig {

    @Value("${parser.stream.input.topic}")
    private String inputTopicName;

    @Value("${parser.stream.output.topic}")
    private String outputTopicName;

    @Bean
    public KStream<String, String> stream(StreamsBuilder builder, StreamsBuilderFactoryBean streamsBuilderFactoryBean, KafkaStreamsConfiguration kafkaStreamsConfiguration) {
        KStream<String, String> stream = builder.stream(inputTopicName, Consumed.with(Serdes.String(), Serdes.String()));

        stream.mapValues(value -> new TryValueMapper().apply(value))
                .filter(TryValueMapper::isValid)
                .to(outputTopicName, Produced.with(Serdes.String(), Serdes.String()));

        streamsBuilderFactoryBean.setStreamsUncaughtExceptionHandler(exception -> {
            log.error(exception.getMessage(), exception);
            return StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.SHUTDOWN_APPLICATION;
        });

        return stream;
    }

    static class TryValueMapper implements ValueMapper<String, String> {

        @Override
        public String apply(String value) {
            try {
                if (value.contains("error")) {
                    throw new RuntimeException("Mimic unexpected error");
                }

                return value;
            } catch (Exception e) {
                return null;
            }
        }

        public static boolean isValid(String key, String value) {
            return value != null;
        }
    }


}
