package com.pranav.travenor.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

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

    val rating: Double,

    @SerialName("cost_per_person")
    val costPerPerson: Int? = null
)
