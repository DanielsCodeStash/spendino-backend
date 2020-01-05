package com.spendino.backend.data

import java.time.LocalDate

data class StatementEntry(val date : LocalDate, val description : String, val amount : Int)