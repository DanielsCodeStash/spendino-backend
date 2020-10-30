package com.spendino.backend.io

import com.spendino.backend.data.StatementEntry
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StatementFileReader {

    fun parseFile(filePath: String, month : String, fileType: InputFileType): List<StatementEntry> {

        return File(filePath)
                .readLines()
                .filter { it.startsWith(month) }
                .map { line -> if(fileType == InputFileType.CARD)  parseCardLine(line) else parseBankLine(line) }
                .toList();
    }

    private fun parseBankLine(line: String): StatementEntry {

        val cols = line.replace("\t\t", "\t").split("\t")

        val date = LocalDate.parse(cols[0].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val addedAmount = cols[2];
        val subtractedAmount = cols[3];
        val amountString = if (addedAmount.isBlank()) subtractedAmount else addedAmount
        val amount = parseCurrencyInput(amountString)

        return StatementEntry(date, cols[1], amount);
    }

    private fun parseCardLine(line: String): StatementEntry {

        val cols = line.replace("\t\t", "\t").split("\t")

        val date = LocalDate.parse(cols[0].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val amount = parseCurrencyInput(cols[4])

        return StatementEntry(date, cols[1], amount);
    }

    private fun parseCurrencyInput(input : String) : Int {
        var amount = input.substring(0, input.indexOf(','))
        amount = amount.replace(" ", "");
        amount = amount.replace("−", "-")
        return amount.toInt();
    }


}