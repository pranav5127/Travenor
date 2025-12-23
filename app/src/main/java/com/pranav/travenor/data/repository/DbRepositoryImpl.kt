package com.pranav.travenor.data.repository

import com.pranav.travenor.data.datasources.SupabaseDbDataSource
import com.pranav.travenor.data.model.Destination
import com.pranav.travenor.domain.repository.DbRepository
import com.pranav.travenor.ui.model.BookingState
import kotlinx.coroutines.flow.Flow

class DbRepositoryImpl(
    private val dataSource: SupabaseDbDataSource
) : DbRepository {


    override fun observeDestinations(): Flow<List<Destination>> {
        return dataSource.observeDestinations()
    }

    override fun observeDestinationDetails(id: String): Flow<Destination?> {
       return dataSource.observeDestinationDetails(id)
    }

    override suspend fun updateBookingState(id: String) {
        dataSource.updateBookingState(id, BookingState.REQUEST)
    }
}