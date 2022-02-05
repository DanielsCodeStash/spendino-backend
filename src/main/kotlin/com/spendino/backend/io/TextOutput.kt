package com.spendino.backend.io

import com.spendino.backend.data.SpendingData
import java.io.File

fun SpendingData.writeToFile(filePath: String) {
    File(filePath).writeText(this.toString())
}