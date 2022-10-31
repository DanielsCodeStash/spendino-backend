package com.spendino.backend.logic

import org.springframework.stereotype.Component

@Component
class SavingsHandler {

    var daniel = 0
    var lyrin = 0

    fun fetchInput() {
        println("Daniel savings?")
        daniel = readLine()!!.toInt()
        println("Lyrin savings?")
        lyrin = readLine()!!.toInt()
    }
}