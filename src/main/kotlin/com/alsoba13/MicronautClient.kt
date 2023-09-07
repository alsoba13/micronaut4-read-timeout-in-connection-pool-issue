package com.alsoba13

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.HttpClientConfiguration
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import java.time.Duration
import java.util.*

@Client("https://micronaut.io/launch", configuration = MicronautClient.ClientConfig::class)
interface MicronautClient {
    @Get
    fun get(): Publisher<HttpResponse<String>>

    @Singleton
    class ClientConfig: HttpClientConfiguration() {
        override fun getConnectionPoolConfiguration(): ConnectionPoolConfiguration = ConnectionPoolConfiguration().also { it.isEnabled = true }
        override fun getReadTimeout(): Optional<Duration> = Optional.of(Duration.ofSeconds(2))
    }
}




