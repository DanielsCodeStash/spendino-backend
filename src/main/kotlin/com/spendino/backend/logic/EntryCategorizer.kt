package com.spendino.backend.logic

import com.spendino.backend.data.SpendingEntry
import com.spendino.backend.data.StatementEntry

class EntryCategorizer {

    private val housingCat = "Bostad"
    private val monthlyCat = "Fasta utgift"
    private val oneTime = "oneTime"
    private val base = "Base"

    private val ignore = hashSetOf("entercard group ab", "lön", "inbetalning bankgiro", "netlens.se")

    private val straightMatches = hashMapOf(
            "gbg energi" to Pair(housingCat, "EL"),
            "göteborg energi" to Pair(housingCat, "EL"),
            "csn" to Pair(monthlyCat, "CSN"),
            "aea" to Pair(monthlyCat, "Akassa"),
            "folksam" to Pair(monthlyCat, "Försäkring"),
            "frisktandv" to Pair(monthlyCat, "Tandvård"),
            "enkla vardag" to Pair(monthlyCat, "Bank"),
            "västtrafik a" to Pair(monthlyCat, "Västtrafik"),
            "hallon.se" to Pair(monthlyCat, "Telefon"),
            "spotify" to Pair(monthlyCat, "Streaming"),
            "netflix.com" to Pair(monthlyCat, "Streaming"),
            "hbo nordic" to Pair(monthlyCat, "Streaming"),
            "audible" to Pair(monthlyCat, "Streaming"),
            "steampowered" to Pair(oneTime, "Datorspel"),
            "stora coop backaplan" to Pair(base, "Mat och bas"),
            "willys" to Pair(base, "Mat och bas"),
            "ica nära" to Pair(base, "Mat och bas"),
            "ica nara" to Pair(base, "Mat och bas"),
            "ica supermarket" to Pair(base, "Mat och bas"),
            "sannegardens" to Pair(base, "Mat ute"),
            "systembolaget" to Pair(base, "Öl"),
            "sushi tiden" to Pair(base, "Mat ute")

    )


    fun categorize(statementEntry: StatementEntry) : SpendingEntry? {

        val description = statementEntry.description.trim().toLowerCase()

        if(ignore.any { description.contains(it) }) {
            return null
        }

        if(description == "brf cyklisten") {
            return SpendingEntry(housingCat, "Hyra", -2618)
        } else if(description.startsWith("lån")) {
            return SpendingEntry(housingCat, "Ränta", statementEntry.amount + 2618)
        }

        for((matchString, categories) in straightMatches) {
            if(description.contains(matchString)) {
                return SpendingEntry(categories.first, categories.second, statementEntry.amount)
            }
        }

        return SpendingEntry("<needs classification>", statementEntry.description, statementEntry.amount)
    }


}