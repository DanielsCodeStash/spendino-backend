package com.spendino.backend.data

data class SpendingEntry(val category: String, val subCategory: String, var amount : Int)
{
    override fun toString(): String = "$category | $subCategory | $amount"
}