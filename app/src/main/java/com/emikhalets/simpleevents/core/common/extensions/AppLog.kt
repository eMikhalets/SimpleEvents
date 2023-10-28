package com.emikhalets.simpleevents.core.common.extensions

object AppLog {

    var isInitialized: Boolean = false
        private set

    const val DefaultTag: String = "EventsAppLog"

    fun initialize() {
        isInitialized = true
    }
}
