package com.spendino.backend.data

import java.time.LocalDate

data class SpendingEntry(val date : LocalDate, val category: String, val subCategory: String, var amount : Int)
{
    override fun toString(): String = "$date | $category | $subCategory | $amount"
}