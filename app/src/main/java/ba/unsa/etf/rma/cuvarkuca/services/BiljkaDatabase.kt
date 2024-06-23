package ba.unsa.etf.rma.cuvarkuca.services

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Update
import ba.unsa.etf.rma.cuvarkuca.TrefleDAO
import ba.unsa.etf.rma.cuvarkuca.models.Biljka
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Database(entities = [Biljka::class, BiljkaBitmap::class], version = 1)
@TypeConverters(Converters::class)
abstract class BiljkaDatabase : RoomDatabase() {

    abstract fun biljkaDao(): BiljkaDAO

    companion object {
        private var INSTANCE: BiljkaDatabase? = null

        fun getInstance(
            context: Context
        ): BiljkaDatabase {
            if (INSTANCE == null)
                synchronized (BiljkaDatabase::class) {
                    INSTANCE = buildRoomDataBase(context)
                }

            return INSTANCE!!
        }

        private fun buildRoomDataBase(
            context: Context
        ): BiljkaDatabase {
            return Room
                .databaseBuilder(
                    context.applicationContext,
                    BiljkaDatabase::class.java,
                    name = "biljke-db"
                )
                .build()
        }
    }

    @Dao
    interface BiljkaDAO {
        @Insert
        suspend fun insertNewPlants(vararg plants: Biljka): List<Long>

        @Query("SELECT * FROM biljka")
        suspend fun readAllPlants(): List<Biljka>

        @Query("SELECT * FROM biljka WHERE onlineChecked = false")
        suspend fun readUnfixedPlants(): List<Biljka>

        @Update
        suspend fun updatePlant(plant: Biljka)

        @Query("DELETE FROM biljka")
        suspend fun deleteAllPlants()

        @Insert
        suspend fun insertNewPlantBitmap(plantBitmap: BiljkaBitmap): Long

        @Query("SELECT bitmap FROM biljkabitmap WHERE idBiljke = :plantId")
        suspend fun readBitmapByPlantId(plantId: Long): Bitmap?

        @Query("DELETE FROM biljkabitmap")
        suspend fun deleteAllPlantBitmaps()

        suspend fun saveBiljka(
            plant: Biljka
        ): Boolean {
            return withContext(Dispatchers.IO) {
                try {
                    plant.id = insertNewPlants(plant)[0]

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
                    val offlinePlants = readUnfixedPlants()

                    for (plant in offlinePlants) {
                        val fixedPlant = TrefleDAO.fixData(plant.copy())

                        if (fixedPlant != plant) {
                            fixedCounter++
                            updatePlant(fixedPlant)
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
                    insertNewPlantBitmap(
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
                    Log.i("*_getAllPlants", "All plants fetched")
                    readAllPlants()
                } catch (e: Exception) {
                    Log.e("*_getAllPlants", e.message.toString())
                    listOf()
                }
            }
        }

        suspend fun clearData() {
            withContext(Dispatchers.IO) {
                try {
                    deleteAllPlantBitmaps()
                    deleteAllPlants()

                    Log.i("*_clearData", "Data cleared")
                } catch (e: Exception) {
                    Log.e("*_clearData", e.message.toString())
                }
            }
        }
    }
}