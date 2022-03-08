package id.stefanusdany.efishery.ui.filter

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import id.stefanusdany.efishery.R
import id.stefanusdany.efishery.data.source.local.entity.AreaEntity
import id.stefanusdany.efishery.databinding.ActivityFilterBinding
import id.stefanusdany.efishery.ui.homepage.MainActivity
import id.stefanusdany.efishery.ui.homepage.MainActivity.Companion.RESULT_CODE
import id.stefanusdany.efishery.utils.Helper.visibility
import id.stefanusdany.efishery.vo.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding
    private val viewModel: FilterViewModel by viewModel()
    private var filterProvince = ""
    private var filterCity = ""
    private var filterSize = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.tv_add_filter)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getFilterProvinceAndCity()
        getFilterSize()

        binding.btnAddFilter.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra(MainActivity.EXTRA_FILTER_PROVINCE, filterProvince)
            resultIntent.putExtra(MainActivity.EXTRA_FILTER_CITY, filterCity)
            resultIntent.putExtra(MainActivity.EXTRA_FILTER_SIZE, filterSize)
            setResult(RESULT_CODE, resultIntent)
            finish()
        }

    }

    private fun getFilterProvinceAndCity() {
        viewModel.getAllArea().observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    //filter province
                    isLoading(false)
                    val listProvince = mutableListOf<String>()
                    resource.data?.let { data ->
                        data.map { area ->
                            listProvince.add(area.province)
                        }
                    }

                    binding.etFilterProvince.apply {
                        setAdapter(
                            ArrayAdapter(
                                this@FilterActivity,
                                R.layout.dropdown_list,
                                listProvince.distinct()
                            )
                        )
                        setOnItemClickListener { parent, view, position, rowId ->
                            filterProvince = parent?.getItemAtPosition(position).toString()
                            binding.etFilterCity.text = null
                            resource.data?.let { getFilterCity(filterProvince, it) }
                        }
                    }
                }
                Status.LOADING -> isLoading(true)
                Status.ERROR -> Snackbar.make(
                    binding.root,
                    resource.message.toString(),
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }
    }

    private fun getFilterCity(selectedProvince: String, data: List<AreaEntity>) {
        val listCity = mutableListOf<String>()
        data.map { area ->
            if (selectedProvince == area.province) {
                listCity.add(area.city)
            }
        }

        binding.etFilterCity.apply {
            setAdapter(
                ArrayAdapter(
                    this@FilterActivity,
                    R.layout.dropdown_list,
                    listCity
                )

            )
            setOnItemClickListener { parent, view, position, rowId ->
                filterCity = parent?.getItemAtPosition(position).toString()
            }
        }
    }

    private fun getFilterSize() {
        viewModel.getAllSize().observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    isLoading(false)
                    val listSize = mutableListOf<String>()
                    resource.data?.let {
                        it.map { size ->
                            listSize.add(size.size)
                        }
                        binding.etFilterSize.apply {
                            setAdapter(
                                ArrayAdapter(
                                    this@FilterActivity,
                                    R.layout.dropdown_list,
                                    listSize
                                )
                            )
                            setOnItemClickListener { parent, view, position, rowId ->
                                filterSize = parent?.getItemAtPosition(position).toString()
                            }
                        }
                    }
                }
                Status.LOADING -> isLoading(true)
                Status.ERROR -> Snackbar.make(
                    binding.root,
                    resource.message.toString(),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun isLoading(value: Boolean) {
        binding.progressBar.visibility(value)
    }

}