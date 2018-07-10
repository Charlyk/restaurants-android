package io.dotcoding.software.restaurante.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager


/**
 * Created by Eduard Albu on 04 Апрель 2018.
 * For project: figaro-android
 * Copyright (c) 2018. Fabity.co
 */
fun Int.toPx(): Int {
    val res = RApplication.instance.resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), res.displayMetrics).toInt()
}

fun Activity.displaySize(): Pair<Int, Int> {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    val height = displayMetrics.heightPixels
    val width = displayMetrics.widthPixels
    return Pair(width, height)
}
