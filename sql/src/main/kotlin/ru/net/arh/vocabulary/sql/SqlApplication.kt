package ru.net.arh.vocabulary.sql

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SqlApplication

fun main(args: Array<String>) {
    runApplication<SqlApplication>(*args)
}
