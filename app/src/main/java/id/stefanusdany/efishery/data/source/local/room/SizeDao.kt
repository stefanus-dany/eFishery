package id.stefanusdany.efishery.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.stefanusdany.efishery.data.source.local.entity.SizeEntity

@Dao
interface SizeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sizeEntity: List<SizeEntity>)

    @Query("DELETE FROM sizeentity")
    fun deleteALlSize()

    @Query("SELECT * from sizeentity")
    fun getSize(): LiveData<List<SizeEntity>>
}