package ba.unsa.etf.rma.cuvarkuca.services

import com.google.gson.annotations.SerializedName

data class GetSearchResponse(
    @SerializedName("data") val results: List<GeneralResult>
)
