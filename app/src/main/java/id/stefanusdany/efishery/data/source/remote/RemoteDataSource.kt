package id.stefanusdany.efishery.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.stefanusdany.efishery.data.source.remote.model.AreaModel
import id.stefanusdany.efishery.data.source.remote.model.CommodityModel
import id.stefanusdany.efishery.data.source.remote.model.CommodityPostResponse
import id.stefanusdany.efishery.data.source.remote.model.SizeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RemoteDataSource(private val api: ApiService) {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    @Suppress("UNCHECKED_CAST")
    fun getData(): LiveData<ApiResponse<List<CommodityModel>>> {
        val mutableData = MutableLiveData<ApiResponse<List<CommodityModel>?>>()
        executorService.execute {
            api.getAllCommodity().enqueue(object : Callback<List<CommodityModel>> {
                override fun onResponse(
                    call: Call<List<CommodityModel>>,
                    response: Response<List<CommodityModel>>
                ) {
                    if (response.isSuccessful) {
                        mutableData.value = ApiResponse.success(response.body())
                    } else {
                        mutableData.value = ApiResponse.error("Cannot get data", null)
                        Log.e("getData", "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<CommodityModel>>, t: Throwable) {
                    mutableData.value = ApiResponse.error("Cannot get data from the server", null)
                    Log.e("getData", "onResponse: ${t.message}")
                }

            })
        }
        return mutableData as LiveData<ApiResponse<List<CommodityModel>>>
    }

    @Suppress("UNCHECKED_CAST")
    fun postCommodity(value: List<CommodityModel>): LiveData<ApiResponse<CommodityPostResponse>> {
        val mutableData = MutableLiveData<ApiResponse<CommodityPostResponse?>>()
        executorService.execute {
            api.postCommodity(value).enqueue(object : Callback<CommodityPostResponse> {
                override fun onResponse(
                    call: Call<CommodityPostResponse>,
                    response: Response<CommodityPostResponse>
                ) {
                    if (response.isSuccessful) {
                        mutableData.value = ApiResponse.success(response.body())
                    } else {
                        mutableData.value = ApiResponse.error("Cannot get data", null)
                        Log.e("getData", "onResponse: ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<CommodityPostResponse>, t: Throwable) {
                    mutableData.value = ApiResponse.error("Cannot post to the server", null)
                    Log.e("getData", "onResponse: ${t.message}")
                }

            })
        }
        return mutableData as LiveData<ApiResponse<CommodityPostResponse>>
    }

    @Suppress("UNCHECKED_CAST")
    fun getArea(): LiveData<ApiResponse<List<AreaModel>>> {
        val mutableData = MutableLiveData<ApiResponse<List<AreaModel>?>>()
        executorService.execute {
            api.getArea().enqueue(object : Callback<List<AreaModel>> {
                override fun onResponse(
                    call: Call<List<AreaModel>>,
                    response: Response<List<AreaModel>>
                ) {
                    if (response.isSuccessful) {
                        mutableData.value = ApiResponse.success(response.body())
                    } else {
                        mutableData.value = ApiResponse.error("Cannot get data", null)
                        Log.e("getData", "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<AreaModel>>, t: Throwable) {
                    mutableData.value = ApiResponse.error("Cannot get data from the server", null)
                    Log.e("getData", "onResponse: ${t.message}")
                }

            })
        }
        return mutableData as LiveData<ApiResponse<List<AreaModel>>>
    }

    @Suppress("UNCHECKED_CAST")
    fun getSize(): LiveData<ApiResponse<List<SizeModel>>> {
        val mutableData = MutableLiveData<ApiResponse<List<SizeModel>?>>()
        executorService.execute {
            api.getSize().enqueue(object : Callback<List<SizeModel>> {
                override fun onResponse(
                    call: Call<List<SizeModel>>,
                    response: Response<List<SizeModel>>
                ) {
                    if (response.isSuccessful) {
                        mutableData.value = ApiResponse.success(response.body())
                    } else {
                        mutableData.value = ApiResponse.error("Cannot get data", null)
                        Log.e("getData", "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<SizeModel>>, t: Throwable) {
                    mutableData.value = ApiResponse.error("Cannot get data from the server", null)
                    Log.e("getData", "onResponse: ${t.message}")
                }

            })
        }
        return mutableData as LiveData<ApiResponse<List<SizeModel>>>
    }
}