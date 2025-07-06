package evg.echo.healthlog.model

import androidx.room.Database
import androidx.room.RoomDatabase
import evg.echo.healthlog.model.dao.MigraineDao
import evg.echo.healthlog.model.dao.PanicDao
import evg.echo.healthlog.model.dao.PressureDao
import evg.echo.healthlog.model.dao.SugarDao
import evg.echo.healthlog.model.dao.UserInfoDao
import evg.echo.healthlog.model.ent.Migraine
import evg.echo.healthlog.model.ent.Panic
import evg.echo.healthlog.model.ent.Pressure
import evg.echo.healthlog.model.ent.Sugar
import evg.echo.healthlog.model.ent.UserInfo

@Database(
    entities = [Migraine::class, Pressure::class, Sugar::class, UserInfo::class, Panic::class],
    version = 1,
    exportSchema = false
)
abstract class AppDB : RoomDatabase() {
    abstract fun migraineDao(): MigraineDao
    abstract fun pressureDao(): PressureDao
    abstract fun sugarDao(): SugarDao
    abstract fun userInfoDao(): UserInfoDao
    abstract fun panicDao(): PanicDao
}