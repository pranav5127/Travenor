package com.pranav.travenor.data.model

import com.pranav.travenor.ui.model.BookingState
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Destination(
    val id: String,
    val name: String,
    val city: String,

    val idx: Int? = null,

    @SerialName("is_active")
    val isActive: Boolean = true,

    @SerialName("owner_id")
    val ownerId: String? = null,

    @SerialName("image_url")
    val imageUrl: String? = null,

    @SerialName("gallery_images")
    val galleryImages: List<String>? = null,

    val rating: Double,

    @SerialName("review_count")
    val reviewCount: Int = 0,

    @SerialName("cost_per_person")
    val costPerPerson: Int? = null,

    val about: String? = null,

    @SerialName("booking_state")
    val bookingState: BookingState = BookingState.IDLE
)
