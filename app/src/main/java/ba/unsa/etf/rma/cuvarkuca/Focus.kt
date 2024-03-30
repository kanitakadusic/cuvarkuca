package ba.unsa.etf.rma.cuvarkuca

enum class Focus (val id: FocusResources) {
    MEDICAL(FocusResources(
        R.string.medical,
        R.drawable.medical_24px,
        R.drawable.medical_filled_24px,
        R.id.medicalBox
    )),
    CULINARY(FocusResources(
        R.string.culinary,
        R.drawable.culinary_24px,
        R.drawable.culinary_filled_24px,
        R.id.culinaryBox
    )),
    BOTANICAL(FocusResources(
        R.string.botanical,
        R.drawable.botanical_24px,
        R.drawable.botanical_filled_24px,
        R.id.botanicalBox
    ))
}