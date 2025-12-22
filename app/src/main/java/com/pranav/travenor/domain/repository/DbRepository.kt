package com.pranav.travenor.domain.repository

import com.pranav.travenor.data.model.Destination
import kotlinx.coroutines.flow.Flow

interface DbRepository {

    fun observeDestinations(): Flow<List<Destination>>

    fun observeDestinationDetails(id: String): Flow<Destination?>
    suspend fun bookDestinations(id: String)

}