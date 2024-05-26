package ba.unsa.etf.rma.cuvarkuca.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class MedicinskaKorist (override val description: String) : EnumWithDescription, Parcelable {
    SMIRENJE("Smirenje - za smirenje i relaksaciju"),
    PROTUUPALNO("Protuupalno - za smanjenje upale"),
    PROTIVBOLOVA("Protivbolova - za smanjenje bolova"),
    REGULACIJAPRITISKA("Regulacija pritiska - za regulaciju visokog/niskog pritiska"),
    REGULACIJAPROBAVE("Regulacija probave"),
    PODRSKAIMUNITETU("Podrška imunitetu")
}