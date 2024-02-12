package com.application.traveldiary.models.items

import com.application.traveldiary.models.Journey

open class JourneyItem(
    var journey: Journey,
    val topRatio: Float = (journey.fromTime) / 24f,
    val heightRatio: Float = (journey.toTime - journey.fromTime) / 24f
)