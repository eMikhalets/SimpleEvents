package com.emikhalets.simpleevents.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.domain.entity.NotificationEvent
import com.emikhalets.simpleevents.ui.MainActivity

object AppNotificationManager {

    private const val NOTIFICATION_ID_EVENTS = "simple_events.notification.id.events"
    private const val NOTIFICATION_ID_UPDATE_ERROR = "simple_events.notification.id.update_error"

    private const val NOTIFICATION_CHANNEL_ID_EVENTS = "simple_events.notification.channel.events"
    private const val NOTIFICATION_CHANNEL_NAME_EVENTS = "Events notifications"

    fun sendUpdateError(context: Context) {
        val nm = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, NOTIFICATION_ID_UPDATE_ERROR)
            .setSmallIcon(R.drawable.ic_event_available)
            .setContentTitle(context.getString(R.string.notification_update_error))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(getPendingIntent(context))

        nm.createNotificationChannel(getEventsChannel())

        NotificationManagerCompat.from(context).notify(0, builder.build())
    }

    fun sendEventsNotification(context: Context, events: List<NotificationEvent>) {
        val nm = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, NOTIFICATION_ID_EVENTS)
            .setSmallIcon(R.drawable.ic_event_available)
            .setContentTitle(context.getString(R.string.notification_upcoming_events))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(getPendingIntent(context))
            .setAutoCancel(true)

        nm.createNotificationChannel(getEventsChannel())

        val style = NotificationCompat.InboxStyle()
        events.forEach { notificationEvent ->
            style.addLine(context.getString(notificationEvent.notificationTime.nameRes))
            notificationEvent.events.forEach { style.addLine(setEvent(context, it)) }
        }

        builder.setStyle(style)

        NotificationManagerCompat.from(context).notify(1, builder.build())
    }

    private fun setEvent(context: Context, event: EventEntityDB): String {
        val type = event.eventType.nameRes
        val name = event.name
        val turns = context.getString(R.string.notification_turns, event.ageTurns)
        return "$type • $name • $turns"
    }

    fun createNotificationChannels(context: Context) {
        val nm = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(getEventsChannel())
    }

    private fun getEventsChannel(): NotificationChannel {
        return NotificationChannel(
            NOTIFICATION_CHANNEL_ID_EVENTS,
            NOTIFICATION_CHANNEL_NAME_EVENTS,
            NotificationManager.IMPORTANCE_DEFAULT
        )
    }

    private fun getPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        return getActivity(context, 0, intent, pendingFlag)
    }
}