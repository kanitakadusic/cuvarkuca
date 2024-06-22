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
            childColumns = ["idBiljke"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(
            value = ["idBiljke"],
            unique = true
        )
    ]
)
data class BiljkaBitmap(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,

    @ColumnInfo(name = "bitmap", typeAffinity = ColumnInfo.TEXT) var bitmap: Bitmap,
    @ColumnInfo(name = "idBiljke") var plantId: Long
)