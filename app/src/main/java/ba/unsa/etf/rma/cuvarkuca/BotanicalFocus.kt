package ba.unsa.etf.rma.cuvarkuca

object BotanicalFocus : Focus {
    override val name = R.string.botanical
    override val iconOutline = R.drawable.ic_botanical
    override val iconSolid = R.drawable.ic_botanical_filled
    override val view = R.id.botanicalBox
    override val isFilterableByInputAndColor = true

    override fun areSimilarPlants(
        x: Biljka,
        y: Biljka
    ): Boolean {
        return x.porodica == y.porodica
                && x.klimatskiTipovi.any { it in y.klimatskiTipovi }
                && x.zemljisniTipovi.any { it in y.zemljisniTipovi }
    }
}