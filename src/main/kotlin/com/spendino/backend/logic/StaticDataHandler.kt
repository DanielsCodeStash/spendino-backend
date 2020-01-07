package com.spendino.backend.logic

import com.spendino.backend.data.SpendingEntry
import kotlin.math.abs

class StaticDataHandler {

    fun staticDataModify(entries : List<SpendingEntry>) : List<SpendingEntry> {

        entries
                .forEach { it.amount = abs(it.amount) }


        return entries

    }
}