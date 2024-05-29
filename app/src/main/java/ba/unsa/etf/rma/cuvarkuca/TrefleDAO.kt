package ba.unsa.etf.rma.cuvarkuca

import android.util.Log
import ba.unsa.etf.rma.cuvarkuca.models.Biljka
import ba.unsa.etf.rma.cuvarkuca.models.KlimatskiTip
import ba.unsa.etf.rma.cuvarkuca.models.ProfilOkusaBiljke
import ba.unsa.etf.rma.cuvarkuca.models.Zemljiste
import ba.unsa.etf.rma.cuvarkuca.services.GetPlantResponse
import ba.unsa.etf.rma.cuvarkuca.services.GetSearchResponse
import ba.unsa.etf.rma.cuvarkuca.services.PlantRepository
import ba.unsa.etf.rma.cuvarkuca.services.PlantResult

object TrefleDAO {
    val defaultBitmap = R.drawable.tulips

    /*
    suspend fun getImage(
        plant: Biljka
    ): Bitmap {
        when (val searchResponse = PlantRepository.getSearchResponse(plant.naziv)) {
            is GetSearchResponse -> {
                if (searchResponse.results.isNotEmpty()) {
                    val url = searchResponse.results[0].imageUrl
                } else Log.i("TrefleDAO", "fixData -> searchResponse.results is empty")
            } else -> Log.e("TrefleDAO", "fixData -> not GetSearchResponse")
        }

        return plant
    }
    */

    suspend fun fixData(
        plant: Biljka
    ): Biljka {
        when (val searchResponse = PlantRepository.getSearchResponse(Utility.getScientificName(plant.naziv)!!)) {
            is GetSearchResponse -> {
                if (searchResponse.results.isNotEmpty()) {
                    when (val plantResponse = PlantRepository.getPlantResponse(searchResponse.results[0].identifier)) {
                        is GetPlantResponse -> return getFixedPlant(plant, plantResponse.plant, false)
                        else -> Log.e("TrefleDAO", "fixData -> not GetPlantResponse")
                    }
                } else Log.i("TrefleDAO", "fixData -> searchResponse.results is empty")
            } else -> Log.e("TrefleDAO", "fixData -> not GetSearchResponse")
        }

        return plant
    }

    private fun getFixedPlant(
        plant: Biljka,
        data: PlantResult,
        setNameFromData: Boolean
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

        val fixedName: String = if (!setNameFromData) plant.naziv else {
            if (data.commonName != null) data.commonName + " (" + data.scientificName + ")"
            else "(" + data.scientificName + ")"
        }

        return Biljka(
            fixedName,
            data.family,
            fixedWarning,
            plant.medicinskeKoristi,
            plant.profilOkusa,
            if (data.edible == false) listOf() else plant.jela,
            fixedClimates,
            fixedSoils
        )
    }

    suspend fun getPlantsWithFlowerColor(
        color: String,
        input: String
    ): List<Biljka> {
        val plants: MutableList<Biljka> = mutableListOf()
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

        when (val filterResponse = PlantRepository.getFilterResponse(color, input)) {
            is GetSearchResponse -> {
                for (result in filterResponse.results)
                    when (val plantResponse = PlantRepository.getPlantResponse(result.identifier)) {
                        is GetPlantResponse -> plants.add(getFixedPlant(default, plantResponse.plant, true))
                        else -> Log.e("TrefleDAO", "flowerColor -> not GetPlantResponse")
                    }
            } else -> Log.e("TrefleDAO", "flowerColor -> not GetSearchResponse")
        }

        return plants
    }
}