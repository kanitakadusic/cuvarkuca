package ba.unsa.etf.rma.cuvarkuca

import android.util.Log
import ba.unsa.etf.rma.cuvarkuca.models.Biljka
import ba.unsa.etf.rma.cuvarkuca.models.KlimatskiTip
import ba.unsa.etf.rma.cuvarkuca.models.Zemljiste
import ba.unsa.etf.rma.cuvarkuca.services.GetPlantResponse
import ba.unsa.etf.rma.cuvarkuca.services.GetSearchResponse
import ba.unsa.etf.rma.cuvarkuca.services.PlantRepository
import ba.unsa.etf.rma.cuvarkuca.services.PlantResult

object TrefleDAO {
    val defaultBitmap = R.drawable.tulips

    /*
    fun getImage(
        plant: Biljka
    ): Bitmap {

    }
    */

    suspend fun fixData(
        plant: Biljka
    ): Biljka {
        when (val searchResponse = PlantRepository.getSearchResponse(plant.naziv)) {
            is GetSearchResponse -> {
                if (searchResponse.results.isNotEmpty()) {
                    when (val plantResponse = PlantRepository.getPlantResponse(searchResponse.results[0].identifier)) {
                        is GetPlantResponse -> return getFixedPlant(plant, plantResponse.plant)
                        else -> Log.e("TrefleDAO", "fixData -> not GetPlantResponse")
                    }
                } else Log.i("TrefleDAO", "fixData -> searchResponse.results is empty")
            } else -> Log.e("TrefleDAO", "fixData -> not GetSearchResponse")
        }

        return plant
    }

    private fun getFixedPlant(
        plant: Biljka,
        data: PlantResult
    ): Biljka {
        var fixedWarning: String = plant.medicinskoUpozorenje

        if (data.edible == false)
            if (!fixedWarning.contains("NIJE JESTIVO")) fixedWarning += " NIJE JESTIVO."

        if (data.specifications.toxic != null && data.specifications.toxic != "none")
            if (!fixedWarning.contains("TOKSIČNO")) fixedWarning += " TOKSIČNO."

        var fixedSoils: List<Zemljiste> = plant.zemljisniTipovi

        if (data.growth.soil != null) {
            val soilRanges = listOf(3..4, 1..2, 5..6, 7..8, 9..9, 10..10)
            val soilTypes = Zemljiste.entries

            for (i in 0 until soilTypes.size)
                if (data.growth.soil in soilRanges[i]) {
                    fixedSoils = listOf(soilTypes[i])
                    break
                }
        }

        val fixedClimates: MutableList<KlimatskiTip> = plant.klimatskiTipovi.toMutableList()

        if (data.growth.light != null && data.growth.humidity != null) {
            val lightRanges = listOf(6..9, 8..10, 6..9, 4..7, 7..9, 0..5)
            val humidityRanges = listOf(1..5, 7..10, 5..8, 3..7, 1..2, 3..7)
            val climateTypes = KlimatskiTip.entries

            for (i in 0 until climateTypes.size)
                if (data.growth.light in lightRanges[i] && data.growth.humidity in humidityRanges[i]) {
                    if (!fixedClimates.contains(climateTypes[i]))
                        fixedClimates.add(climateTypes[i])
                } else {
                    fixedClimates.remove(climateTypes[i])
                }
        }

        return Biljka(
            plant.naziv,
            data.family,
            fixedWarning,
            plant.medicinskeKoristi,
            plant.profilOkusa,
            if (data.edible == false) listOf() else plant.jela,
            fixedClimates,
            fixedSoils
        )
    }

    /*
    fun getPlantsWithFlowerColor(
        color: String,
        input: String
    ): List<Biljka> {
        return listOf()
    }
    */
}