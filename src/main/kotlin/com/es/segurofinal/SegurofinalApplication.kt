package com.es.segurofinal

import com.es.segurofinal.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class SegurofinalApplication

fun main(args: Array<String>) {
	runApplication<SegurofinalApplication>(*args)
}
