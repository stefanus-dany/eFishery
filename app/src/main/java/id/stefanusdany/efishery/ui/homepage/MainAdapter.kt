package id.stefanusdany.efishery.ui.homepage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.stefanusdany.efishery.data.source.local.entity.CommodityEntity
import id.stefanusdany.efishery.databinding.ItemListBinding
import id.stefanusdany.efishery.utils.DiffCallback

class MainAdapter internal constructor(private val context: Context) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var data = ArrayList<CommodityEntity>()

    fun setData(data: List<CommodityEntity>?) {
        if (data == null) return
        val diffCallback = DiffCallback(this.data, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.data.clear()
        this.data.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = data[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        private val binding: ItemListBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CommodityEntity) {
            binding.itemList = data
        }
    }
}