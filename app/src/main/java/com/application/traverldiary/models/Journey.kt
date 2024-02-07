package com.application.traverldiary.models

open class Journey(
    var nameOrId: String,
    var fromTime: Float,
    var toTime: Float,
    var isHappened: Boolean = false
)