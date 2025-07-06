package evg.echo.healthlog.services

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import evg.echo.healthlog.model.dao.MigraineDao
import evg.echo.healthlog.model.dao.PanicDao
import evg.echo.healthlog.model.dao.PressureDao
import evg.echo.healthlog.model.dao.SugarDao
import evg.echo.healthlog.model.ent.Migraine
import evg.echo.healthlog.model.ent.Panic
import evg.echo.healthlog.model.ent.Pressure
import evg.echo.healthlog.model.ent.Sugar
import evg.echo.healthlog.util.MeasureContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.random.Random

class MeasureService(
    private val migraineDao: MigraineDao,
    private val pressureDao: PressureDao,
    private val sugarDao: SugarDao,
    private val panicDao: PanicDao,
    private val userService: UserService,
    private val fusedLocationClient: FusedLocationProviderClient,
) {
    private val dbScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun cleanup() {
        dbScope.cancel()
    }

    /***************************/

    fun generate() {
        val intervalMs: Long = 30 * 24 * 3600 * 1000L
        val base: Long = Date().time - intervalMs


        (0..100).forEach {
            saveSug(
                sugar = 2 + 10 * Random.nextFloat(),
                comment = "",
                timestamp = base + Random.nextLong(intervalMs)
            )

            val low = 50 + Random.nextInt(50)
            savePres(
                high = low + Random.nextInt(60),
                low = low,
                comment = "",
                timestamp = base + Random.nextLong(intervalMs)
            )

            saveMig(
                pain = 1 + Random.nextInt(3),
                durationMinutes = 10 + Random.nextInt(60),
                comment = "",
                timestamp = base + Random.nextLong(intervalMs)
            )

            savePan(
                durationMinutes = 10 + Random.nextInt(60),
                comment = "",
                timestamp = base + Random.nextLong(intervalMs)
            )
        }
    }

    /***************************/

    suspend fun getByDaysBefore(start: Int, end: Int = 0): MeasureContainer {
        val time = Date().time
        val startMs = start * 24 * 3600 * 1000L
        val endMs = end * 24 * 3600 * 1000L

        val mc = MeasureContainer()
        mc.migraines.addAll(migraineDao.getByTs(time - startMs, time - endMs))
        mc.panics.addAll(panicDao.getByTs(time - startMs, time - endMs))
        mc.pressures.addAll(pressureDao.getByTs(time - startMs, time - endMs))
        mc.sugars.addAll(sugarDao.getByTs(time - startMs, time - endMs))

        return mc
    }

    /***************************/

    @SuppressLint("MissingPermission")
    fun saveSug(sugar: Float, comment: String, timestamp: Long = Date().time) {
        dbScope.launch {
            val sugId = sugarDao.insert(
                Sugar(
                    timestamp = timestamp,
                    lon = 0.0,
                    lat = 0.0,
                    value = sugar,
                    comment = comment
                )
            )

            if (userService.useLocation()) {
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    dbScope.launch {
                        if (null != it)
                            sugarDao.setLocation(sugId, it.longitude, it.latitude)
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun savePres(high: Int, low: Int, comment: String, timestamp: Long = Date().time) {
        dbScope.launch {
            val presId = pressureDao.insert(
                Pressure(
                    timestamp = timestamp,
                    lon = 0.0,
                    lat = 0.0,
                    high = high,
                    low = low,
                    comment = comment
                )
            )

            if (userService.useLocation()) {
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    dbScope.launch {
                        if (null != it)
                            pressureDao.setLocation(presId, it.longitude, it.latitude)
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun saveMig(pain: Int, durationMinutes: Int, comment: String, timestamp: Long = Date().time) {
        dbScope.launch {
            var migId = migraineDao.insert(
                Migraine(
                    timestamp = timestamp,
                    lon = 0.0,
                    lat = 0.0,
                    value = pain,
                    durationMin = durationMinutes,
                    comment = comment
                )
            )

            if (userService.useLocation()) {
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    dbScope.launch {
                        if (null != it)
                            migraineDao.setLocation(migId, it.longitude, it.latitude)
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun savePan(durationMinutes: Int, comment: String, timestamp: Long = Date().time) {
        dbScope.launch {
            var panId = panicDao.insert(
                Panic(
                    timestamp = timestamp,
                    lon = 0.0,
                    lat = 0.0,
                    durationMin = durationMinutes,
                    comment = comment
                )
            )

            if (userService.useLocation()) {
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    dbScope.launch {
                        if (null != it)
                            panicDao.setLocation(panId, it.longitude, it.latitude)
                    }
                }
            }
        }
    }

    suspend fun delete(measure: Any) {
        when (measure) {
            is Migraine -> migraineDao.delete(measure)
            is Panic -> panicDao.delete(measure)
            is Sugar -> sugarDao.delete(measure)
            is Pressure -> pressureDao.delete(measure)
        }

    }


}