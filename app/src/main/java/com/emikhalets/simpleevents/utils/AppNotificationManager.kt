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
import com.emikhalets.simpleevents.domain.entity.NotificationEvent
import com.emikhalets.simpleevents.ui.MainActivity
import com.emikhalets.simpleevents.utils.extensions.formatNotificationInfo

object AppNotificationManager {

    private const val NOTIFICATION_ID_EVENTS = 123

    private const val CHANNEL_ID_EVENTS = "simple_events.notification.channel.events"
    private const val CHANNEL_NAME_EVENTS = "Events notifications"

    fun sendEventsNotification(context: Context, events: List<NotificationEvent>) {
        val nm = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, CHANNEL_ID_EVENTS)
            .setSmallIcon(R.drawable.ic_event_available)
            .setContentTitle(context.getString(R.string.notification_upcoming_events))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(getPendingIntent(context))
            .setAutoCancel(true)

        nm.createNotificationChannel(getEventsChannel())

        val style = NotificationCompat.InboxStyle()
        events.forEach { notificationEvent ->
            style.addLine(notificationEvent.notificationTime.nameEn)
            notificationEvent.events.forEach { style.addLine(it.formatNotificationInfo(context)) }
        }

        builder.setStyle(style)

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID_EVENTS, builder.build())
    }

    fun createNotificationChannels(context: Context) {
        val nm = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(getEventsChannel())
    }

    private fun getEventsChannel(): NotificationChannel {
        return NotificationChannel(
            CHANNEL_ID_EVENTS,
            CHANNEL_NAME_EVENTS,
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