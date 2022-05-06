package com.spendino.backend.logic

import org.springframework.stereotype.Component

@Component
class SavingsHandler {

    var daniel = 1
    var lyrin = 1

    fun fetchInput() {
        println("Daniel savings?")
        daniel = readLine()!!.toInt()
        println("Lyrin savings?")
        lyrin = readLine()!!.toInt()
    }
}