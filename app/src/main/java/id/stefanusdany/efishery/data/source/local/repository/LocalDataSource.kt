package id.stefanusdany.efishery.data.source.local.repository

import androidx.lifecycle.LiveData
import id.stefanusdany.efishery.data.source.local.entity.AreaEntity
import id.stefanusdany.efishery.data.source.local.entity.CommodityEntity
import id.stefanusdany.efishery.data.source.local.entity.SizeEntity
import id.stefanusdany.efishery.data.source.local.room.AreaDao
import id.stefanusdany.efishery.data.source.local.room.CommodityDao
import id.stefanusdany.efishery.data.source.local.room.SizeDao

class LocalDataSource(
    private val commodityDao: CommodityDao,
    private val areaDao: AreaDao,
    private val sizeDao: SizeDao,
) {

    fun getAllCommodity(): LiveData<List<CommodityEntity>> = commodityDao.getAllCommodity()

    fun getArea(): LiveData<List<AreaEntity>> = areaDao.getArea()

    fun getSize(): LiveData<List<SizeEntity>> = sizeDao.getSize()

    fun insertCommodity(commodity: List<CommodityEntity>) = commodityDao.insert(commodity)

    fun insertArea(area: List<AreaEntity>) = areaDao.insert(area)

    fun insertSize(size: List<SizeEntity>) = sizeDao.insert(size)

    fun deleteAllCommodity() = commodityDao.deleteALlCommodity()

    fun deleteArea() = areaDao.deleteALlArea()

    fun deleteSize() = sizeDao.deleteALlSize()

    fun searchCommodity(
        searchText: String
    ): LiveData<List<CommodityEntity>> = commodityDao.getSearchCommodity(searchText)
}