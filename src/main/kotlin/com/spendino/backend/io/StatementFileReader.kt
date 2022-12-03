package com.spendino.backend.io

import com.spendino.backend.data.StatementEntry
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun parseStatementFile(file: File, month: String) =
    file
        .readLines()
        .filter { it.startsWith(month) }
        .map { parseLine(it) }

private fun parseLine(line: String): StatementEntry {
    val cols = line
        .replace("\t\t", "\t")
        .split("\t")
        .map { it.trim() }

    val date = LocalDate.parse(cols[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val description = cols[1]
    val amount = parseCurrencyInput(getCurrencyColumnData(cols))
    return StatementEntry(date, description, amount)
}

private fun getCurrencyColumnData(cols: List<String>) =
    cols
        .subList(2, cols.size) // it's not either of the first two columns
        .first { it.contains(Regex("[0-9],[0-9]")) } // it's always the first number column

private fun parseCurrencyInput(input: String) =
    input
        .substring(0, input.indexOf(',')) // ignore cents
        .replace(" ", "") // remove a thousand separator
        .replace("\u00A0","") // remove non-breaking space used as a thousand separator
        .replace("−", "-") // replace dash with minus
        .toInt()

