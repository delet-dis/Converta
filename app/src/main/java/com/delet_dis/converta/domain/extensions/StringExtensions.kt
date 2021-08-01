package com.delet_dis.converta.domain.extensions

import java.util.*

fun String.beautifyString() =
    (this as CharSequence).trim().toString().replace("\\s+", " ")

fun String.splitBySentences(): String {
    var resultString = ""

    this.split(".").forEach { string ->
        resultString += " "
        resultString += (string.trim().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        })
    }

    return resultString
}
