package ba.unsa.etf.rma.cuvarkuca.services

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PlantRepository {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getSearchResponse(query: String): GetSearchResponse? {
        return withContext(ioDispatcher) {
            val response = ApiAdapter.retrofit.getSearchResponse(query)
            return@withContext response.body()
        }
    }

    suspend fun getPlantResponse(identifier: String): GetPlantResponse? {
        return withContext(ioDispatcher) {
            val response = ApiAdapter.retrofit.getPlantResponse(identifier)
            return@withContext response.body()
        }
    }
}