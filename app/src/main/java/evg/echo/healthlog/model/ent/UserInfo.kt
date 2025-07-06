package evg.echo.healthlog.model.ent

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class InfoType {
    email, fio, birth, gender, use_location
}

enum class Gender(val displayName: String) {
    MALE("Мужской"),
    FEMALE("Женский")
}

@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey
    val type: String,
    val value: String
)
