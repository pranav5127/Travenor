package com.pranav.travenor.ui.utils


import com.pranav.travenor.ui.model.UiDestination
import com.pranav.travenor.ui.model.UiDestinationDetails
import java.util.UUID
import com.pranav.travenor.data.model.Destination as DataDestination

fun DataDestination.toUi(): UiDestination {
    return UiDestination(
        id = UUID.fromString(id),
        imageUrl = imageUrl ?: "",
        name = name,
        rating = rating,
        city = city,
        pricePerPerson = costPerPerson ?: 0
    )
}


fun DataDestination.toDetailsUi(): UiDestinationDetails {
    return UiDestinationDetails(
        id = id,
        title = name,
        location = city,
        rating = rating.toFloat(),
        imageUrl = imageUrl,
        galleryImages = galleryImages,
        about = about,
        price = costPerPerson
    )
}
