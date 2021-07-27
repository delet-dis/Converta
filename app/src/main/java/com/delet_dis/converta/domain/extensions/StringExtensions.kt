package com.delet_dis.converta.domain.extensions

fun String.beautifyString() =
    (this as CharSequence).trim().toString()