package id.stefanusdany.efishery.ui.addData

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.widget.RxTextView
import id.stefanusdany.efishery.R
import id.stefanusdany.efishery.data.source.local.entity.AreaEntity
import id.stefanusdany.efishery.data.source.remote.StatusResponse
import id.stefanusdany.efishery.data.source.remote.model.CommodityModel
import id.stefanusdany.efishery.databinding.ActivityAddDataBinding
import id.stefanusdany.efishery.ui.homepage.MainActivity
import id.stefanusdany.efishery.utils.Helper.visibility
import id.stefanusdany.efishery.vo.Status
import io.reactivex.Observable
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.sql.Timestamp
import java.util.*


class AddDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDataBinding
    private val viewModel: AddDataViewModel by viewModel()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.tv_add_data)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getArea()
        getSize()

        val komoditasStream = RxTextView.textChanges(binding.etKomoditas)
            .skipInitialValue()
            .map { komoditas ->
                komoditas.toString().trim().isEmpty()
            }
        komoditasStream.subscribe {
            showKomoditasExistAlert(it)
        }

        val areaProvinsiStream = RxTextView.textChanges(binding.etAreaProvinsi)
            .skipInitialValue()
            .map { areaProvinsi ->
                areaProvinsi.toString().trim().isEmpty()
            }
        areaProvinsiStream.subscribe {
            showAreaProvinsiAlert(it)
        }

        val areaKotaStream = RxTextView.textChanges(binding.etAreaKota)
            .skipInitialValue()
            .map { areaKota ->
                areaKota.toString().trim().isEmpty()
            }
        areaKotaStream.subscribe {
            showAreaKotaAlert(it)
        }

        val sizeStream = RxTextView.textChanges(binding.etSize)
            .skipInitialValue()
            .map { size ->
                size.toString().trim().isEmpty()
            }
        sizeStream.subscribe {
            showSizeAlert(it)
        }

        val priceStream = RxTextView.textChanges(binding.etPrice)
            .skipInitialValue()
            .map { price ->
                price.toString().trim().isEmpty()
            }
        priceStream.subscribe {
            showPriceAlert(it)
        }

        val invalidFieldsStream = Observable.combineLatest(
            komoditasStream,
            areaProvinsiStream,
            areaKotaStream,
            sizeStream,
            priceStream
        ) { komoditasInvalid: Boolean, areaProvinsiInvalid: Boolean, areaKotaInvalid: Boolean, sizeInvalid: Boolean, priceInvalid: Boolean ->
            !komoditasInvalid && !areaProvinsiInvalid && !areaKotaInvalid && !sizeInvalid && !priceInvalid
        }
        invalidFieldsStream.subscribe { isValid ->
            if (isValid) {
                binding.btnAddData.isEnabled = true
                binding.btnAddData.setBackgroundResource(R.drawable.btn_primary)
            } else {
                binding.btnAddData.isEnabled = false
                binding.btnAddData.setBackgroundResource(R.drawable.btn_secondary)
            }
        }

        binding.btnAddData.setOnClickListener {
            postCommodity()
        }
    }

    private fun setCity(selectedProvince: String, data: List<AreaEntity>) {
        val listCity = mutableListOf<String>()
        data.map { area ->
            if (selectedProvince == area.province) {
                listCity.add(area.city)
            }
        }

        binding.etAreaKota.setAdapter(
            ArrayAdapter(
                this@AddDataActivity,
                R.layout.dropdown_list,
                listCity
            )

        )
    }

    private fun getArea() {
        viewModel.getAllArea().observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    isLoading(false)
                    val listProvince = mutableListOf<String>()
                    resource.data?.let { data ->
                        data.map { area ->
                            listProvince.add(area.province)
                        }
                    }

                    binding.etAreaProvinsi.apply {
                        setAdapter(
                            ArrayAdapter(
                                this@AddDataActivity,
                                R.layout.dropdown_list,
                                listProvince.distinct()
                            )
                        )
                        setOnItemClickListener { parent, view, position, rowId ->
                            val itemSelected = parent?.getItemAtPosition(position).toString()
                            binding.etAreaKota.text = null
                            resource.data?.let { setCity(itemSelected, it) }
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

    private fun getSize() {
        viewModel.getAllSize().observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    isLoading(false)
                    val listSize = mutableListOf<String>()
                    resource.data?.let {
                        it.map { size ->
                            listSize.add(size.size)
                        }
                        binding.etSize.setAdapter(
                            ArrayAdapter(
                                this@AddDataActivity,
                                R.layout.dropdown_list,
                                listSize
                            )
                        )
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

    private fun postCommodity() {
        isLoading(true)
        viewModel.postCommodity(
            listOf(
                CommodityModel(
                    getUUID(),
                    binding.etKomoditas.text.toString().trim(),
                    binding.tilAreaProvinsi.editText?.text.toString().trim(),
                    binding.tilAreaKota.editText?.text.toString().trim(),
                    binding.tilSize.editText?.text.toString().trim(),
                    binding.etPrice.text.toString().trim(),
                    getCalendar(),
                    getTimeStamp()
                )
            )
        ).observe(this) {
            isLoading(false)
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    Snackbar.make(
                        binding.root,
                        "Commodity has been posted to API",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@AddDataActivity, MainActivity::class.java))
                    finishAffinity()
                }
                else -> {
                    Snackbar.make(
                        binding.root,
                        "${it.message}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun getUUID() = UUID.randomUUID().toString()

    private fun getCalendar() = Timestamp(System.currentTimeMillis()).toString()

    private fun getTimeStamp() = (System.currentTimeMillis() / 1000).toString()

    private fun showKomoditasExistAlert(isNotValid: Boolean) {
        binding.etKomoditas.error = if (isNotValid) getString(R.string.komoditas_empty) else null
    }

    private fun showAreaProvinsiAlert(isNotValid: Boolean) {
        binding.etAreaProvinsi.error =
            if (isNotValid) getString(R.string.area_provinsi_empty) else null
    }

    private fun showAreaKotaAlert(isNotValid: Boolean) {
        binding.etAreaKota.error = if (isNotValid) getString(R.string.area_kota_empty) else null
    }

    private fun showSizeAlert(isNotValid: Boolean) {
        binding.etSize.error = if (isNotValid) getString(R.string.size_empty) else null
    }

    private fun showPriceAlert(isNotValid: Boolean) {
        binding.etPrice.error = if (isNotValid) getString(R.string.price_empty) else null
    }

    private fun isLoading(value: Boolean) {
        binding.progressBar.visibility(value)
    }
}