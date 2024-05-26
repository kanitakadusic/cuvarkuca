package ba.unsa.etf.rma.cuvarkuca.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ProfilOkusaBiljke (override val description: String) : EnumWithDescription, Parcelable {
    MENTA("Mentol - osvježavajući, hladan ukus"),
    CITRUSNI("Citrusni - osvježavajući, aromatičan"),
    SLATKI("Sladak okus"),
    BEZUKUSNO("Obični biljni okus - travnat, zemljast ukus"),
    LJUTO("Ljuto ili papreno"),
    KORIJENASTO("Korenast - drvenast i gorak ukus"),
    AROMATICNO("Začinski - topli i aromatičan ukus"),
    GORKO("Gorak okus")
}