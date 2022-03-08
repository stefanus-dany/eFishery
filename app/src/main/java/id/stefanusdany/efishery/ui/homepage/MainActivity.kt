package id.stefanusdany.efishery.ui.homepage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.stefanusdany.efishery.R
import id.stefanusdany.efishery.data.source.local.entity.CommodityEntity
import id.stefanusdany.efishery.databinding.ActivityMainBinding
import id.stefanusdany.efishery.ui.addData.AddDataActivity
import id.stefanusdany.efishery.ui.filter.FilterActivity
import id.stefanusdany.efishery.utils.Helper.visibility
import id.stefanusdany.efishery.vo.Status
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAllData()
        search()

        binding.ivFilter.setOnClickListener {
            val moveForResultIntent = Intent(this@MainActivity, FilterActivity::class.java)
            resultLauncher.launch(moveForResultIntent)
        }

        binding.fabAdd.setOnClickListener {
            val moveForResultIntent = Intent(this@MainActivity, AddDataActivity::class.java)
            startActivity(moveForResultIntent)
        }
    }

    private fun setupRecyclerView(data: List<CommodityEntity>?) {
        adapter = MainAdapter(this@MainActivity)
        with(binding.rvMain) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        adapter.setData(data)
    }

    private fun getAllData() {
        viewModel.getAllCommodity(true).observe(this) {
            setupRecyclerView(it.data)
            isLoading(it.status == Status.LOADING && it.data.isNullOrEmpty())
            tvDataEmptyIsVisible(
                it.status == Status.ERROR && it.data.isNullOrEmpty(),
                it.message.toString()
            )
        }
    }

    private fun getSearchData(text: String) {
        isLoading(true)
        viewModel.searchCommodity(text).observe(this@MainActivity) {
            setupRecyclerView(it.data)
            isLoading(false)
            tvDataEmptyIsVisible(
                it.data.isNullOrEmpty(),
                resources.getString(R.string.data_is_empty)
            )
        }
    }

    @SuppressLint("CheckResult")
    private fun search() {
        Observable.create<String> { emitter ->
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    //hide keyboard when press search button on keyboard
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!emitter.isDisposed) {
                        if (newText != null) {
                            emitter.onNext(newText)
                        }
                    }
                    return true
                }
            })
        }.debounce(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                getSearchData(text)
            }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_CODE && result.data != null) {
            val filterProvinceValue =
                result.data?.getStringExtra(EXTRA_FILTER_PROVINCE)
            val filterCityValue =
                result.data?.getStringExtra(EXTRA_FILTER_CITY)
            val filterSizeValue =
                result.data?.getStringExtra(EXTRA_FILTER_SIZE)
            getFilterData(filterProvinceValue, filterCityValue, filterSizeValue)

        }
    }

    private fun getFilterData(
        filterProvinceValue: String?,
        filterCityValue: String?,
        filterSizeValue: String?
    ) {
        viewModel.getAllCommodity(false).observe(this) {
            val listProvince = mutableListOf<CommodityEntity>()
            if (filterProvinceValue == "" && filterCityValue == "" && filterSizeValue == "") {
                it.data?.let { it1 -> listProvince.addAll(it1) }
            } else if (filterProvinceValue != "" && filterCityValue == "" && filterSizeValue == "") {
                it.data?.map { value ->
                    if (filterProvinceValue == value.area_provinsi) {
                        listProvince.add(value)
                    }
                }
            } else if (filterProvinceValue == "" && filterCityValue != "" && filterSizeValue == "") {
                it.data?.map { value ->
                    if (filterCityValue == value.area_kota) {
                        listProvince.add(value)
                    }
                }
            } else if (filterProvinceValue == "" && filterCityValue == "" && filterSizeValue != "") {
                it.data?.map { value ->
                    if (filterSizeValue == value.size) {
                        listProvince.add(value)
                    }
                }
            } else if (filterProvinceValue == "" && filterCityValue != "" && filterSizeValue != "") {
                it.data?.map { value ->
                    if (filterCityValue == value.area_kota && filterSizeValue == value.size) {
                        listProvince.add(value)
                    }
                }
            } else if (filterProvinceValue != "" && filterCityValue == "" && filterSizeValue != "") {
                it.data?.map { value ->
                    if (filterProvinceValue == value.area_provinsi && filterSizeValue == value.size) {
                        listProvince.add(value)
                    }
                }
            } else if (filterProvinceValue != "" && filterCityValue != "" && filterSizeValue == "") {
                it.data?.map { value ->
                    if (filterProvinceValue == value.area_provinsi && filterCityValue == value.area_kota) {
                        listProvince.add(value)
                    }
                }
            } else if (filterProvinceValue != "" && filterCityValue != "" && filterSizeValue != "") {
                it.data?.map { value ->
                    if (filterProvinceValue == value.area_provinsi && filterCityValue == value.area_kota && filterSizeValue == value.size) {
                        listProvince.add(value)
                    }
                }
            }
            setupRecyclerView(listProvince)
            isLoading(it.status == Status.LOADING && it.data.isNullOrEmpty())
            tvDataEmptyIsVisible(
                it.status == Status.ERROR && it.data.isNullOrEmpty(),
                it.message.toString()
            )


        }
    }

    private fun tvDataEmptyIsVisible(value: Boolean, textAlert: String) {
        binding.tvDataEmpty.visibility(value)
        binding.tvDataEmpty.text = textAlert
    }

    private fun isLoading(value: Boolean) {
        binding.progressBar.visibility(value)
    }

    companion object {
        const val RESULT_CODE = 200
        const val EXTRA_FILTER_PROVINCE = "filter_province"
        const val EXTRA_FILTER_CITY = "filter_city"
        const val EXTRA_FILTER_SIZE = "filter_size"
    }
}