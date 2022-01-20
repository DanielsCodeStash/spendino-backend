package com.spendino.backend.data

data class SubCategory(
    val name: String,
    var moneyChange: Int = 0,
    val entries: MutableList<StatementEntry> = mutableListOf()
) {

    fun addSpending(spendingEntry: StatementEntry) {
        moneyChange += spendingEntry.amount
        entries.add(spendingEntry)
    }

    fun toString(indentNum: Int): String {
        var out = indent(indentNum) + "$name $moneyChange\n"
        entries.forEach { out += it.toString(indentNum + 1) + "\n" }
        return out
    }
}