package org.gdgcloudtorino.kafkaexaclyonce

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

/**
 * La classe inizializza l'applicazione spring boot
 * EnableConfigurationProperties crea il singleton di AppConfig a partire dalla configurazione
 */
@SpringBootApplication
@EnableConfigurationProperties(value = [AppConfig::class])
class KafkaExaclyOnceApplication

fun main(args: Array<String>) {
	runApplication<KafkaExaclyOnceApplication>(*args)
}
