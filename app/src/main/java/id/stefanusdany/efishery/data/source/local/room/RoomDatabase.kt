package id.stefanusdany.efishery.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.stefanusdany.efishery.data.source.local.entity.AreaEntity
import id.stefanusdany.efishery.data.source.local.entity.CommodityEntity
import id.stefanusdany.efishery.data.source.local.entity.SizeEntity

@Database(entities = [CommodityEntity::class, AreaEntity::class, SizeEntity::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun commodityDao(): CommodityDao
    abstract fun areaDao(): AreaDao
    abstract fun sizeDao(): SizeDao
}