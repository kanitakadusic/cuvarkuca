package ba.unsa.etf.rma.cuvarkuca.services

import com.google.gson.annotations.SerializedName

data class GeneralResult(
    @SerializedName("slug") val slug: String,
    @SerializedName("image_url") val imageUrl: String?
)
