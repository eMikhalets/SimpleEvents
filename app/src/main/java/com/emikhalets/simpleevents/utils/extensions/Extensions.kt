package com.emikhalets.simpleevents.utils.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.model.EventModel

fun toast(context: Context, @StringRes messageRes: Int) {
    Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show()
}

fun EventModel.calculateEventData(): EventModel {
    return this.apply {
//        age = date.turns
//        days = date.daysLeft
    }
}

@Composable
fun EventModel.formatHomeInfo(): String {
    val date = date.formatDateThisYear("EE, d MMM")
    val type = stringResource(eventType.nameRes)
    val turns = stringResource(R.string.event_list_item_turns, age)
    return if (age == 0 || withoutYear) "$date • $type" else "$date • $type • $turns"
}

fun EventModel.formatNotificationInfo(context: Context): String {
    val type = context.getString(eventType.nameRes)
    val turns = context.getString(R.string.notification_turns, age)
    return if (age == 0 || withoutYear) "$type • $name" else "$type • $name • $turns"
}
