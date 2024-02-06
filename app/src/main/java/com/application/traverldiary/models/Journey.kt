package com.application.traverldiary.models

open class Journey(
    var nameOrId: String,
    var fromTime: Int,
    var toTime: Int,
    var isHappened: Boolean = false
)