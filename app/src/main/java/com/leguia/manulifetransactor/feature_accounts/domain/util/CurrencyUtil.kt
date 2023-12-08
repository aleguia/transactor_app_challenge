package com.leguia.manulifetransactor.feature_accounts.domain.util

import android.icu.number.Notation
import android.icu.number.NumberFormatter
import android.icu.util.Currency
import java.util.Locale

/*
* Created by Fernando Leguia on December 07, 2023
*/

fun Double.formatCurrency(): String = NumberFormatter.with()
    .notation(Notation.simple())
    .unit(Currency.getInstance("CAD"))
//    .precision(Precision.maxSignificantDigits(2))
    .locale(Locale.CANADA)
    .format(this)
    .toString()