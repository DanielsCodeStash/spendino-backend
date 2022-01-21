package com.spendino.backend.logic

import com.spendino.backend.data.SpendingData
import com.spendino.backend.data.StatementEntry

fun applyStaticModifications(data: SpendingData) {

    // rent
    val rawRentPost = data.extractMatchingUncategorizedPost("cyklisten")
    val rentPost = rawRentPost.copy(amount = -2618)
    data.addSpending(rentPost, EntryCategorizer.housingCat, "Hyra")

    // interest payment
    val rawLoanPost = data.extractMatchingUncategorizedPost("lån")
    val loanPost = rawLoanPost.copy(amount = (rawLoanPost.amount + 3125))
    data.addSpending(loanPost, EntryCategorizer.housingCat, "Ränta")

    // standard posts
    data.addStaticPost(-3125, EntryCategorizer.housingCat, "Amortering")
    data.addStaticPost(-200, EntryCategorizer.housingCat, "Vatten")
    data.addStaticPost(-350, EntryCategorizer.monthlyCat, "Linser")

}

private fun SpendingData.extractMatchingUncategorizedPost(description: String): StatementEntry {
    val post = this.uncategorized.first { it.description.toLowerCase().startsWith(description)}
    this.uncategorized.remove(post)
    return post
}

private fun SpendingData.addStaticPost(amount: Int, category: String, subCategory: String) {
    val monthStart = this.categories.first().subCategories.first().entries.first().date.withDayOfMonth(1)
    val statement = StatementEntry(monthStart, subCategory, amount)
    this.addSpending(statement, category, subCategory)
}