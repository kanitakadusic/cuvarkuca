package ba.unsa.etf.rma.cuvarkuca

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.unsa.etf.rma.cuvarkuca.models.Biljka
import ba.unsa.etf.rma.cuvarkuca.services.BiljkaDAO
import ba.unsa.etf.rma.cuvarkuca.services.BiljkaDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var dao: BiljkaDAO
    private lateinit var database: BiljkaDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, BiljkaDatabase::class.java).build()
        dao = database.plantDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun saveAndGet() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)

        scope.launch {
            val plant = Biljka(slug = "save-1")
            dao.saveBiljka(plant)
            val all = dao.getAllBiljkas()

            assertThat(all.size, greaterThan(0))
            assertThat(all.last(), equalTo(plant))
        }
    }

    @Test
    fun clearAndGet() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)

        scope.launch {
            dao.saveBiljka(Biljka(slug = "populate-1"))
            dao.saveBiljka(Biljka(slug = "populate-2"))

            dao.clearData()
            val all = dao.getAllBiljkas()

            assertThat(all.isEmpty(), equalTo(true))
        }
    }
}