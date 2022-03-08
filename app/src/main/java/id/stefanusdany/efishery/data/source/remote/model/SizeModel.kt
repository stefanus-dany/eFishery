package id.stefanusdany.efishery.data.source.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SizeModel(
    @SerializedName("size")
    var size: String?,

): Parcelable