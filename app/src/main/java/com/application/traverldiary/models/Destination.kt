package com.application.traverldiary.models

class Destination(
    var name: String,
    fromTime: Int,
    toTime: Int,
    isHappened: Boolean
) : Journey(nameOrId = name, fromTime, toTime, isHappened)