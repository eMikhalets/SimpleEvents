package com.emikhalets.simpleevents.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.emikhalets.simpleevents.data.database.Db.TableNotifications.COL_DAYS
import com.emikhalets.simpleevents.data.database.Db.TableNotifications.COL_ENABLED
import com.emikhalets.simpleevents.data.database.Db.TableNotifications.COL_ID
import com.emikhalets.simpleevents.data.database.Db.TableNotifications.COL_NAME
import com.emikhalets.simpleevents.data.database.Db.TableNotifications.NAME

@Entity(tableName = NAME)
data class NotificationGlobal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COL_ID) val id: Long = 0,
    @ColumnInfo(name = COL_NAME) val nameRes: Int,
    @ColumnInfo(name = COL_ENABLED) val enabled: Boolean,
    @ColumnInfo(name = COL_DAYS) val daysLeft: Int,
) {

    @Ignore
    constructor(nameRes: Int, enabled: Boolean, daysLeft: Int)
            : this(0, nameRes, enabled, daysLeft)
}