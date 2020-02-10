package com.juandavid.balances.utils

import kotlin.math.absoluteValue
import kotlin.math.pow

object Format {
    fun currency(value: Int): String {
        val absValue: Int = value.absoluteValue
        var numStr: String = absValue.toString()
        val sign = if (value >= 0) "$" else "$-"

        numStr = partialFormat(absValue, numStr, 6, "'")
        numStr = partialFormat(absValue, numStr, 3, ",")

        return sign + numStr
    }

    private fun partialFormat(
        value: Int,
        numStr: String,
        position: Int,
        separator: String
    ): String {
        return if (value >= 10.0.pow(position.toDouble())) {
            (numStr.substring(0, numStr.length - position)
                    + separator
                    + numStr.substring(numStr.length - position, numStr.length))
        } else {
            numStr
        }
    }
}