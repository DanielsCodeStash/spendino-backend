package com.spendino.backend.logic

import com.spendino.backend.data.SpendingEntry
import com.spendino.backend.data.StatementEntry



class EntryCategorizer {

    companion object {
        const val categoryNeeded = "<needs classification>"

        const val housingCat = "Bostad"
        const val monthlyCat = "Fasta utgift"
        const val oneTime = "One time"
        const val base = "Base"
    }

    private val ignore = hashSetOf("entercard group ab", "lön", "inbetalning bankgiro", "netlens.se", "Pension", "skandia liv", "56990473967")

    private val straightMatches = hashMapOf(
            "gbg energi" to Pair(housingCat, "EL"),
            "göteborg energi" to Pair(housingCat, "EL"),
            "dinel" to Pair(housingCat, "EL"),
            "csn" to Pair(monthlyCat, "CSN"),
            "Akad.a-kassa" to Pair(monthlyCat, "Akassa"),
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
            "rusta backaplan" to Pair(base, "Mat och bas"),
            "salong live" to Pair(base, "Klippning"),
            "Live Salong" to Pair(base, "Klippning"),
            "Barbersh" to Pair(base, "Klippning"),
            "ica supermarket" to Pair(base, "Mat och bas"),
            "sannegardens" to Pair(base, "Mat ute"),
            "restaurang istanbul" to Pair(base, "Mat ute"),
            "systembolaget" to Pair(base, "Öl"),
            "sushi tiden" to Pair(base, "Mat ute"),
            "DA BELLO ENOTECA" to Pair(base, "Mat ute"),
            "GOOGLE STORAGE" to Pair(monthlyCat, "Streaming"),
            "discovery+" to Pair(monthlyCat, "Streaming"),
            "FOOD LAB" to Pair(base, "Mat ute"),
            "SCHNITZELPLATZ" to Pair(base, "Öl"),
            "Kafe Alkemiste" to Pair(base, "Mat ute"),
            "ZAMENHOF" to Pair(base, "Öl"),
            "GYMGROSSISTEN" to Pair(base, "Gym"),
            "FILMSTADEN" to Pair(oneTime, "Bio"),
            "INDISKA" to Pair(base, "Mat ute"),
            "Apotek" to Pair(base, "Mat och bas"),
            "apohem" to Pair(base, "Mat och bas"),
            "Apotea" to Pair(base, "Mat och bas"),
            "HELLOFRESH" to Pair(base, "Mat och bas"),
            "GOTEBORGS ORIEN" to Pair(base, "Mat och bas"),
            "ZOZAKI JAPAN" to Pair(base, "Mat ute"),
            "BURGER KING" to Pair(base, "Mat ute"),
            "SUBWAY" to Pair(base, "Mat ute"),
            "FLYING BARELL" to Pair(base, "Mat ute")



    )


    fun categorize(statementEntry: StatementEntry) : SpendingEntry? {

        val description = statementEntry.description.trim().toLowerCase()
        val date = statementEntry.date;


        if(ignore.any { description.contains(it, true) }) {
            return null
        }

        if(description == "brf cyklisten") {
            return SpendingEntry(date, housingCat, "Hyra", -2618)
        } else if(description.startsWith("lån")) {
            return SpendingEntry(date, housingCat, "Ränta", statementEntry.amount + 3125)
        }

        for((matchString, categories) in straightMatches) {
            if(description.contains(matchString, true)) {
                return SpendingEntry(date, categories.first, categories.second, statementEntry.amount)
            }
        }

        return SpendingEntry(date, categoryNeeded, statementEntry.description, statementEntry.amount)
    }


}