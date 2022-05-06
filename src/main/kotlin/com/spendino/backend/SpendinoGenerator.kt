package com.spendino.backend

import com.spendino.backend.io.FileSystem
import com.spendino.backend.io.parseStatementFile
import com.spendino.backend.io.writeSpendinoJson
import com.spendino.backend.io.writeToFile
import com.spendino.backend.logic.CategoryMapper
import com.spendino.backend.logic.applyStaticModifications
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class SpendinoGenerator(
    val categoryMapper: CategoryMapper
) : CommandLineRunner {

    override fun run(vararg args: String?) {

        val files = FileSystem(
            month = "2022-04",
            basePath = "C:\\Users\\danie\\Google Drive\\filer\\Spendino\\"
        )

        val cardData = parseStatementFile(files.cardFile(), files.month)
        val bankData = parseStatementFile(files.bankFile(), files.month)

        val spending = categoryMapper.categorize(cardData + bankData)
        applyStaticModifications(spending)

        println(spending)
        println("Attention: Unclassified posts above could not be categorized")

        spending.writeSpendinoJson(files)
        spending.writeToFile(files)
    }
}