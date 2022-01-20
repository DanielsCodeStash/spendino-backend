package com.spendino.backend.logic

import com.spendino.backend.data.SpendingData
import com.spendino.backend.data.StatementEntry

class StaticDataHandler {

    fun applyStaticModifications(data: SpendingData) {

        val rentPost = data.uncategorized.first { it.description.toLowerCase().contains("cyklisten")}
        data.addSpending(rentPost.copy(amount = -2618), EntryCategorizer.housingCat, "Hyra")
        data.uncategorized.remove(rentPost)


        val loanPost = data.uncategorized.first { it.description.toLowerCase().startsWith("lån") }
        val totalLoanAmount = loanPost.amount
        data.addSpending(loanPost.copy(amount = totalLoanAmount + 3125), EntryCategorizer.housingCat, "Ränta")
        data.uncategorized.remove(loanPost)


        // standard posts
        data.addStaticPost(-3125, EntryCategorizer.housingCat, "Amortering")
        data.addStaticPost(-200, EntryCategorizer.housingCat, "Vatten")
        data.addStaticPost(-350, EntryCategorizer.monthlyCat, "Linser")

    }


    private fun SpendingData.addStaticPost(amount: Int, category: String, subCategory: String) {
        val monthStart = this.categories.first().subCategories.first().entries.first().date.withDayOfMonth(1)
        val statement = StatementEntry(monthStart, subCategory, amount)
        this.addSpending(statement, category, subCategory)
    }
}