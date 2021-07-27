package com.delet_dis.converta.domain.extensions

import android.content.Context
import android.util.DisplayMetrics

fun Float.pxToDp(context: Context) =
    this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

fun Int.dpToPx(context: Context) =
    this * context.resources.displayMetrics.density
