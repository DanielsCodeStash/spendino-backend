package com.spendino.backend

import com.spendino.backend.io.JsonOutputHandler
import com.spendino.backend.io.parseStatementFile
import com.spendino.backend.logic.CategoryMapper
import com.spendino.backend.logic.EntryCategorizer
import com.spendino.backend.logic.StaticDataHandler
import com.spendino.backend.data.GeneratorConfig
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component


@Component
class SpendinoGenerator(
    val categoryMapper: CategoryMapper
) : CommandLineRunner {

    override fun run(vararg args: String?) {

        val month = "2021-12"
        val pathPrefix = "C:\\Daniel\\projects\\spendino-backend\\backend_data\\"

        val cardFile = pathPrefix + "card.txt"
        val bankFile = pathPrefix + "bank.txt"
        val jsonFile = pathPrefix + month.replace("-" , "") + ".json";
        val categoryFile = pathPrefix + month.replace("-" , "") + "_raw.txt";

        val cardStatementEntries = parseStatementFile(cardFile, month)
        val bankStatementEntries = parseStatementFile(bankFile, month)
        val statementEntries = cardStatementEntries + bankStatementEntries

        val spendingEntries = categoryMapper.map(statementEntries)
        val enrichedEntries = StaticDataHandler().staticDataModify(spendingEntries)

        JsonOutputHandler().writeJson(enrichedEntries, jsonFile)

        println("JSON file written to $jsonFile")
        println("Raw category file written to $categoryFile")
        println("Attention: These posts could not be categorized")
        enrichedEntries
            .filter { it.category == EntryCategorizer.categoryNeeded}
            .sortedBy { it.date }
            .forEach { println(it) }

    }
}