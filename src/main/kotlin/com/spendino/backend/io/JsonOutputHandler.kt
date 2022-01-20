package com.spendino.backend.io

import com.spendino.backend.logic.EntryCategorizer
import java.io.File

class JsonOutputHandler {

//    val foreword = "[\n";
//    val prologue = "]"
//
//    var out = "";
//
//    fun writeJson(statements: List<SpendingEntry>, outFile: String) {
//
//        val file = File(outFile)
//
//        out += foreword
//        writeCategory(file, EntryCategorizer.housingCat, statements)
//        out += "\n"
//        writeCategory(file, EntryCategorizer.monthlyCat, statements)
//        out += "\n"
//        writeCategory(file, EntryCategorizer.base, statements)
//        out += "\n"
//        writeCategory(file, EntryCategorizer.oneTime, statements)
//
//        out += prologue
//
//        // remove last ","
//        out = out.removeRange(out.lastIndexOf(','), out.lastIndexOf(',')+1)
//
//        file.writeText(out)
//    }
//
//    private fun writeCategory(file : File, category : String, statements: List<SpendingEntry>) {
//        statements
//                .filter {it.category == category}
//                .forEach { writeEntry(file, it) }
//    }
//
//    private fun writeEntry(file : File, entry : SpendingEntry) {
//
//        var entryOut = "\t{ \"g1\": \""
//        entryOut += entry.category
//        entryOut += "\", \"g2\": \""
//        entryOut += entry.subCategory
//        entryOut += "\", \"value\": "
//        entryOut += entry.amount
//        entryOut += " },\n"
//
//        out += entryOut;
//    }
}