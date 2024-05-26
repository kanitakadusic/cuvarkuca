package ba.unsa.etf.rma.cuvarkuca.services

import com.google.gson.annotations.SerializedName

data class GetPlantResponse(
    @SerializedName("data") val plant: PlantResult
)
