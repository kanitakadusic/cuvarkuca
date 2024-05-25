package ba.unsa.etf.rma.cuvarkuca

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Zemljiste (override val description: String) : EnumWithDescription, Parcelable {
    PJESKOVITO("Pjeskovito zemljište"),
    GLINENO("Glineno zemljište"),
    ILOVACA("Ilovača"),
    CRNICA("Crnica"),
    SLJUNKOVITO("Šljunovito zemljište"),
    KRECNJACKO("Krečnjačko zemljište")
}