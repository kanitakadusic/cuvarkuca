package ba.unsa.etf.rma.cuvarkuca.services

import com.google.gson.annotations.SerializedName

data class GrowthResult(
    @SerializedName("light") val light: Int?,
    @SerializedName("atmospheric_humidity") val humidity: Int?,
    @SerializedName("soil_texture") val soil: Int?
)
