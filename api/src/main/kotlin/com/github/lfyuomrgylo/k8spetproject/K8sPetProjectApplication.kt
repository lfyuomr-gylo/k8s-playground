package com.github.lfyuomrgylo.k8spetproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class K8sPetProjectApplication

fun main(args: Array<String>) {
    runApplication<K8sPetProjectApplication>(*args)
}