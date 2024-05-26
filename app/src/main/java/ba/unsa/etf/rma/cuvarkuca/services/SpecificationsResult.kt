package ba.unsa.etf.rma.cuvarkuca.services

import com.google.gson.annotations.SerializedName

data class SpecificationsResult(
    @SerializedName("toxicity") val toxic: String?
)
