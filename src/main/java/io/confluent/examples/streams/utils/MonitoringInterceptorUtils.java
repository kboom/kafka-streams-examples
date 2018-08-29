package io.confluent.examples.streams.utils;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

/**
 * Utility helper class that will enable MonitoringInterceptors when a Kafka Streams application is deployed in Confluent Enterprise.
 *
 * Note that this requires you to run Confluent Control Center
 */
public class MonitoringInterceptorUtils {

    private static final String CONSUMER_INTERCEPTOR = "io.confluent.monitoring.clients.interceptor.MonitoringConsumerInterceptor";
    private static final String PRODUCER_INTERCEPTOR = "io.confluent.monitoring.clients.interceptor.MonitoringProducerInterceptor";


    private MonitoringInterceptorUtils() {
    }

    public static void maybeConfigureInterceptors(final Properties streamsConfig) {
        try {
            Class.forName(PRODUCER_INTERCEPTOR);
        } catch (final ClassNotFoundException e) {
            System.out.println("Producer interceptor not found, skipping");
            return;
        }

        try {
            Class.forName(CONSUMER_INTERCEPTOR);
        } catch (final ClassNotFoundException e) {
            System.out.println("Consumer interceptor not found, skipping");
            return;
        }
        streamsConfig.put(StreamsConfig.PRODUCER_PREFIX + ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, PRODUCER_INTERCEPTOR);
        streamsConfig.put(StreamsConfig.CONSUMER_PREFIX + ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, CONSUMER_INTERCEPTOR);
    }

}