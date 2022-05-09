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
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.ui.MainActivity

object AppNotificationManager {

    const val DATA_NOTIFICATION_MONTH = "worker_notif_month"
    const val DATA_NOTIFICATION_WEEK = "worker_notif_week"
    const val DATA_NOTIFICATION_TWO_DAY = "worker_notif_two_day"
    const val DATA_NOTIFICATION_DAY = "worker_notif_day"
    const val DATA_NOTIFICATION_TODAY = "worker_notif_today"

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            nm.createNotificationChannel(getEventsChannel())

        NotificationManagerCompat.from(context).notify(0, builder.build())
    }

//    fun sendEvents(context: Context, events: HashMap<String, List<Event>>) {
//        val nm = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//
//        val notification = NotificationCompat.Builder(context, ID_EVENTS)
//            .setSmallIcon(R.drawable.ic_calendar)
//            .setContentTitle(context.getString(R.string.notification_title))
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setAutoCancel(true)
//
//        notification.setPendingIntent(context, 1)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//            notificationManager.setNotificationChannel(notification, ID_EVENTS, NAME_EVENTS)
//
//        val style = NotificationCompat.InboxStyle()
//        events[DATA_NOTIFICATION_TODAY]?.let { list ->
//            if (list.isNotEmpty()) {
//                style.addLine(context.getString(R.string.notification_text_today))
//                list.forEach { style.addLine(setEvent(context, it)) }
//            }
//        }
//        events[DATA_NOTIFICATION_DAY]?.let { list ->
//            if (list.isNotEmpty()) {
//                style.addLine(context.getString(R.string.notification_text_day))
//                list.forEach { style.addLine(setEvent(context, it)) }
//            }
//        }
//        events[DATA_NOTIFICATION_TWO_DAY]?.let { list ->
//            if (list.isNotEmpty()) {
//                style.addLine(context.getString(R.string.notification_text_two_day))
//                list.forEach { style.addLine(setEvent(context, it)) }
//            }
//        }
//        events[DATA_NOTIFICATION_WEEK]?.let { list ->
//            if (list.isNotEmpty()) {
//                style.addLine(context.getString(R.string.notification_text_week))
//                list.forEach { style.addLine(setEvent(context, it)) }
//            }
//        }
//        events[DATA_NOTIFICATION_MONTH]?.let { list ->
//            if (list.isNotEmpty()) {
//                style.addLine(context.getString(R.string.notification_text_month))
//                list.forEach { style.addLine(setEvent(context, it)) }
//            }
//        }
//        notification.setStyle(style)
//
//        NotificationManagerCompat.from(context).notify(1, notification.build())
//    }
//
//    private fun setEvent(context: Context, event: Event): String {
//        var string = context.getString(
//            when (event.eventType) {
//                EventType.ANNIVERSARY.value -> R.string.anniversary
//                EventType.BIRTHDAY.value -> R.string.birthday
//                else -> 0
//            }
//        )
//        string += "  "
//        string += event.fullName()
//        if (!event.withoutYear) {
//            string += "  "
//            string += context.resources.getQuantityString(R.plurals.age, event.age, event.age)
//        }
//        return string
//    }

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(getEventsChannel())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
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