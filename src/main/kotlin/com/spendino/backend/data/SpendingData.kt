package com.spendino.backend.data

data class SpendingData(
    val uncategorized: MutableList<SpendingEntry> = mutableListOf(),
    val categories: MutableList<Category> = mutableListOf(),
    var totalSpent: Int = 0
) {
    fun addSpending(spendingEntry: SpendingEntry, categoryName: String, subCategoryName: String) {

        val categoryExists = categories.any { it.name == categoryName }
        if(!categoryExists) {
            categories.add(Category(categoryName))
        }
        val category = categories.first { it.name == categoryName }

        totalSpent += spendingEntry.amount
        category.addSpending(spendingEntry, subCategoryName)
    }

}