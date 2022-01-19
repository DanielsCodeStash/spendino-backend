package com.spendino.backend.data

data class Category(
    val name: String,
    var totalSpent: Int = 0,
    var subCategories: MutableList<SubCategory> = mutableListOf()) {

    fun addSpending(spendingEntry: SpendingEntry, subCategoryName: String) {

        val subCategoryExists = subCategories.any { it.name == subCategoryName }
        if(!subCategoryExists) {
            subCategories.add(SubCategory(subCategoryName))
        }

        val subCategory = subCategories.first { it.name == subCategoryName }

        totalSpent += spendingEntry.amount
        subCategory.addSpending(spendingEntry)
    }
}