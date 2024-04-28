package ba.unsa.etf.rma.cuvarkuca

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ProfilOkusaBiljke (override val string: String) : EnumString, Parcelable {
    MENTA("Mentol - osvježavajući, hladan ukus"),
    CITRUSNI("Citrusni - osvježavajući, aromatičan"),
    SLATKI("Sladak okus"),
    BEZUKUSNO("Obični biljni okus - travnat, zemljast ukus"),
    LJUTO("Ljuto ili papreno"),
    KORIJENASTO("Korenast - drvenast i gorak ukus"),
    AROMATICNO("Začinski - topli i aromatičan ukus"),
    GORKO("Gorak okus")
}