package org.gdgcloudtorino.kafkaexaclyonce

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(value = [AppConfig::class])
class KafkaExaclyOnceApplication

fun main(args: Array<String>) {
	runApplication<KafkaExaclyOnceApplication>(*args)
}
