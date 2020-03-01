package com.example.buscalc

import android.widget.EditText
import kotlin.math.round

fun EditText.validateInt() = text.toString().toIntOrNull()
fun EditText.validateFloat() = text.toString().toFloatOrNull()
fun Float.roundToTwoDecimals() = round(this * 100) / 100