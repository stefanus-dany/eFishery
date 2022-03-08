package id.stefanusdany.efishery.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class CommodityEntity(

    @ColumnInfo(name = "uuid")
    var uuid: String?,

    @ColumnInfo(name = "komoditas")
    var komoditas: String?,

    @ColumnInfo(name = "area_provinsi")
    var area_provinsi: String?,

    @ColumnInfo(name = "area_kota")
    var area_kota: String?,

    @ColumnInfo(name = "size")
    var size: String?,

    @ColumnInfo(name = "price")
    var price: String?,

    @ColumnInfo(name = "tgl_parsed")
    var tgl_parsed: String?,

    @PrimaryKey
    @ColumnInfo(name = "timestamp")
    var timestamp: String
) : Parcelable