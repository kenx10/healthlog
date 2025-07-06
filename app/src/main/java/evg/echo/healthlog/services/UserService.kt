package evg.echo.healthlog.services

import androidx.lifecycle.asFlow
import evg.echo.healthlog.model.dao.UserInfoDao
import evg.echo.healthlog.model.ent.Gender
import evg.echo.healthlog.model.ent.InfoType
import evg.echo.healthlog.model.ent.UserInfo
import kotlinx.coroutines.flow.first

class UserService(
    private val userInfoDao: UserInfoDao
) {
    suspend fun getInfo(): Map<InfoType, String> {
        val result = mutableMapOf<InfoType, String>()
        userInfoDao.getAll().asFlow().first().forEach {
            result.put(InfoType.valueOf(it.type), it.value)
        }
        return result
    }

    suspend fun useLocation(): Boolean {
        val ul = userInfoDao.getById(InfoType.use_location)
        if (null != ul)
            return ul.value.toBoolean()
        return false
    }

    suspend fun setUseLocation(value: Boolean) {
        userInfoDao.insert(UserInfo("${InfoType.use_location}", "$value"))
    }

    suspend fun getFIO(): String? {
        return userInfoDao.getById(InfoType.fio)?.value
    }

    suspend fun setFIO(value: String) {
        userInfoDao.insert(UserInfo("${InfoType.fio}", value))
    }

    suspend fun getBirth(): Long? {
        return userInfoDao.getById(InfoType.birth)?.value?.toLong()
    }

    suspend fun setBirth(value: Long) {
        userInfoDao.insert(UserInfo("${InfoType.birth}", "$value"))
    }

    suspend fun getEmail(): String? {
        return userInfoDao.getById(InfoType.email)?.value
    }

    suspend fun setEmail(value: String) {
        userInfoDao.insert(UserInfo("${InfoType.email}", value))
    }

    suspend fun getGender(): Gender? {
        val gStr = userInfoDao.getById(InfoType.gender)?.value
        if (null == gStr)
            return null

        return Gender.valueOf(gStr)
    }

    suspend fun setGender(value: Gender) {
        userInfoDao.insert(UserInfo("${InfoType.gender}", "$value"))
    }
}