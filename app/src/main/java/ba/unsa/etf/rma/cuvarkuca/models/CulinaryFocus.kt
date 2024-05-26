package ba.unsa.etf.rma.cuvarkuca.models

import ba.unsa.etf.rma.cuvarkuca.R

object CulinaryFocus : Focus {
    override val name = R.string.culinary
    override val iconOutline = R.drawable.ic_culinary
    override val iconSolid = R.drawable.ic_culinary_filled
    override val view = R.id.culinaryBox
    override val isFilterableByInputAndColor = false

    override fun areSimilarPlants(
        x: Biljka,
        y: Biljka
    ): Boolean {
        return x.profilOkusa == y.profilOkusa
                || x.jela.any { it in y.jela }
    }
}