package id.stefanusdany.efishery.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.stefanusdany.efishery.data.source.local.entity.AreaEntity

@Dao
interface AreaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(areaEntity: List<AreaEntity>)

    @Query("DELETE FROM areaentity")
    fun deleteALlArea()

    @Query("SELECT * from areaentity")
    fun getArea(): LiveData<List<AreaEntity>>
}