package evg.echo.healthlog.model.ent

import androidx.room.Entity
import androidx.room.PrimaryKey
import evg.echo.healthlog.data.Measure
import kotlin.math.pow


@Entity(tableName = "migraine")
data class Migraine(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val timestamp: Long,
    val lon: Double,
    val lat: Double,

    val value: Int,
    val durationMin: Int = 1,
    val comment: String
) : Measure {

    override fun getTimestampMS(): Long {
        return timestamp
    }

    fun calcMigPoint(): Int {
        return (2.0.pow(value) * durationMin).toInt()
    }
}
