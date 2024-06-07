package ba.unsa.etf.rma.cuvarkuca.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ba.unsa.etf.rma.cuvarkuca.models.Biljka

@Database(entities = [Biljka::class, BiljkaBitmap::class], version = 5)
@TypeConverters(Converters::class)
abstract class BiljkaDatabase : RoomDatabase() {

    abstract fun roomDao(): RoomDao

    abstract fun plantDao(): BiljkaDAO

    companion object {
        private var INSTANCE: BiljkaDatabase? = null

        private fun buildRoomDataBase(context: Context): BiljkaDatabase {
            return Room
                .databaseBuilder(
                    context.applicationContext,
                    BiljkaDatabase::class.java,
                    name = "biljke-db"
                )
                .build()
        }

        fun createInstance(context: Context) {
            if (INSTANCE == null)
                synchronized(BiljkaDatabase::class) {
                    INSTANCE = buildRoomDataBase(context)
                }
        }

        fun getInstance(): BiljkaDatabase? = INSTANCE
    }
}