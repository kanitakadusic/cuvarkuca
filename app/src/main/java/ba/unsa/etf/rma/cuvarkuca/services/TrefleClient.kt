package ba.unsa.etf.rma.cuvarkuca.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TrefleClient {
    val retrofit: TrefleService = Retrofit.Builder()
        .baseUrl("https://trefle.io/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TrefleService::class.java)
}