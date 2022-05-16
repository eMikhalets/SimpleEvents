package com.emikhalets.simpleevents.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventEntityOld(
    @SerialName("id") var id: Long,
    @SerialName("name") var name: String,
    @SerialName("lastName") var lastName: String,
    @SerialName("middleName") var middleName: String,
    @SerialName("date") var date: Long,
    @SerialName("daysLeft") var daysLeft: Int,
    @SerialName("age") var age: Int,
    @SerialName("eventType") var eventType: Int,
    @SerialName("group") var group: String,
    @SerialName("notes") var notes: String,
    @SerialName("withoutYear") var withoutYear: Boolean,
    @SerialName("imageUri") var imageUri: String,
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
