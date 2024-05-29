package ba.unsa.etf.rma.cuvarkuca.services

import com.google.gson.annotations.SerializedName

data class PlantResult(
    @SerializedName("slug") val identifier: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("common_name") val commonName: String?,
    @SerializedName("scientific_name") val scientificName: String,
    @SerializedName("family") val family: String,
    @SerializedName("edible") val edible: Boolean?,
    @SerializedName("specifications") val specifications: SpecificationsResult,
    @SerializedName("growth") val growth: GrowthResult
)
