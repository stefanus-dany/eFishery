package id.stefanusdany.efishery.ui.addData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.stefanusdany.efishery.data.Repository
import id.stefanusdany.efishery.data.source.local.entity.AreaEntity
import id.stefanusdany.efishery.data.source.local.entity.SizeEntity
import id.stefanusdany.efishery.data.source.remote.ApiResponse
import id.stefanusdany.efishery.data.source.remote.model.CommodityModel
import id.stefanusdany.efishery.data.source.remote.model.CommodityPostResponse
import id.stefanusdany.efishery.vo.Resource

class AddDataViewModel(private val repository: Repository) : ViewModel() {

    fun getAllArea(): LiveData<Resource<List<AreaEntity>>> = repository.getArea()

    fun getAllSize(): LiveData<Resource<List<SizeEntity>>> = repository.getSize()

    fun postCommodity(value: List<CommodityModel>): LiveData<ApiResponse<CommodityPostResponse>> =
        repository.postCommodity(value)
}