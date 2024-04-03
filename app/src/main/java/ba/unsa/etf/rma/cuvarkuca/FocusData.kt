package ba.unsa.etf.rma.cuvarkuca

// BEGIN: for testing purposes only [*]
fun getFocus(name: String): Focus? {
    return when (name) {
        "Medicinski" -> Focus.MEDICAL
        "Kuharski" -> Focus.CULINARY
        "Botanički" -> Focus.BOTANICAL
        else -> null
    }
}
// END: for testing purposes only

enum class Focus (val position: Int) {
    MEDICAL(0),
    CULINARY(1),
    BOTANICAL(2)
}

data class Data (
    val name: Int,
    val outlineIcon: Int,
    val solidIcon: Int,
    val viewBox: Int,
    val similar: (Biljka, Biljka) -> Boolean,
    val bindData: (PlantListAdapter.PlantViewHolder, Biljka) -> Unit
)

val focusData = listOf(
    Data(
        R.string.medical,
        R.drawable.medical_24px,
        R.drawable.medical_filled_24px,
        R.id.medicalBox,
        { x, y -> FocusPlantUtility.medicallySimilar(x, y) },
        { x, y -> FocusPlantUtility.bindMedicalData(x, y) }
    ),
    Data(
        R.string.culinary,
        R.drawable.culinary_24px,
        R.drawable.culinary_filled_24px,
        R.id.culinaryBox,
        { x, y -> FocusPlantUtility.culinarySimilar(x, y) },
        { x, y -> FocusPlantUtility.bindCulinaryData(x, y) }
    ),
    Data(
        R.string.botanical,
        R.drawable.botanical_24px,
        R.drawable.botanical_filled_24px,
        R.id.botanicalBox,
        { x, y -> FocusPlantUtility.botanicallySimilar(x, y) },
        { x, y -> FocusPlantUtility.bindBotanicalData(x, y) }
    )
)
