package com.spendino.backend.logic

import com.spendino.backend.data.GeneratorConfig
import com.spendino.backend.data.StatementEntry
import com.spendino.backend.data.SubCategory
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

    data class Categories(
        val category: String,
        val subCategory: String
    )

    fun categorize(statementEntry: StatementEntry): Categories? {

        val description = statementEntry.description.trim().toLowerCase()
        val date = statementEntry.date

        if(config.ignored.any { description.contains(it, true) }) {
            return null
        }

        searchMatchingCategory(description, date, statementEntry.amount)?.let { return it }

        return Categories(categoryNeeded, statementEntry.description)
    }

    private fun searchMatchingCategory(description: String, date: LocalDate, amount: Int): Categories? {

        config.categories.forEach { (categoryName, subCategory) ->
            subCategory.forEach { (subCategoryName, statementDescriptions) ->
                statementDescriptions.forEach { statementDescription ->
                    if(description.contains(statementDescription, ignoreCase = true)) {
                        return Categories(categoryName, subCategoryName)
                    }
                }
             }
        }
        return null
    }

}