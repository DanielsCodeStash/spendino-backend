package com.spendino.backend

import com.spendino.backend.io.JsonOutputHandler
import com.spendino.backend.io.parseStatementFile
import com.spendino.backend.logic.CategoryMapper
import com.spendino.backend.logic.applyStaticModifications
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class SpendinoGenerator(
    val categoryMapper: CategoryMapper
) : CommandLineRunner {

    override fun run(vararg args: String?) {

        val month = "2022-01"
        val pathPrefix = "C:\\Daniel\\Projects\\spendino-backend\\backend_data\\"

        val cardFile = pathPrefix + "card.txt"
        val bankFile = pathPrefix + "bank.txt"
        val jsonFile = pathPrefix + month.replace("-" , "") + ".json";
        val categoryFile = pathPrefix + month.replace("-" , "") + "_raw.txt";

        val cardData = parseStatementFile(cardFile, month)
        val bankData = parseStatementFile(bankFile, month)

        val spending = categoryMapper.categorize(cardData + bankData)
        applyStaticModifications(spending)

        JsonOutputHandler().writeJson(spending, jsonFile)

        println(spending)
        println("JSON file written to $jsonFile")
        println("Raw category file written to $categoryFile")
        println("Attention: Unclassifed posts above  could not be categorized")

    }
}