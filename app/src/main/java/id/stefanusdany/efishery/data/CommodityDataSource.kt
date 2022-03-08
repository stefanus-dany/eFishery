package id.stefanusdany.efishery.data

import androidx.lifecycle.LiveData
import id.stefanusdany.efishery.data.source.local.entity.AreaEntity
import id.stefanusdany.efishery.data.source.local.entity.CommodityEntity
import id.stefanusdany.efishery.data.source.local.entity.SizeEntity
import id.stefanusdany.efishery.data.source.remote.ApiResponse
import id.stefanusdany.efishery.data.source.remote.model.CommodityModel
import id.stefanusdany.efishery.data.source.remote.model.CommodityPostResponse
import id.stefanusdany.efishery.vo.Resource

interface CommodityDataSource {

    fun getAllCommodity(filter: Boolean): LiveData<Resource<List<CommodityEntity>>>

    fun searchCommodity(text: String): LiveData<Resource<List<CommodityEntity>>>

    fun postCommodity(value: List<CommodityModel>): LiveData<ApiResponse<CommodityPostResponse>>

    fun getArea(): LiveData<Resource<List<AreaEntity>>>

    fun getSize(): LiveData<Resource<List<SizeEntity>>>
}