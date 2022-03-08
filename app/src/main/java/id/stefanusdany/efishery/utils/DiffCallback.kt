package id.stefanusdany.efishery.utils

import androidx.recyclerview.widget.DiffUtil
import id.stefanusdany.efishery.data.source.local.entity.CommodityEntity

class DiffCallback(
    private val mOldList: List<CommodityEntity>,
    private val mNewList: List<CommodityEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList[oldItemPosition].uuid == mNewList[newItemPosition].uuid
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItemPosition == newItemPosition
}