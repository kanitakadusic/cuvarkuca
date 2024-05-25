package ba.unsa.etf.rma.cuvarkuca

object MedicalFocus : Focus {
    override val name = R.string.medical
    override val iconOutline = R.drawable.ic_medical
    override val iconSolid = R.drawable.ic_medical_filled
    override val view = R.id.medicalBox
    override val isFilterableByInputAndColor = false

    override fun areSimilarPlants(
        x: Biljka,
        y: Biljka
    ): Boolean {
        return x.medicinskeKoristi.any { it in y.medicinskeKoristi }
    }
}