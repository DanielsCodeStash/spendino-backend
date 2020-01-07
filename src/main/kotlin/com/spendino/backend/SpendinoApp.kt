package com.spendino.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpendinoApp

fun main(args: Array<String>) {
	runApplication<SpendinoApp>(*args)
}
