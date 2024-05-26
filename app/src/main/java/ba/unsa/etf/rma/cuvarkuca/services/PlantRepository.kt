package ba.unsa.etf.rma.cuvarkuca.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PlantRepository {
    suspend fun getSearchResponse(query: String): GetSearchResponse? {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getSearchResponse(query)
            return@withContext response.body()
        }
    }

    suspend fun getPlantResponse(identifier: String): GetPlantResponse? {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getPlantResponse(identifier)
            return@withContext response.body()
        }
    }
}