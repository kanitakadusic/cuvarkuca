package ba.unsa.etf.rma.cuvarkuca

enum class Zemljiste (override val string: String) : EnumString {
    PJESKOVITO("Pjeskovito zemljište"),
    GLINENO("Glineno zemljište"),
    ILOVACA("Ilovača"),
    CRNICA("Crnica"),
    SLJUNKOVITO("Šljunovito zemljište"),
    KRECNJACKO("Krečnjačko zemljište")
}