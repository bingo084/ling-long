package com.bingo.linglong

import org.babyfish.jimmer.client.EnableImplicitApi
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableImplicitApi
@SpringBootApplication
class LingLongApplication

fun main(args: Array<String>) {
    runApplication<LingLongApplication>(*args)
}
