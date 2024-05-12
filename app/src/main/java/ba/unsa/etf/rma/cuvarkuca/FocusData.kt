package ba.unsa.etf.rma.cuvarkuca

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
        R.drawable.ic_medical,
        R.drawable.ic_medical_filled,
        R.id.medicalBox,
        { x, y -> FocusPlantUtility.medicallySimilar(x, y) },
        { x, y -> FocusPlantUtility.bindMedicalData(x, y) }
    ),
    Data(
        R.string.culinary,
        R.drawable.ic_culinary,
        R.drawable.ic_culinary_filled,
        R.id.culinaryBox,
        { x, y -> FocusPlantUtility.culinarySimilar(x, y) },
        { x, y -> FocusPlantUtility.bindCulinaryData(x, y) }
    ),
    Data(
        R.string.botanical,
        R.drawable.ic_botanical,
        R.drawable.ic_botanical_filled,
        R.id.botanicalBox,
        { x, y -> FocusPlantUtility.botanicallySimilar(x, y) },
        { x, y -> FocusPlantUtility.bindBotanicalData(x, y) }
    )
)
