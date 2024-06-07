package ba.unsa.etf.rma.cuvarkuca.services

import android.graphics.Bitmap
import android.util.Log
import androidx.room.Dao
import ba.unsa.etf.rma.cuvarkuca.TrefleDAO
import ba.unsa.etf.rma.cuvarkuca.models.Biljka
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Dao
interface BiljkaDAO {
    suspend fun saveBiljka(
        plant: Biljka
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val room = BiljkaDatabase.getInstance()!!
                plant.id = room.roomDao().insertPlant(plant)

                Log.i("*_savePlant", "Plant saved")
                true
            } catch (e: Exception) {
                Log.e("*_savePlant", e.message.toString())
                false
            }
        }
    }

    suspend fun fixOfflineBiljka(): Int {
        return withContext(Dispatchers.IO) {
            var fixedCounter = 0

            try {
                val room = BiljkaDatabase.getInstance()!!
                val offlinePlants = room.roomDao().readOfflinePlants()

                for (plant in offlinePlants) {
                    val fixedPlant = TrefleDAO.fixData(plant)

                    if (fixedPlant != plant) {
                        fixedCounter++
                        room.roomDao().updatePlant(fixedPlant)
                    }
                }

                Log.i("*_fixOfflinePlants", "Offline plants fixed")
            } catch (e: Exception) {
                Log.e("*_fixOfflinePlants", e.message.toString())
            }

            fixedCounter
        }
    }

    suspend fun addImage(
        id: Long,
        bitmap: Bitmap
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val room = BiljkaDatabase.getInstance()!!
                room.roomDao().insertPlantBitmap(
                    BiljkaBitmap(
                        bitmap = bitmap,
                        plantId = id
                    )
                )

                Log.i("*_saveBitmap", "Bitmap saved")
                true
            } catch (e: Exception) {
                Log.e("*_saveBitmap", e.message.toString())
                false
            }
        }
    }

    suspend fun getAllBiljkas(): List<Biljka> {
        return withContext(Dispatchers.IO) {
            try {
                val room = BiljkaDatabase.getInstance()!!

                Log.i("*_getAllPlants", "All plants fetched")
                room.roomDao().readAllPlants()
            } catch (e: Exception) {
                Log.e("*_getAllPlants", e.message.toString())
                listOf()
            }
        }
    }

    suspend fun clearData() {
        withContext(Dispatchers.IO) {
            try {
                val room = BiljkaDatabase.getInstance()!!
                room.roomDao().deleteAllPlantBitmaps()
                room.roomDao().deleteAllPlants()

                Log.i("*_clearData", "Data cleared")
            } catch (e: Exception) {
                Log.e("*_clearData", e.message.toString())
            }
        }
    }
}