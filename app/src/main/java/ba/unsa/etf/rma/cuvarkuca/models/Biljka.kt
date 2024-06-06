package ba.unsa.etf.rma.cuvarkuca.models

import android.os.Parcelable
import ba.unsa.etf.rma.cuvarkuca.services.PlantResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class Biljka (
    var slug: String = "no-slug",
    var onlineChecked: Boolean = false,

    var naziv: String = "",
    var porodica: String = "",
    var medicinskoUpozorenje: String = "",
    var medicinskeKoristi: List<MedicinskaKorist> = listOf(),
    var profilOkusa: ProfilOkusaBiljke = ProfilOkusaBiljke.BEZUKUSNO,
    var jela: List<String> = listOf(),
    var klimatskiTipovi: List<KlimatskiTip> = listOf(),
    var zemljisniTipovi: List<Zemljiste> = listOf()
) : Parcelable {

    fun getScientificName(): String? {
        val regex = "\\((.*?)\\)".toRegex()
        return regex.find(naziv)?.groupValues?.getOrNull(1)
    }

    fun fixWith(
        reference: PlantResult,
        fixName: Boolean = false
    ) {
        slug = reference.slug

        if (fixName) {
            naziv =
                if (reference.commonName != null) reference.commonName + " (" + reference.scientificName + ")"
                else "(" + reference.scientificName + ")"
        }

        porodica = reference.family

        if (reference.edible == false) {
            if (!medicinskoUpozorenje.contains("NIJE JESTIVO")) medicinskoUpozorenje += " NIJE JESTIVO."
            jela = listOf()
        }

        if (reference.specifications.toxic != null && reference.specifications.toxic != "none")
            if (!medicinskoUpozorenje.contains("TOKSIČNO")) medicinskoUpozorenje += " TOKSIČNO."

        if (reference.growth.light != null && reference.growth.humidity != null) {
            val lightRanges = listOf(6..9, 8..10, 6..9, 4..7, 7..9, 0..5)
            val humidityRanges = listOf(1..5, 7..10, 5..8, 3..7, 1..2, 3..7)
            val climateTypes = KlimatskiTip.entries

            val fixedClimates = klimatskiTipovi.toMutableList()

            for (i in 0 until climateTypes.size)
                if (reference.growth.light in lightRanges[i] && reference.growth.humidity in humidityRanges[i]) {
                    if (!fixedClimates.contains(climateTypes[i]))
                        fixedClimates.add(climateTypes[i])
                } else {
                    fixedClimates.remove(climateTypes[i])
                }

            klimatskiTipovi = fixedClimates
        }

        if (reference.growth.soil != null) {
            val soilRanges = listOf(3..4, 1..2, 5..6, 7..8, 9..9, 10..10)
            val soilTypes = Zemljiste.entries

            for (i in 0 until soilTypes.size)
                if (reference.growth.soil in soilRanges[i]) {
                    zemljisniTipovi = listOf(soilTypes[i])
                    break
                }
        }
    }
}