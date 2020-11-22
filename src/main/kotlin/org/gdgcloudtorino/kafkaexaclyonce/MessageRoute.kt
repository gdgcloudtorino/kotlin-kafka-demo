package org.gdgcloudtorino.kafkaexaclyonce

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.router

@Configuration
class MessageRoute(val messageListener: MessageListener) {

    @Bean
    fun messageRoute() {
        router { POST("/message", messageListener::sendMessage) }
    }

}