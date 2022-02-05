package com.spendino.backend.io

import com.spendino.backend.data.Category
import com.spendino.backend.data.SpendingData
import com.spendino.backend.data.SubCategory
import com.spendino.backend.logic.EntryCategorizer
import java.io.File
import kotlin.math.absoluteValue

fun SpendingData.writeSpendinoJson(outPath: String) {

    var out = "[\n"

    listOf(
        EntryCategorizer.housingCat,
        EntryCategorizer.monthlyCat,
        EntryCategorizer.base,
        EntryCategorizer.oneTime
    ).forEach {
        out += getCategoryJson(getCategory(it)) + "\n"
    }

    // remove last ","
    out = out.removeRange(out.lastIndexOf(','), out.lastIndexOf(',') + 1)

    out += "]"

    File(outPath).writeText(out)
}

private fun getCategoryJson(category: Category): String {

    return category.subCategories
        .joinToString(separator = "") { subCategory ->
            val totalAmount = subCategory.entries.sumBy { it.amount }
            getEntryJson(totalAmount, category, subCategory)
        }
}

private fun getEntryJson(amount: Int, category: Category, subCategory: SubCategory): String {

    var entryOut = "\t{ \"g1\": \""
    entryOut += category.name
    entryOut += "\", \"g2\": \""
    entryOut += subCategory.name
    entryOut += "\", \"value\": "
    entryOut += amount.absoluteValue
    entryOut += " },\n"

    return entryOut
}