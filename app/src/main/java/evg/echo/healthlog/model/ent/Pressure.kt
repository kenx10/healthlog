package evg.echo.healthlog.model.ent

import androidx.room.Entity
import androidx.room.PrimaryKey
import evg.echo.healthlog.data.Measure

@Entity(tableName = "pressure")
data class Pressure(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val timestamp: Long,
    val lon: Double,
    val lat: Double,

    val low: Int,
    val high: Int,
    val comment: String
) : Measure {

    override fun getTimestampMS(): Long {
        return timestamp
    }
}
