package com.pranav.travenor.ui.model

import java.util.UUID

data class UiDestination(
    val id: UUID,
    val imageUrl: String,
    val name: String,
    val rating: Double,
    val city: String,
    val pricePerPerson: Int
)
