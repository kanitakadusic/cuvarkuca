package ba.unsa.etf.rma.cuvarkuca

import android.graphics.Bitmap
import android.util.Log
import ba.unsa.etf.rma.cuvarkuca.models.Biljka
import ba.unsa.etf.rma.cuvarkuca.services.GetPlantResponse
import ba.unsa.etf.rma.cuvarkuca.services.GetSearchResponse
import ba.unsa.etf.rma.cuvarkuca.services.TrefleRepository

object TrefleDAO {

    suspend fun getImage(
        plant: Biljka
    ): Bitmap? {
        val scientificName: String? = plant.getScientificName()

        if (scientificName != null) {
            when (val searchResponse = TrefleRepository.getSearchResponse(scientificName)) {
                is GetSearchResponse -> {
                    if (searchResponse.results.isNotEmpty()) {
                        val bitmap: Bitmap? = Utility.getBitmapFromUrl(searchResponse.results[0].imageUrl)
                        if (bitmap != null) return bitmap
                    }
                }
            }
        }

        return null
    }

    suspend fun fixData(
        plant: Biljka
    ): Biljka {
        val scientificName: String? = plant.getScientificName()

        if (scientificName != null) {
            when (val searchResponse = TrefleRepository.getSearchResponse(scientificName)) {
                is GetSearchResponse -> {
                    if (searchResponse.results.isNotEmpty()) {
                        when (val plantResponse = TrefleRepository.getPlantResponse(searchResponse.results[0].slug)) {
                            is GetPlantResponse -> {
                                plant.fixWith(plantResponse.plant)
                                plant.onlineChecked = true
                                Log.i("*_fixData", "Plant fixed")
                            }
                        }
                    }
                }
            }
        }

        return plant
    }

    suspend fun getPlantsWithFlowerColor(
        color: String,
        input: String
    ): List<Biljka> {
        val plants = mutableListOf<Biljka>()

        when (val filterResponse = TrefleRepository.getFilterResponse(color, input)) {
            is GetSearchResponse -> {
                for (result in filterResponse.results) {
                    when (val plantResponse = TrefleRepository.getPlantResponse(result.slug)) {
                        is GetPlantResponse -> {
                            val default = Biljka()

                            default.fixWith(plantResponse.plant, true)
                            default.onlineChecked = true

                            plants.add(default)
                        }
                    }
                }
            }
        }

        return plants
    }
}