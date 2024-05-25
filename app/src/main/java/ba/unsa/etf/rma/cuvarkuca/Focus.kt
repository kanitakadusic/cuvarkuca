package ba.unsa.etf.rma.cuvarkuca

interface Focus {
    val name: Int
    val iconOutline: Int
    val iconSolid: Int
    val view: Int
    val isFilterableByInputAndColor: Boolean

    fun areSimilarPlants(x: Biljka, y: Biljka): Boolean
}