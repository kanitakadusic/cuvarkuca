package ba.unsa.etf.rma.cuvarkuca.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.room.TypeConverter
import ba.unsa.etf.rma.cuvarkuca.Utility
import ba.unsa.etf.rma.cuvarkuca.models.KlimatskiTip
import ba.unsa.etf.rma.cuvarkuca.models.MedicinskaKorist
import ba.unsa.etf.rma.cuvarkuca.models.Zemljiste
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt

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
        var cropped = Utility.cropBitmapToSquare(bitmap)

        while (cropped.byteCount > 500000) {
            Log.i("*_fromBitmap", "Scaling... (" + cropped.byteCount.toString() + " bytes)")

            val scaledWidth = (cropped.width * 0.8).roundToInt()
            val scaledHeight = (cropped.height * 0.8).roundToInt()
            cropped = Bitmap.createScaledBitmap(cropped, scaledWidth, scaledHeight, true)
        }

        val stream = ByteArrayOutputStream()
        cropped.compress(Bitmap.CompressFormat.PNG, 100, stream)

        val byteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    @TypeConverter
    fun toBitmap(string: String): Bitmap {
        val byteArray = Base64.decode(string, Base64.DEFAULT)
        Log.i("*_toBitmap", "ByteArray size: " + byteArray.size.toString())
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}