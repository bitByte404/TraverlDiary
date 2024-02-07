package com.application.traverldiary.models

class Destination(
    var name: String,
    fromTime: Float,
    toTime: Float,
    isHappened: Boolean
) : Journey(nameOrId = name, fromTime, toTime, isHappened)