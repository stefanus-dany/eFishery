package id.stefanusdany.efishery.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class AreaEntity(

    @ColumnInfo(name = "province")
    var province: String,

    @PrimaryKey
    @ColumnInfo(name = "city")
    var city: String
) : Parcelable