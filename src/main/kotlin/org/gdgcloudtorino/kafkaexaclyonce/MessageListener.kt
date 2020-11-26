package org.gdgcloudtorino.kafkaexaclyonce

import org.apache.kafka.clients.producer.RecordMetadata
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import java.util.*
import kotlin.random.Random


/**
 * Leggiamo da input e scriviamo su topicA
 * leggiamo da topicA a topicB
 */
@Component
class MessageListener(
                     kafkaTemplate: KafkaTemplate<String, String>,appConfig: AppConfig) {
    companion object {
        val log:Logger = LoggerFactory.getLogger(this::class.java.simpleName)
    }

    private val kafkaTemplate: KafkaTemplate<String, String> = kafkaTemplate

    private val appConfig: AppConfig = appConfig

    fun sendMessage(message: String): RecordMetadata? {
        // ...
        return kafkaTemplate.executeInTransaction {
            kafkaTemplate.send(appConfig.input, UUID.fromString(message).toString(),message).get().recordMetadata
        }

    }

    @KafkaListener(topics = ["#{'\${app.input}'}"])
    fun process(message: String) {
        log.info("Process: {}", message)
        // invia al topic a
        kafkaTemplate.send(appConfig.topicA, UUID.fromString(message).toString(),
                String.format("Notification A: %s", message))
        // impostiamo un error rate generico. se un numero tra 0 e 100 risulta inferiore alla soglia di errore
        if(Random.nextDouble(0.0,100.0) < appConfig.errorRate){
            throw RuntimeException("ERROR TEST")
        }
        // questo dovrebbe generare un duplicato
    }

    @KafkaListener(topics = ["#{'\${app.topicA}'}"])
    fun processB(message: String) {
        log.info("Process: {}", message)
        // invia al topic a
        kafkaTemplate.send(appConfig.topicB, UUID.fromString(message).toString(),
                String.format("Notification B: %s", message))

    }

}