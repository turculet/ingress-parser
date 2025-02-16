package com.turculet.ingress_parser.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafkaStreams
@Slf4j
public class StreamConfig {

    @Value("${parser.stream.input.topic}")
    private String inputTopicName;

    @Value("${parser.stream.output.topic}")
    private String outputTopicName;

    @Bean
    public KStream<String, String> stream(StreamsBuilder builder) {
        KStream<String, String> stream = builder.stream(inputTopicName);

        stream.map((key, value) -> {
            log.info("Key: {} Value length: {}", key, value.length());
                    return new KeyValue<>(key, "parsedValue");
                }
        ).to(outputTopicName);

        return stream;
    }

}
