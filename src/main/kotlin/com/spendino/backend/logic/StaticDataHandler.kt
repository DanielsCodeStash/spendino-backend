package com.spendino.backend.logic

import com.spendino.backend.data.SpendingEntry
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs

class StaticDataHandler {

    fun staticDataModify(entries : List<SpendingEntry>) : List<SpendingEntry> {

        val modifiedEntries = ArrayList<SpendingEntry>()

        entries.forEach {
            it.amount = abs(it.amount)
            modifiedEntries.add(it)
        }


        val date = LocalDate.parse("1970-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        modifiedEntries.add( SpendingEntry(date, EntryCategorizer.housingCat, "Amortering", 3125))
        modifiedEntries.add( SpendingEntry(date, EntryCategorizer.housingCat, "Vatten", 200))
        modifiedEntries.add( SpendingEntry(date, EntryCategorizer.monthlyCat, "Linser", 350))

        return modifiedEntries

    }
}