package com.alsoba13
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import java.time.Clock


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

    private fun runExperiment(delayMilis: Long) {
        println()
        println("- Running 10 request with ${delayMilis}ms delay between requests")
        println()
        val clock = Clock.systemUTC()

        for(i in 1..10) {
            val start = clock.instant().toEpochMilli()
            val result = try {
                client.toBlocking().exchange("/", String::class.java)
            } catch (e: HttpClientResponseException) {
                e.response
            }
            val totalTime = clock.instant().toEpochMilli() - start
            println("    (${totalTime}ms) ${result.status.code} - ${result.body()}")
            println("    -- Sleeping for ${delayMilis}ms --")
            Thread.sleep(delayMilis)
        }
    }

}
