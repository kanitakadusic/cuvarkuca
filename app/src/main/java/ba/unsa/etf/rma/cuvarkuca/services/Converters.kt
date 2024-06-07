package ba.unsa.etf.rma.cuvarkuca.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import ba.unsa.etf.rma.cuvarkuca.Utility
import ba.unsa.etf.rma.cuvarkuca.models.KlimatskiTip
import ba.unsa.etf.rma.cuvarkuca.models.MedicinskaKorist
import ba.unsa.etf.rma.cuvarkuca.models.Zemljiste
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.ByteBuffer

class Converters {
    @TypeConverter
    fun fromBenefits(benefits: List<MedicinskaKorist>): String = Gson().toJson(benefits)

    @TypeConverter
    fun toBenefits(data: String): List<MedicinskaKorist> {
        val list = object : TypeToken<List<MedicinskaKorist>>() {}.type
        return Gson().fromJson(data, list)
    }

    @TypeConverter
    fun fromDishes(dishes: List<String>): String = Gson().toJson(dishes)

    @TypeConverter
    fun toDishes(data: String): List<String> {
        val list = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(data, list)
    }

    @TypeConverter
    fun fromClimates(climates: List<KlimatskiTip>): String = Gson().toJson(climates)

    @TypeConverter
    fun toClimates(data: String): List<KlimatskiTip> {
        val listType = object : TypeToken<List<KlimatskiTip>>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun fromSoils(soils: List<Zemljiste>): String = Gson().toJson(soils)

    @TypeConverter
    fun toSoils(data: String): List<Zemljiste> {
        val listType = object : TypeToken<List<Zemljiste>>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): String {
        val newBitmap = Utility.cropBitmapTo11Ratio(
            Utility.compressBitmap(
                bitmap
            )
        )

        val byteBuffer = ByteBuffer.allocate(newBitmap.height * newBitmap.rowBytes)
        newBitmap.copyPixelsToBuffer(byteBuffer)
        return Base64.encodeToString(byteBuffer.array(), Base64.DEFAULT)
    }

    @TypeConverter
    fun toBitmap(base64String: String): Bitmap {
        val byteArray = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}