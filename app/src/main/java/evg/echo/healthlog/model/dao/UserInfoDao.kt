package evg.echo.healthlog.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import evg.echo.healthlog.model.ent.InfoType
import evg.echo.healthlog.model.ent.UserInfo

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: UserInfo)

    @Query("SELECT * FROM user_info")
    fun getAll(): LiveData<List<UserInfo>>

    @Query("SELECT * FROM user_info WHERE type = :type")
    suspend fun getById(type: InfoType): UserInfo?
}