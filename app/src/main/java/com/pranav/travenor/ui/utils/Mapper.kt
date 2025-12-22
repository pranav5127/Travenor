package com.pranav.travenor.ui.utils


import com.pranav.travenor.ui.model.UiDestination
import com.pranav.travenor.data.model.Destination as DataDestination

fun DataDestination.toUi(): UiDestination {
    return UiDestination(
        imageUrl = imageUrl ?: "",
        name = name,
        rating = rating,
        city = city,
        pricePerPerson = costPerPerson ?: 0
    )
}
