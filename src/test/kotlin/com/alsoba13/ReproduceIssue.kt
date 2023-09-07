package com.alsoba13
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test



@MicronautTest
class ReproduceIssue(
    @Client("/") private val client: HttpClient,
) {

    @Test
    fun runConsecutiveRequests() {
        runExperiment(100)
        runExperiment(1900)
        runExperiment(3000)
    }

    private fun runExperiment(delayMillis: Long) {
        println()
        println("Running 10 request with ${delayMillis}ms delay between requests")
        println()

        for(i in 1..10) {
            val response = client.toBlocking().retrieve("/")
            println(response)
            Thread.sleep(delayMillis)
        }
    }

}
