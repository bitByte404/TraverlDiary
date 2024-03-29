package com.application.traveldiary.models

class Ticket(
    private val ticketId: String,
    val fromLocation: String,
    val toLocation: String,
    val seat: String,
    fromTime: Float,
    toTime: Float,
    isHappened: Boolean
) : Journey(nameOrId = ticketId, fromTime, toTime, isHappened)