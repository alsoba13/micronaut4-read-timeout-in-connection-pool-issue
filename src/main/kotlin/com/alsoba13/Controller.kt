package com.alsoba13

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

@Controller("/")
class Controller(
    private val micronautClient: MicronautClient,
) {
    @Get
    fun hello(): Publisher<MutableHttpResponse<String>> =
        Mono.from(
            micronautClient.get()
        ).map {
            val charCount = it.body().count()
            HttpResponse.ok("Micronaut launch page has $charCount characters")
        }.onErrorResume {
            Mono.just(HttpResponse.serverError(it.toString()))
        }
}