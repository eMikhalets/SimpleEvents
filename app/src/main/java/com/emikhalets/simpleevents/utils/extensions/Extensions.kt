package com.emikhalets.simpleevents.utils.extensions

import com.emikhalets.simpleevents.data.database.EventEntityDB

const val DEFAULT_UPDATE_EVENTS_HOUR = 0
const val DEFAULT_UPDATE_EVENTS_MINUTE = 0
const val DEFAULT_EVENTS_HOUR = 9
const val DEFAULT_EVENTS_MINUTE = 0

fun EventEntityDB.calculateEventData(): EventEntityDB {
    return this.copy(
        ageTurns = date.turns,
        daysCount = date.daysLeft
    )
}