package evg.echo.healthlog.model.ent

import androidx.room.Entity
import androidx.room.PrimaryKey
import evg.echo.healthlog.data.Measure


@Entity(tableName = "panic_attack")
data class Panic(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val timestamp: Long,
    val lon: Double,
    val lat: Double,

    val durationMin: Int = 1,
    val comment: String
) : Measure {

    override fun getTimestampMS(): Long {
        return timestamp
    }
}
