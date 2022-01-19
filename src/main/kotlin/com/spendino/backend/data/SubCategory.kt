package com.spendino.backend.data

data class SubCategory(
    val name: String,
    var totalSpent: Int = 0,
    val entries: MutableList<SpendingEntry> = mutableListOf()
) {

    fun addSpending(spendingEntry: SpendingEntry) {
        totalSpent += spendingEntry.amount
        entries.add(spendingEntry)
    }
}