package com.spendino.backend.io

import com.spendino.backend.data.StatementEntry
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun main() {

    println(StatementReader().parseFile("C:\\Daniel\\other_projects\\spendino-backend\\backend_data\\seb.txt"))

}

class StatementReader {

    fun parseFile(filePath: String): List<StatementEntry> {

        return File(filePath)
                .readLines()
                .map { line -> parseLine(line) }
                .toList();

    }

    private fun parseLine(line: String): StatementEntry {

        val cols = line.split("\t")

        //println(line)

        val date = LocalDate.parse(cols[0].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val addedAmount = cols[2];
        val subtractedAmount = cols[3];

        var amount = if (addedAmount.isBlank()) subtractedAmount else addedAmount
        amount = amount.substring(0, amount.indexOf(','))
        amount = amount.replace(" ", "");
        amount = amount.replace("−", "-")

        return StatementEntry(date, cols[1], amount.toInt());
    }
}