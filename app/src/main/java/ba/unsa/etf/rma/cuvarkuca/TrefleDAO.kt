package ba.unsa.etf.rma.cuvarkuca

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import ba.unsa.etf.rma.cuvarkuca.models.Biljka
import ba.unsa.etf.rma.cuvarkuca.models.ProfilOkusaBiljke
import ba.unsa.etf.rma.cuvarkuca.services.GetPlantResponse
import ba.unsa.etf.rma.cuvarkuca.services.GetSearchResponse
import ba.unsa.etf.rma.cuvarkuca.services.PlantRepository

class TrefleDAO(
    private val context: Context
) {
    private val defaultBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.default_plant_image)

    suspend fun getImage(
        plant: Biljka
    ): Bitmap {
        val scientificName: String? = plant.getScientificName()
        if (scientificName == null) {
            Log.e("TrefleDAO", "getImage -> scientificName is null")
            return defaultBitmap
        }

        try {
            when (val searchResponse = PlantRepository.getSearchResponse(scientificName)) {
                is GetSearchResponse -> {
                    if (searchResponse.results.isNotEmpty()) {
                        val bitmap: Bitmap? = Utility.getBitmapFromUrl(searchResponse.results[0].imageUrl)
                        if (bitmap != null) return bitmap
                    } else Log.e("TrefleDAO", "getImage -> searchResponse.results is empty")
                } else -> Log.e("TrefleDAO", "getImage -> not GetSearchResponse")
            }
        } catch (e: Exception) {
            Log.e("TrefleDAO", "getImage -> connection error")
        }

        return defaultBitmap
    }

    suspend fun fixData(
        plant: Biljka
    ): Biljka {
        val scientificName: String? = plant.getScientificName()
        if (scientificName == null) {
            Log.e("TrefleDAO", "fixData -> scientificName is null")
            return plant
        }

        try {
            when (val searchResponse = PlantRepository.getSearchResponse(scientificName)) {
                is GetSearchResponse -> {
                    if (searchResponse.results.isNotEmpty()) {
                        when (val plantResponse = PlantRepository.getPlantResponse(searchResponse.results[0].identifier)) {
                            is GetPlantResponse -> {
                                plant.fixWith(plantResponse.plant)
                                Toast.makeText(context, "Plant fixed", Toast.LENGTH_SHORT).show()
                            } else -> Log.e("TrefleDAO", "fixData -> not GetPlantResponse")
                        }
                    } else Log.e("TrefleDAO", "fixData -> searchResponse.results is empty")
                } else -> Log.e("TrefleDAO", "fixData -> not GetSearchResponse")
            }
        } catch (e: Exception) {
            Log.e("TrefleDAO", "fixData -> connection error")
        }

        return plant
    }

    suspend fun getPlantsWithFlowerColor(
        color: String,
        input: String
    ): List<Biljka> {
        val plants = mutableListOf<Biljka>()

        try {
            when (val filterResponse = PlantRepository.getFilterResponse(color, input)) {
                is GetSearchResponse -> {
                    for (result in filterResponse.results)
                        when (val plantResponse = PlantRepository.getPlantResponse(result.identifier)) {
                            is GetPlantResponse -> {
                                val default = Biljka(
                                    "",
                                    "",
                                    "",
                                    listOf(),
                                    ProfilOkusaBiljke.BEZUKUSNO,
                                    listOf(),
                                    listOf(),
                                    listOf()
                                )

                                default.fixWith(plantResponse.plant, true)
                                plants.add(default)
                            } else -> Log.e("TrefleDAO", "getPlantsWithFlowerColor -> not GetPlantResponse")
                        }
                } else -> Log.e("TrefleDAO", "getPlantsWithFlowerColor -> not GetSearchResponse")
            }
        } catch (e: Exception) {
            Log.e("TrefleDAO", "getPlantsWithFlowerColor -> connection error")
        }

        return plants
    }
}