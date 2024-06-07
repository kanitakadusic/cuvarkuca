package ba.unsa.etf.rma.cuvarkuca.services

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TrefleRepository {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getSearchResponse(
        query: String
    ): GetSearchResponse? {
        return withContext(ioDispatcher) {
            try {
                val response = TrefleClient.retrofit.getSearchResponse(query)
                response.body()!!
            } catch (e: Exception) {
                Log.e("*_getSearchResponse", e.message.toString())
                null
            }
        }
    }

    suspend fun getPlantResponse(
        slug: String
    ): GetPlantResponse? {
        return withContext(ioDispatcher) {
            try {
                val response = TrefleClient.retrofit.getPlantResponse(slug)
                response.body()!!
            } catch (e: Exception) {
                Log.e("*_getPlantResponse", e.message.toString())
                null
            }
        }
    }

    suspend fun getFilterResponse(
        color: String,
        query: String
    ): GetSearchResponse? {
        return withContext(ioDispatcher) {
            try {
                val response = TrefleClient.retrofit.getFilterResponse(color, query)
                response.body()!!
            } catch (e: Exception) {
                Log.e("*_getFilterResponse", e.message.toString())
                null
            }
        }
    }
}