package com.spendino.backend.logic

import com.spendino.backend.data.SpendingData
import com.spendino.backend.data.StatementEntry

fun applyStaticModifications(data: SpendingData, savingHandler: SavingsHandler) {

    // rent
    val rawRentPost = data.extractMatchingUncategorizedPost("cyklisten")
    val rentPost = rawRentPost.copy(amount = -2749)
    data.addSpending(rentPost, EntryCategorizer.housingCat, "Rent")

    // interest payment
    val rawLoanPosts = data.extractAllMatchingUncategorizedPosts("lån")
    val totalLoanCost = rawLoanPosts.sumBy { it.amount }
    val amortizationSmall = 68
    val amortizationBig = 3125
    val totalAmortization = amortizationBig + amortizationSmall
    val loanPost = rawLoanPosts.first().copy(description = "Two loans", amount = totalLoanCost + totalAmortization)
    data.addSpending(loanPost, EntryCategorizer.housingCat, "Interest")

    // standard posts
    data.addStaticPost(-totalAmortization, EntryCategorizer.saving, "Amortization")
    data.addStaticPost(-200, EntryCategorizer.housingCat, "Water")

    // insurance
    data.addStaticPost(608, EntryCategorizer.transport, "Car insurance")
    data.addStaticPost(180, EntryCategorizer.housingCat, "Home insurance")

    // savings
    data.addStaticPost(savingHandler.daniel, EntryCategorizer.saving, "D")
    data.addStaticPost(savingHandler.lyrin, EntryCategorizer.saving, "L")

    // badminton modification (almost always shared cost)
    data.extractAllMatchingUncategorizedPosts("MATCHi")
        .map { it.copy(amount = it.amount / 2 ) }
        .forEach { data.addSpending(it, EntryCategorizer.spending, "Badminton")}

}

private fun SpendingData.extractMatchingUncategorizedPost(description: String): StatementEntry {
    val post = this.uncategorized.first { it.description.toLowerCase().startsWith(description.toLowerCase())}
    this.uncategorized.remove(post)
    return post
}

private fun SpendingData.extractAllMatchingUncategorizedPosts(description: String): List<StatementEntry> {
    val posts = this.uncategorized.filter { it.description.toLowerCase().startsWith(description.toLowerCase())}
    posts.forEach { this.uncategorized.remove(it) }
    return posts
}

private fun SpendingData.addStaticPost(amount: Int, category: String, subCategory: String) {
    val monthStart = this.categories.first().subCategories.first().entries.first().date.withDayOfMonth(1)
    val statement = StatementEntry(monthStart, subCategory, amount)
    this.addSpending(statement, category, subCategory)
}