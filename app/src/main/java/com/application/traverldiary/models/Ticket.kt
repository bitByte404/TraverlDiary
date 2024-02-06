package com.application.traverldiary.models

class Ticket(
    private val ticketId: String,
    val fromLocation: String,
    val toLocation: String,
    val seat: String,
    fromTime: Int,
    toTime: Int,
    isHappened: Boolean
) : Journey(nameOrId = ticketId, fromTime, toTime, isHappened)