package ba.unsa.etf.rma.cuvarkuca

class FocusPlantUtility {
    companion object {
        fun medicallySimilar(x: Biljka, y: Biljka): Boolean {
            return x.medicinskeKoristi.any { it in y.medicinskeKoristi }
        }

        fun culinarySimilar(x: Biljka, y: Biljka): Boolean {
            return x.profilOkusa == y.profilOkusa
                    || x.jela.any { it in y.jela }
        }

        fun botanicallySimilar(x: Biljka, y: Biljka): Boolean {
            return x.porodica == y.porodica
                    && x.klimatskiTipovi.any { it in y.klimatskiTipovi }
                    && x.zemljisniTipovi.any { it in y.zemljisniTipovi }
        }

        fun bindMedicalData(holder: PlantListAdapter.PlantViewHolder, plant: Biljka) {
            holder.caution.text = plant.medicinskoUpozorenje

            val benefitList = plant.medicinskeKoristi
            for (i in 0 until minOf(holder.benefits.size, benefitList.size)) {
                holder.benefits[i].text = benefitList[i].opis
            }
        }

        fun bindCulinaryData(holder: PlantListAdapter.PlantViewHolder, plant: Biljka) {
            holder.taste.text = plant.profilOkusa.opis

            val dishList = plant.jela
            for (i in 0 until minOf(holder.dishes.size, dishList.size)) {
                holder.dishes[i].text = dishList[i]
            }
        }

        fun bindBotanicalData(holder: PlantListAdapter.PlantViewHolder, plant: Biljka) {
            holder.family.text = plant.porodica

            if (plant.klimatskiTipovi.isNotEmpty()) {
                holder.climate.text = plant.klimatskiTipovi[0].opis
            }

            if (plant.zemljisniTipovi.isNotEmpty()) {
                holder.soil.text = plant.zemljisniTipovi[0].naziv
            }
        }
    }
}