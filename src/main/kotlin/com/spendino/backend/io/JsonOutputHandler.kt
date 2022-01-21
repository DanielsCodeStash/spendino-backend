package com.spendino.backend.io

import com.spendino.backend.data.Category
import com.spendino.backend.data.SpendingData
import com.spendino.backend.data.StatementEntry
import com.spendino.backend.data.SubCategory
import com.spendino.backend.logic.EntryCategorizer
import java.io.File
import kotlin.math.absoluteValue

class JsonOutputHandler {

    val foreword = "[\n";
    val prologue = "]"

    var out = "";

    fun writeJson(data: SpendingData, outFile: String) {

        val file = File(outFile)

        out += foreword
        writeCategory(data.getCategory(EntryCategorizer.housingCat))
        out += "\n"
        writeCategory(data.getCategory(EntryCategorizer.monthlyCat))
        out += "\n"
        writeCategory(data.getCategory(EntryCategorizer.base))
        out += "\n"
        writeCategory(data.getCategory(EntryCategorizer.oneTime))

        out += prologue

        // remove last ","
        out = out.removeRange(out.lastIndexOf(','), out.lastIndexOf(',')+1)

        file.writeText(out)
    }

    private fun writeCategory(category: Category) {

        category.subCategories.forEach { subCategory ->
            val totalAmount = subCategory.entries.sumBy { it.amount }
            writeEntry(totalAmount, category, subCategory)
        }
    }

    private fun writeEntry(amount: Int, category: Category, subCategory: SubCategory) {

        var entryOut = "\t{ \"g1\": \""
        entryOut += category.name
        entryOut += "\", \"g2\": \""
        entryOut += subCategory.name
        entryOut += "\", \"value\": "
        entryOut += amount.absoluteValue
        entryOut += " },\n"

        out += entryOut;
    }
}