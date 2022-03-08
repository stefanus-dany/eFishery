package id.stefanusdany.efishery.data.source.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommodityModel(

    @SerializedName("uuid")
    var uuid: String?,

    @SerializedName("komoditas")
    var komoditas: String?,

    @SerializedName("area_provinsi")
    var area_provinsi: String?,

    @SerializedName("area_kota")
    var area_kota: String?,

    @SerializedName("size")
    var size: String?,

    @SerializedName("price")
    var price: String?,

    @SerializedName("tgl_parsed")
    var tgl_parsed: String?,

    @SerializedName("timestamp")
    var timestamp: String?
): Parcelable