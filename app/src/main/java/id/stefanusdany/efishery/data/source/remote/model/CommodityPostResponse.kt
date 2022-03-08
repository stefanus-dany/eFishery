package id.stefanusdany.efishery.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class CommodityPostResponse(
    @SerializedName("updatedRange")
    var updateRange: String?
)