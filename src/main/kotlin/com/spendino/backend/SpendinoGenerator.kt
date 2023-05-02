package com.spendino.backend

import com.spendino.backend.io.FileSystem
import com.spendino.backend.io.parseStatementFile
import com.spendino.backend.io.writeSpendinoJson
import com.spendino.backend.io.writeToFile
import com.spendino.backend.logic.CategoryMapper
import com.spendino.backend.logic.SavingsHandler
import com.spendino.backend.logic.applyStaticModifications
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class SpendinoGenerator(
    val categoryMapper: CategoryMapper,
    val savingHandler: SavingsHandler
) : CommandLineRunner {

    override fun run(vararg args: String?) {

        val askForSavings = true

        val files = FileSystem(
            month = "2023-04",
            basePath = "C:\\Users\\danie\\My Drive\\filer\\Spendino\\"
        )

        if (askForSavings) savingHandler.fetchInput()

        val cardData = parseStatementFile(files.cardFile(), files.month)
        val bankData = parseStatementFile(files.bankFile(), files.month)

        val spending = categoryMapper.categorize(cardData + bankData)
        applyStaticModifications(spending, savingHandler)

        println(spending)
        println("Attention: Unclassified posts above could not be categorized")

        spending.writeSpendinoJson(files)
        spending.writeToFile(files)
    }
}