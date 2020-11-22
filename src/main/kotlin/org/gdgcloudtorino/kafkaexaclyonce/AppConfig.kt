package org.gdgcloudtorino.kafkaexaclyonce

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app")
data class AppConfig(val errorRate:Double,
                     val input: String,
                     val topicA: String,
                     val topicB: String,
                     val eos:Boolean) {

}