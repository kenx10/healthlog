package evg.echo.healthlog.vm

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import evg.echo.healthlog.services.MeasureService
import evg.echo.healthlog.util.splitByDateStr
import kotlinx.coroutines.launch

class AnalyticsViewModel(
    private val measureService: MeasureService
) : ViewModel() {
    val migDT = mutableStateListOf<Long>()
    val mig = mutableStateListOf<Int>()

    val panDT = mutableStateListOf<Long>()
    val pan = mutableStateListOf<Int>()

    val presDT = mutableStateListOf<Long>()
    val presL = mutableStateListOf<Int>()
    val presDif = mutableStateListOf<Int>()

    val sugDT = mutableStateListOf<Long>()
    val sug = mutableStateListOf<Float>()

    init {
        viewModelScope.launch {
            val mc = measureService.getByDaysBefore(100, 0)
            val dateMeasures = splitByDateStr(mc)

            dateMeasures
                .sortedBy { it.date }
                .forEach {
                    val dayMS = it.date.toEpochDay() * 24 * 3600 * 1000L

                    var migPoint = 0
                    it.migraines.forEach {
                        migPoint += it.calcMigPoint()
                    }

                    var panPoint = 0
                    it.panics.forEach {
                        panPoint += it.durationMin
                    }

                    if (0 < migPoint) {
                        migDT.add(dayMS)
                        mig.add(migPoint)
                    }

                    if (0 < panPoint) {
                        panDT.add(dayMS)
                        pan.add(panPoint)
                    }
                }

            mc.sugars
                .sortedBy { it.timestamp }
                .forEach {
                    sugDT.add(it.timestamp)
                    sug.add(it.value)
                }

            mc.pressures
                .sortedBy { it.timestamp }
                .forEach {
                    presDT.add(it.timestamp)
                    presL.add(it.low)
                    presDif.add(it.high - it.low)
                }
        }
    }
}