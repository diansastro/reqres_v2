package com.suitmedia.reqres_v2.model

data class EventData(
    var id: Int? = 0,
    var eventName: String? = "",
    var eventDate: String? = "",
    var eventMedia: String? = "",
    var lat: Double? = 0.0,
    var log: Double? = 0.0) {
}