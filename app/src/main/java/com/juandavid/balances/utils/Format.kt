package com.juandavid.balances.utils

import kotlin.math.absoluteValue

object Format {
    fun currency(value: Int): String {
        val absValue: Int = value.absoluteValue
        var numStr: String = absValue.toString()
        val sign = if (value >= 0) "$" else "$-"

        if (absValue >= 1000000) {
            numStr = (numStr.substring(0, numStr.length - 6)
                    + "'"
                    + numStr.substring(numStr.length - 6, numStr.length))
        }

        if (absValue >= 1000) {
            numStr = (numStr.substring(0, numStr.length - 3)
                    + ","
                    + numStr.substring(numStr.length - 3, numStr.length))
        }
        return sign + numStr
    }
}