package com.spendino.backend

import com.spendino.backend.io.InputFileType
import com.spendino.backend.io.StatementFileReader
import com.spendino.backend.logic.CategoryMapper

fun main() {

    val month = "2019-12"
    val cardFile = "C:\\Daniel\\other_projects\\spendino-backend\\backend_data\\card.txt"
    val bankFile = "C:\\Daniel\\other_projects\\spendino-backend\\backend_data\\bank.txt"

    val cardStatementEntries = StatementFileReader().parseFile(cardFile, month, InputFileType.CARD)
    val bankStatementEntries = StatementFileReader().parseFile(bankFile, month, InputFileType.BANK)
    val statementEntries = cardStatementEntries + bankStatementEntries

    val spendingEntries = CategoryMapper().map(statementEntries)

    spendingEntries.forEach { println(it) }

}
