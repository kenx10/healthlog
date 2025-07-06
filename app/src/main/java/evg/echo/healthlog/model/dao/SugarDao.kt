package evg.echo.healthlog.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import evg.echo.healthlog.model.ent.Sugar

@Dao
interface SugarDao {
    @Delete
    suspend fun delete(s: Sugar)

    @Insert
    suspend fun insert(entry: Sugar): Long

    @Query("SELECT * FROM sugar WHERE timestamp > :ts ORDER BY timestamp DESC")
    fun getAfterTs(ts: Long): LiveData<List<Sugar>>

    @Query("UPDATE sugar SET lon=:lon, lat=:lat WHERE id=:id")
    fun setLocation(id: Long, lon: Double, lat: Double): Int

    @Query(
        "SELECT * FROM sugar " +
                "WHERE timestamp >= :start and timestamp < :end " +
                "ORDER BY timestamp DESC"
    )
    suspend fun getByTs(start: Long, end: Long): List<Sugar>
}