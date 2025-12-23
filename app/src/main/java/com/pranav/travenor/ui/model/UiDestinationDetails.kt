package com.pranav.travenor.ui.model

data class UiDestinationDetails(
    val id: String,
    val title: String,
    val location: String,
    val rating: Float,
    val imageUrl: String?,
    val galleryImages: List<String>?,
    val about: String?,
    val price: Int?,
    val bookingState: BookingState
)
