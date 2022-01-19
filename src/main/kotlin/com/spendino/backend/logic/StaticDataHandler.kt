package com.spendino.backend.logic

import com.spendino.backend.data.SpendingData

class StaticDataHandler {

    fun applyStaticModifications(data: SpendingData) {

        val rentPost = data.uncategorized.first { it.description.toLowerCase().contains("cyklisten")}
        data.addSpending(rentPost.copy(amount = 2618), EntryCategorizer.housingCat, "Hyra")
        data.uncategorized.remove(rentPost)


        val loanPost = data.uncategorized.first { it.description.toLowerCase().startsWith("lån") }
        val totalLoanAmount = loanPost.amount
        data.addSpending(loanPost.copy(amount = totalLoanAmount + 3125), EntryCategorizer.housingCat, "Ränta")
        data.uncategorized.remove(loanPost)


        // TODO: add this somewhere else
//        if(description == "cyklisten") {
//            return Categories(housingCat, "Hyra", -2618)
//        } else if(description.startsWith("lån")) {
//            return SpendingEntry(date, housingCat, "Ränta", statementEntry.amount + 3125)
//        }
    }


//    fun staticDataModify(entries : List<SpendingEntry>) : List<SpendingEntry> {
//
//        val modifiedEntries = ArrayList<SpendingEntry>()
//
//        entries.forEach {
//            it.amount = abs(it.amount)
//            modifiedEntries.add(it)
//        }
//
//
//        val date = LocalDate.parse("1970-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
//
//        modifiedEntries.add( SpendingEntry(date, EntryCategorizer.housingCat, "Amortering", 3125))
//        modifiedEntries.add( SpendingEntry(date, EntryCategorizer.housingCat, "Vatten", 200))
//        modifiedEntries.add( SpendingEntry(date, EntryCategorizer.monthlyCat, "Linser", 350))
//
//        return modifiedEntries
//
//    }
}