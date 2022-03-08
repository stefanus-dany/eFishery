package id.stefanusdany.efishery.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.stefanusdany.efishery.data.source.local.entity.CommodityEntity

@Dao
interface CommodityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(commodityEntity: List<CommodityEntity>)

    @Query("DELETE FROM commodityentity")
    fun deleteALlCommodity()

    @Query(
        "SELECT * from commodityentity ORDER BY komoditas ASC"
    )
    fun getAllCommodity(): LiveData<List<CommodityEntity>>

    @Query(
        "SELECT * FROM commodityentity WHERE uuid LIKE '%' || :searchText || '%'" +
                "OR komoditas LIKE '%' || :searchText || '%'" +
                "OR area_provinsi LIKE '%' || :searchText || '%'" +
                "OR area_kota LIKE '%' || :searchText || '%'" +
                "OR size LIKE '%' || :searchText || '%'" +
                "OR price LIKE '%' || :searchText || '%'" +
                "OR tgl_parsed LIKE '%' || :searchText || '%'" +
                "OR timestamp LIKE '%' || :searchText || '%'" +
                " ORDER BY komoditas ASC"
    )
    fun getSearchCommodity(
        searchText: String
    ): LiveData<List<CommodityEntity>>
}