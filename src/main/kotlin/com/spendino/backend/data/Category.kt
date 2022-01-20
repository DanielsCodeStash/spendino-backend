package com.spendino.backend.data

data class Category(
    val name: String,
    var moneyChange: Int = 0,
    var subCategories: MutableList<SubCategory> = mutableListOf()) {

    fun addSpending(spendingEntry: StatementEntry, subCategoryName: String) {

        val subCategoryExists = subCategories.any { it.name == subCategoryName }
        if(!subCategoryExists) {
            subCategories.add(SubCategory(subCategoryName))
        }

        val subCategory = subCategories.first { it.name == subCategoryName }

        moneyChange += spendingEntry.amount
        subCategory.addSpending(spendingEntry)
    }

    fun toString(indentNum: Int): String {
        var out = indent(indentNum) + "$name $moneyChange\n"
        subCategories.forEach { out += it.toString(indentNum + 1) }
        return out
    }


}