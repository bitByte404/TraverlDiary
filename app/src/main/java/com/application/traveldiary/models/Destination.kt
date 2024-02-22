package com.application.traveldiary.models

class Destination(
    var name: String,
    fromTime: Float,
    toTime: Float,
    isHappened: Boolean,
    date: String
) : Journey(nameOrId = name, fromTime, toTime, isHappened, date)