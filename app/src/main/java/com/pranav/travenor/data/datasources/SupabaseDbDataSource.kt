package com.pranav.travenor.data.datasources

import com.pranav.travenor.data.model.Destination
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class SupabaseDbDataSource(
    private val supabase: SupabaseClient
) {


    @OptIn(SupabaseExperimental::class)
    fun observeDestinations(): Flow<List<Destination>> {
        return supabase
            .from("locations")
            .selectAsFlow(Destination::id, filter = FilterOperation(column = "is_active", operator = FilterOperator.EQ, value = true))
    }


    suspend fun bookDestination(id: UUID) {

    }
}