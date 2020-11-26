package org.gdgcloudtorino.kafkaexaclyonce

import org.apache.kafka.clients.producer.RecordMetadata
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.function.router

@RestController
class MessageRoute(val messageListener: MessageListener) {

    @PostMapping("/message")
    fun postMessage(@RequestBody message:String): RecordMetadata? {
        return messageListener.sendMessage(message)
    }

}