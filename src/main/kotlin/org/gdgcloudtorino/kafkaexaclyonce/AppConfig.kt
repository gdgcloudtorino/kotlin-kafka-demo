package org.gdgcloudtorino.kafkaexaclyonce

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * La classe contiene le configurazioni applicative inserite all'interno dell application.yml
 * sotto il ramo app
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "app")
data class AppConfig(val errorRate:Double,
                     val input: String,
                     val topicA: String,
                     val topicB: String,
                     val eos:Boolean) {

}