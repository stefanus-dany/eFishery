package id.stefanusdany.efishery.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.stefanusdany.efishery.data.Repository
import id.stefanusdany.efishery.data.source.local.entity.CommodityEntity
import id.stefanusdany.efishery.vo.Resource

class MainViewModel(private val repository: Repository) : ViewModel() {

    fun getAllCommodity(withoutFilter: Boolean): LiveData<Resource<List<CommodityEntity>>> = repository.getAllCommodity(withoutFilter)

    fun searchCommodity(
        text: String
    ): LiveData<Resource<List<CommodityEntity>>> =
        repository.searchCommodity(text)
}