package id.stefanusdany.efishery.data

import androidx.lifecycle.LiveData
import id.stefanusdany.efishery.data.source.local.entity.AreaEntity
import id.stefanusdany.efishery.data.source.local.entity.CommodityEntity
import id.stefanusdany.efishery.data.source.local.entity.SizeEntity
import id.stefanusdany.efishery.data.source.local.repository.LocalDataSource
import id.stefanusdany.efishery.data.source.remote.ApiResponse
import id.stefanusdany.efishery.data.source.remote.RemoteDataSource
import id.stefanusdany.efishery.data.source.remote.model.AreaModel
import id.stefanusdany.efishery.data.source.remote.model.CommodityModel
import id.stefanusdany.efishery.data.source.remote.model.CommodityPostResponse
import id.stefanusdany.efishery.data.source.remote.model.SizeModel
import id.stefanusdany.efishery.utils.AppExecutors
import id.stefanusdany.efishery.vo.Resource

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : CommodityDataSource {

    override fun getAllCommodity(withoutFilter: Boolean): LiveData<Resource<List<CommodityEntity>>> {
        return object :
            NetworkBoundResource<List<CommodityEntity>, List<CommodityModel>>(appExecutors) {
            public override fun loadFromDB(): LiveData<List<CommodityEntity>> =
                localDataSource.getAllCommodity()

            override fun shouldFetch(data: List<CommodityEntity>?): Boolean =
                withoutFilter

            public override fun createCall(): LiveData<ApiResponse<List<CommodityModel>>> =
                remoteDataSource.getData()

            public override fun saveCallResult(data: List<CommodityModel>) {
                val commodityList = ArrayList<CommodityEntity>()
                for (response in data) {
                    val commodity = response.timestamp?.let {
                        CommodityEntity(
                            response.uuid,
                            response.komoditas,
                            response.area_provinsi,
                            response.area_kota,
                            response.size,
                            response.price,
                            response.tgl_parsed,
                            it
                        )
                    }
                    if (commodity != null) {
                        commodityList.add(commodity)
                    }
                }
                localDataSource.deleteAllCommodity()
                localDataSource.insertCommodity(commodityList)
            }
        }.asLiveData()
    }

    override fun searchCommodity(
        text: String
    ): LiveData<Resource<List<CommodityEntity>>> {
        return object :
            NetworkBoundResource<List<CommodityEntity>, List<CommodityModel>>(appExecutors) {
            public override fun loadFromDB(): LiveData<List<CommodityEntity>> =
                localDataSource.searchCommodity(text)

            override fun shouldFetch(data: List<CommodityEntity>?): Boolean =
                false

            public override fun createCall(): LiveData<ApiResponse<List<CommodityModel>>> =
                remoteDataSource.getData()

            public override fun saveCallResult(data: List<CommodityModel>) {
            }
        }.asLiveData()
    }

    override fun postCommodity(value: List<CommodityModel>): LiveData<ApiResponse<CommodityPostResponse>> =
        remoteDataSource.postCommodity(value)

    override fun getArea(): LiveData<Resource<List<AreaEntity>>> {
        return object :
            NetworkBoundResource<List<AreaEntity>, List<AreaModel>>(appExecutors) {
            public override fun loadFromDB(): LiveData<List<AreaEntity>> =
                localDataSource.getArea()

            override fun shouldFetch(data: List<AreaEntity>?): Boolean =
                true

            public override fun createCall(): LiveData<ApiResponse<List<AreaModel>>> =
                remoteDataSource.getArea()

            public override fun saveCallResult(data: List<AreaModel>) {
                val areaList = ArrayList<AreaEntity>()
                for (response in data) {
                    val area = AreaEntity(
                        response.province,
                        response.city
                    )
                    areaList.add(area)
                }
                localDataSource.deleteArea()
                localDataSource.insertArea(areaList)
            }
        }.asLiveData()
    }

    override fun getSize(): LiveData<Resource<List<SizeEntity>>> {
        return object :
            NetworkBoundResource<List<SizeEntity>, List<SizeModel>>(appExecutors) {
            public override fun loadFromDB(): LiveData<List<SizeEntity>> =
                localDataSource.getSize()

            override fun shouldFetch(data: List<SizeEntity>?): Boolean =
                true

            public override fun createCall(): LiveData<ApiResponse<List<SizeModel>>> =
                remoteDataSource.getSize()

            public override fun saveCallResult(data: List<SizeModel>) {
                val sizeList = ArrayList<SizeEntity>()
                for (response in data) {
                    val size = response.size?.let {
                        SizeEntity(
                            it
                        )
                    }
                    if (size != null) {
                        sizeList.add(size)
                    }
                }
                localDataSource.deleteSize()
                localDataSource.insertSize(sizeList)
            }
        }.asLiveData()
    }


}