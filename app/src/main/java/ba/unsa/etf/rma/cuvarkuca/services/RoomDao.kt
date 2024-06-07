package ba.unsa.etf.rma.cuvarkuca.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ba.unsa.etf.rma.cuvarkuca.models.Biljka

@Dao
interface RoomDao {
    @Insert
    suspend fun insertPlant(plant: Biljka): Long

    @Query("SELECT * FROM biljka")
    suspend fun readAllPlants(): List<Biljka>

    @Query("SELECT * FROM biljka WHERE fixed = false")
    suspend fun readOfflinePlants(): List<Biljka>

    @Update
    suspend fun updatePlant(plant: Biljka)

    @Query("DELETE FROM biljka")
    suspend fun deleteAllPlants()

    @Insert
    suspend fun insertPlantBitmap(plantBitmap: BiljkaBitmap): Long

    @Query("DELETE FROM biljkabitmap")
    suspend fun deleteAllPlantBitmaps()
}