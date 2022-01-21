package com.spendino.backend.data

data class SpendingData(
    val uncategorized: MutableList<StatementEntry> = mutableListOf(),
    val categories: MutableList<Category> = mutableListOf(),
    var moneyChange: Int = 0
) {
    fun addSpending(spendingEntry: StatementEntry, categoryName: String, subCategoryName: String) {

        val categoryExists = categories.any { it.name == categoryName }
        if(!categoryExists) {
            categories.add(Category(categoryName))
        }
        val category = categories.first { it.name == categoryName }

        moneyChange += spendingEntry.amount
        category.addSpending(spendingEntry, subCategoryName)
    }

    fun getCategory(name: String): Category {
        return categories.first { it.name == name }
    }

    override fun toString(): String {
        var out = "> Spending data $moneyChange kr\n"
        categories.forEach { out += it.toString(0) + "\n"}
        out += "\n> Unclassified\n"
        uncategorized.forEach { out += it.toString(0) + "\n" }
        return out
    }

}