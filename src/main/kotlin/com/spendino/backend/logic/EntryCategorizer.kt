package com.spendino.backend.logic

import com.spendino.backend.data.GeneratorConfig
import com.spendino.backend.data.SpendingEntry
import com.spendino.backend.data.StatementEntry
import org.springframework.stereotype.Component
import java.time.LocalDate


@Component
class EntryCategorizer(
    val config: GeneratorConfig
) {

    companion object {
        const val categoryNeeded = "<needs classification>"

        const val housingCat = "Bostad"
        const val monthlyCat = "Fasta utgift"
        const val oneTime = "One time"
        const val base = "Base"
    }

    fun categorize(statementEntry: StatementEntry): SpendingEntry? {

        val description = statementEntry.description.trim().toLowerCase()
        val date = statementEntry.date


        if(config.ignored.any { description.contains(it, true) }) {
            return null
        }

        if(description == "cyklisten") {
            return SpendingEntry(date, housingCat, "Hyra", -2618)
        } else if(description.startsWith("lån")) {
            return SpendingEntry(date, housingCat, "Ränta", statementEntry.amount + 3125)
        }

        searchMatchingCategory(description, date, statementEntry.amount)?.let { return it }

        return SpendingEntry(date, categoryNeeded, statementEntry.description, statementEntry.amount)
    }

    fun searchMatchingCategory(description: String, date: LocalDate, amount: Int): SpendingEntry? {

        config.categories.forEach { (categoryName, subCategory) ->
            subCategory.forEach { (subCategoryName, statementDescriptions) ->
                statementDescriptions.forEach { statementDescription ->
                    if(description.contains(statementDescription, ignoreCase = true)) {
                        return SpendingEntry(date, categoryName, subCategoryName, amount)
                    }
                }
             }
        }
        return null
    }


}