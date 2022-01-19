package com.spendino.backend.data

import java.time.LocalDate

data class SpendingEntry(val date : LocalDate, val description: String, var amount : Int)