package evg.echo.healthlog.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import evg.echo.healthlog.model.ent.Pressure

@Dao
interface PressureDao {
    @Delete
    suspend fun delete(p: Pressure)

    @Insert
    suspend fun insert(entry: Pressure): Long

    @Query("SELECT * FROM pressure WHERE timestamp > :ts ORDER BY timestamp DESC")
    fun getAfterTs(ts: Long): LiveData<List<Pressure>>

    @Query("UPDATE pressure SET lon=:lon, lat=:lat WHERE id=:id")
    fun setLocation(id: Long, lon: Double, lat: Double): Int

    @Query(
        "SELECT * FROM pressure " +
                "WHERE timestamp >= :start and timestamp < :end " +
                "ORDER BY timestamp DESC"
    )
    suspend fun getByTs(start: Long, end: Long): List<Pressure>
}