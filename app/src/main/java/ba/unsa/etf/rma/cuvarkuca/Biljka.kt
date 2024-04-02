package ba.unsa.etf.rma.cuvarkuca

data class Biljka (
    val naziv: String,
    val porodica: String,
    val medicinskoUpozorenje: String,
    val medicinskeKoristi: List<MedicinskaKorist>,
    val profilOkusa: ProfilOkusaBiljke,
    val jela: List<String>,
    val klimatskiTipovi: List<KlimatskiTip>,
    val zemljisniTipovi: List<Zemljiste>
) {
    fun medicallySimilarTo(plant: Biljka): Boolean {
        return this.medicinskeKoristi.any { it in plant.medicinskeKoristi }
    }

    fun culinarySimilarTo(plant: Biljka): Boolean {
        return this.profilOkusa == plant.profilOkusa
                || this.jela.any { it in plant.jela }
    }

    fun botanicallySimilarTo(plant: Biljka): Boolean {
        return this.porodica == plant.porodica
                && this.klimatskiTipovi.any { it in plant.klimatskiTipovi }
                && this.zemljisniTipovi.any { it in plant.zemljisniTipovi }
    }
}