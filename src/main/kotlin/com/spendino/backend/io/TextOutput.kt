package com.spendino.backend.io

import com.spendino.backend.data.SpendingData
import java.io.File

fun SpendingData.writeToFile(files: FileSystem) {

    val outPath = files.getNewFilePath("_raw.txt")
    File(outPath).writeText(this.toString())
    println("Raw category file written to $outPath")

}