package ba.unsa.etf.rma.cuvarkuca.services

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ba.unsa.etf.rma.cuvarkuca.models.Biljka

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Biljka::class,
            parentColumns = ["id"],
            childColumns = ["plant_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(
            value = ["plant_id"],
            unique = true
        )
    ]
)
data class BiljkaBitmap(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,

    @ColumnInfo(name = "bitmap") var bitmap: Bitmap,
    @ColumnInfo(name = "plant_id") var plantId: Long
)