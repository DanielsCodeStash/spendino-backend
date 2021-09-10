package com.spendino.backend

import com.spendino.backend.io.InputFileType
import com.spendino.backend.io.JsonOutputHandler
import com.spendino.backend.io.StatementFileReader
import com.spendino.backend.logic.CategoryMapper
import com.spendino.backend.logic.EntryCategorizer
import com.spendino.backend.logic.StaticDataHandler

fun main() {

    val month = "2021-07"
    val cardFile = "C:\\Projects\\spendino-backend\\backend_data\\card.txt"
    val bankFile = "C:\\Projects\\spendino-backend\\backend_data\\bank.txt"

    val outFile = "C:\\Projects\\spendino-backend\\backend_data\\" + month.replace("-" , "") + ".json";

    val cardStatementEntries = StatementFileReader().parseFile(cardFile, month, InputFileType.CARD)
    val bankStatementEntries = StatementFileReader().parseFile(bankFile, month, InputFileType.BANK)
    val statementEntries = cardStatementEntries + bankStatementEntries

    val spendingEntries = CategoryMapper().map(statementEntries)

    val enrichedEntries = StaticDataHandler().staticDataModify(spendingEntries)

    JsonOutputHandler().writeJson(enrichedEntries, outFile)

    println("Output file written to $outFile")
    println("Attention: These posts could not be categorized")
    enrichedEntries
            .filter { it.category == EntryCategorizer.categoryNeeded}
            .sortedBy { it.date }
            .forEach { println(it) }

}
