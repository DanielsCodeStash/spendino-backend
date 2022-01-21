package com.spendino.backend.logic

import com.spendino.backend.data.SpendingData
import com.spendino.backend.data.StatementEntry
import org.springframework.stereotype.Component

@Component
class CategoryMapper(
    val categorizer: EntryCategorizer
) {

    fun categorize(statementEntries: List<StatementEntry>): SpendingData {

        val spendingData = SpendingData()

        statementEntries.forEach { categorize(it, spendingData) }

        return spendingData
    }

    fun categorize(entry: StatementEntry, spendingData: SpendingData) {
        val categories = categorizer.categorize(entry) ?: return
        val spending = StatementEntry(entry.date, entry.description, entry.amount)

        if (categories.category == EntryCategorizer.categoryNeeded) {
            spendingData.uncategorized.add(spending)
        } else {
            spendingData.addSpending(spending, categories.category, categories.subCategory)
        }
    }
}