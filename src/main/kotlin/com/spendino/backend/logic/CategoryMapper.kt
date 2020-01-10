package com.spendino.backend.logic

import com.spendino.backend.data.SpendingEntry
import com.spendino.backend.data.StatementEntry

class CategoryMapper {


    fun map(statementEntries : List<StatementEntry>) : List<SpendingEntry> {

        val categorizer = EntryCategorizer();
        val outSpendingEntries = ArrayList<SpendingEntry>()

        for(statementEntry in statementEntries) {

            val spendingEntry = categorizer.categorize(statementEntry) ?: continue;
            val subCategoryOnlyNumbers = statementEntry.description.trim().chars().allMatch(Character::isDigit)

            val existing = outSpendingEntries.find { it.category == spendingEntry.category && it.subCategory == spendingEntry.subCategory }
            if(existing != null && !subCategoryOnlyNumbers) { // for swish and bank transfer we don't want to combine posts
                existing.amount += spendingEntry.amount
            } else {
                outSpendingEntries.add(spendingEntry)
            }
        }

        outSpendingEntries.sortBy { it.category }

        return outSpendingEntries;
    }
}