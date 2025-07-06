package evg.echo.healthlog.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import evg.echo.healthlog.model.ent.Panic

@Dao
interface PanicDao {
    @Delete
    suspend fun delete(p: Panic)

    @Insert
    suspend fun insert(entry: Panic): Long

    @Query("SELECT * FROM panic_attack ORDER BY timestamp DESC")
    fun getAll(): List<Panic>

    @Query("SELECT * FROM panic_attack WHERE timestamp > :ts ORDER BY timestamp DESC")
    fun getAfterTs(ts: Long): LiveData<List<Panic>>

    @Query("UPDATE panic_attack SET lon=:lon, lat=:lat WHERE id=:id")
    fun setLocation(id: Long, lon: Double, lat: Double): Int

    @Query(
        "SELECT * FROM panic_attack " +
                "WHERE timestamp >= :start and timestamp < :end " +
                "ORDER BY timestamp DESC"
    )
    suspend fun getByTs(start: Long, end: Long): List<Panic>
}