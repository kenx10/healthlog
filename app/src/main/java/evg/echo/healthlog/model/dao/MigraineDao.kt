package evg.echo.healthlog.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import evg.echo.healthlog.model.ent.Migraine

@Dao
interface MigraineDao {
    @Delete
    suspend fun delete(m: Migraine)

    @Insert
    suspend fun insert(entry: Migraine): Long

    @Query("SELECT * FROM migraine ORDER BY timestamp DESC")
    suspend fun getAll(): List<Migraine>

    @Query("SELECT * FROM migraine WHERE timestamp > :ts ORDER BY timestamp DESC")
    fun getAfterTs(ts: Long): LiveData<List<Migraine>>

    @Query("UPDATE migraine SET lon=:lon, lat=:lat WHERE id=:id")
    fun setLocation(id: Long, lon: Double, lat: Double): Int


    @Query(
        "SELECT * FROM migraine " +
                "WHERE timestamp >= :start and timestamp < :end " +
                "ORDER BY timestamp DESC"
    )
    suspend fun getByTs(start: Long, end: Long): List<Migraine>
}