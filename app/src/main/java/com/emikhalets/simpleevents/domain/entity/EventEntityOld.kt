package com.emikhalets.simpleevents.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventEntityOld(
    @SerialName("id") var id: Long,
    @SerialName("name") var name: String,
    @SerialName("lastname") var lastName: String,
    @SerialName("middle_name") var middleName: String,
    @SerialName("date_ts") var date: Long,
    @SerialName("daysLeft") var daysLeft: Int,
    @SerialName("age") var age: Int,
    @SerialName("event_type") var eventType: Int,
    @SerialName("group") var group: String,
    @SerialName("notes") var notes: String,
    @SerialName("without_year") var withoutYear: Boolean,
    @SerialName("image_uri") var imageUri: String,
    @SerialName("contacts") var contacts: List<String>,
) {

    fun fullName(): String {
        return if (middleName.isEmpty()) {
            if (lastName.isEmpty()) name
            else "$lastName $name"
        } else {
            if (lastName.isEmpty()) "$name $middleName"
            else "$lastName $name $middleName"
        }
    }
}
