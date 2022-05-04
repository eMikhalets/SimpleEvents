package com.emikhalets.simpleevents.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatDate(pattern: String): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)