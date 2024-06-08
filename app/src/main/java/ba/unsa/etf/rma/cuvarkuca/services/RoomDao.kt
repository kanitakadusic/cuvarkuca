package ba.unsa.etf.rma.cuvarkuca.services

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ba.unsa.etf.rma.cuvarkuca.models.Biljka

@Dao
interface RoomDao {
    @Insert
    suspend fun insertNewPlant(plant: Biljka): Long

    @Query("SELECT * FROM biljka")
    suspend fun readAllPlants(): List<Biljka>

    @Query("SELECT * FROM biljka WHERE fixed = false")
    suspend fun readUnfixedPlants(): List<Biljka>

    @Update
    suspend fun updatePlant(plant: Biljka)

    @Query("DELETE FROM biljka")
    suspend fun deleteAllPlants()

    @Insert
    suspend fun insertNewPlantBitmap(plantBitmap: BiljkaBitmap): Long

    @Query("SELECT bitmap FROM biljkabitmap WHERE plant_id = :plantId")
    suspend fun readBitmapByPlantId(plantId: Long): Bitmap?

    @Query("DELETE FROM biljkabitmap")
    suspend fun deleteAllPlantBitmaps()
}