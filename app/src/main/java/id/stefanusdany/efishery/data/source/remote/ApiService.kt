package id.stefanusdany.efishery.data.source.remote

import id.stefanusdany.efishery.data.source.remote.model.AreaModel
import id.stefanusdany.efishery.data.source.remote.model.CommodityModel
import id.stefanusdany.efishery.data.source.remote.model.CommodityPostResponse
import id.stefanusdany.efishery.data.source.remote.model.SizeModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("list")
    fun getAllCommodity(): Call<List<CommodityModel>>

    @GET("option_area")
    fun getArea(): Call<List<AreaModel>>

    @GET("option_size")
    fun getSize(): Call<List<SizeModel>>

    @POST("list")
    fun postCommodity(@Body req: List<CommodityModel>): Call<CommodityPostResponse>
}