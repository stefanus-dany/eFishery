package id.stefanusdany.efishery.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class SizeEntity(

    @PrimaryKey
    @ColumnInfo(name = "size")
    var size: String
) : Parcelable