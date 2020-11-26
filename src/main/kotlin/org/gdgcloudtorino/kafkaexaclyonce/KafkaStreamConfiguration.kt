package org.gdgcloudtorino.kafkaexaclyonce

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.core.KafkaTemplate
import java.time.Duration


/**
 * Crea il processo in ascolto sui stream
 */
@Configuration
@EnableKafkaStreams
class KafkaStreamConfiguration(
        kafkaTemplate: KafkaTemplate<String, String>, appConfig: AppConfig) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java.simpleName)
    }

    private val kafkaTemplate: KafkaTemplate<String, String> = kafkaTemplate

    private val appConfig: AppConfig = appConfig

    /*
     * A stream is the most important abstraction provided by Kafka Streams: it represents an unbounded,
     * continuously updating data set, where unbounded means “of unknown or of unlimited size”.
     * Just like a topic in Kafka, a stream in the Kafka Streams API consists of one or more stream partitions.
     * A stream partition is an, ordered, replayable, and fault-tolerant sequence of immutable data records,
     * where a data record is defined as a key-value pair.
     */
    /*A processor topology or simply topology defines the computational logic of the data processing that needs
    * to be performed by a stream processing application.
    * A topology is a graph of stream processors (nodes) that are connected by streams (edges).
    * Developers can define topologies either via the low-level Processor API or via the Kafka Streams DSL,
    * which builds on top of the former.
     */
    @Bean
    fun kStream(kStreamBuilder: StreamsBuilder): KTable<String, Long>? {
        // Fluent KStream API
        val stream:KStream<String,ByteArray> = kStreamBuilder.stream(appConfig.input)
        val streamA = kStreamBuilder.stream<String, ByteArray>(appConfig.topicA)
        val joinWith = StreamJoined.with(
                Serdes.String(), /* key */
                Serdes.String(),   /* left value */
                Serdes.ByteArray())
        val joinA:KStream<String,String> = stream.mapValues { a, b -> String(b) }.join(streamA,
                { leftValue: String, rightValue: ByteArray -> "left=$leftValue, right=$rightValue" }, /* ValueJoiner */
                JoinWindows.of(Duration.ofMinutes(5)),
                joinWith  /* right value */
        )
        val joinB = joinA.join(kStreamBuilder.stream(appConfig.topicB),
                { leftValue: String, rightValue: ByteArray -> "left=$leftValue, right=$rightValue" }, /* ValueJoiner */
                JoinWindows.of(Duration.ofMinutes(5)),
                joinWith)

        return joinB.groupByKey().count(Named.`as`("end_result"));


    }
}