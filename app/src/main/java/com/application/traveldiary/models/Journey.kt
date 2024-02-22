package com.application.traveldiary.models

open class Journey(
    var nameOrId: String,
    var fromTime: Float,
    var toTime: Float,
    var isHappened: Boolean = false,
    var date: String
)