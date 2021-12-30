package com.spendino.backend.logic

import com.spendino.backend.data.SpendingEntry
import com.spendino.backend.data.StatementEntry

class CategoryMapper {


    fun map(statementEntries : List<StatementEntry>) : List<SpendingEntry> {

        val categorizer = EntryCategorizer();
        val outSpendingEntries = ArrayList<SpendingEntry>()
        val logCategoryToStatementEntry = HashMap<String, MutableList<StatementEntry>>()

        for(statementEntry in statementEntries) {

            val spendingEntry = categorizer.categorize(statementEntry) ?: continue
            val subCategoryOnlyNumbers = statementEntry.description.trim().chars().allMatch(Character::isDigit)

            val existing = outSpendingEntries.find { it.category == spendingEntry.category && it.subCategory == spendingEntry.subCategory }

            val logKey = spendingEntry.category + " -> " + spendingEntry.subCategory

            if(existing != null && !subCategoryOnlyNumbers) { // for swish and bank transfer we don't want to combine posts
                existing.amount += spendingEntry.amount

                logCategoryToStatementEntry[logKey]!!.add(statementEntry)
            } else {
                outSpendingEntries.add(spendingEntry)
                logCategoryToStatementEntry[logKey] = ArrayList()
                logCategoryToStatementEntry[logKey]!!.add(statementEntry)
            }
        }

        outSpendingEntries.sortBy { it.category }


        logCategoryToStatementEntry
                .filterKeys { k -> !k.startsWith("<needs classification>") }
                .forEach { (s, mutableList) ->
                    println(s)
                    mutableList
                            .sortBy { it.date };

                    mutableList
                            .forEach {
                                println(" " + it.date + " " + it.description + "\t" + it.amount)
                            }
                }



        return outSpendingEntries;
    }
}