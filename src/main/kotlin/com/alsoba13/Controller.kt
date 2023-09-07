package com.alsoba13

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

@Controller("/")
class Controller(
    private val micronautClient: MicronautClient,
) {
    @Get
    fun hello(): Publisher<String> =
        Mono.from(
            micronautClient.get()
        ).map {
            val charCount = it.body().count()
            "Micronaut launch page has $charCount characters"
        }.onErrorResume {
            Mono.just(it.toString())
        }
}