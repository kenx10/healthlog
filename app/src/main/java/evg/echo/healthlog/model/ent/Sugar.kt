package evg.echo.healthlog.model.ent

import androidx.room.Entity
import androidx.room.PrimaryKey
import evg.echo.healthlog.data.Measure

@Entity(tableName = "sugar")
data class Sugar(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val timestamp: Long,
    val lon: Double,
    val lat: Double,

    val value: Float,
    val comment: String
) : Measure {

    override fun getTimestampMS(): Long {
        return timestamp
    }

}
