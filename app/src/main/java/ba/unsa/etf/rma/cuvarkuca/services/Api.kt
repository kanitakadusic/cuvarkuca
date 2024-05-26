package ba.unsa.etf.rma.cuvarkuca.services

import ba.unsa.etf.rma.cuvarkuca.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("species/search")
    suspend fun getSearchResponse(
        @Query("q") query: String,
        @Query("token") apiKey: String = BuildConfig.TREFLE_API_KEY
    ): Response<GetSearchResponse>

    @GET("species/{slug}")
    suspend fun getPlantResponse(
        @Path("slug") identifier: String,
        @Query("token") apiKey: String = BuildConfig.TREFLE_API_KEY
    ): Response<GetPlantResponse>
}