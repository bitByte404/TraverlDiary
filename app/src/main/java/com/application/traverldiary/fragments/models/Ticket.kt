package com.application.traverldiary.fragments.models

import java.util.Date

class Ticket(
    val ticketId:String,
    val date:Date,
    val fromTime:String,
    val toTime:String,
    val fromLocation:String,
    val toLocation:String,
    val seat:String,
    var isHappened:Boolean = false
) {
}