package com.turculet.ingress_parser.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
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
        KStream<String, String> stream = builder.stream(inputTopicName, Consumed.with(Serdes.String(), Serdes.String()));

        stream.map((key, value) -> {
            log.info("Key: {} Value length: {}", key, value.length());
            log.info("Just pass this value through");
            return new KeyValue<>(key, value);
        }).to(outputTopicName, Produced.with(Serdes.String(), Serdes.String()));

        return stream;
    }

}
