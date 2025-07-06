package evg.echo.healthlog.vm

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import evg.echo.healthlog.services.MeasureService
import evg.echo.healthlog.util.DateAnalyticContainer
import evg.echo.healthlog.util.splitByDateStr
import kotlinx.coroutines.launch

class AnalyticsViewModel(
    private val measureService: MeasureService
) : ViewModel() {
    private val _items = mutableStateListOf<DateAnalyticContainer>()
    val analytics: List<DateAnalyticContainer> get() = _items

    val presDT = mutableStateListOf<Long>()
    val presL = mutableStateListOf<Int>()
    val presDif = mutableStateListOf<Int>()

    private val _sugDT = mutableStateListOf<Long>()
    val sugDT: List<Long> get() = _sugDT
    private val _sug = mutableStateListOf<Float>()
    val sug: List<Float> get() = _sug

    init {
        viewModelScope.launch {
            val mc = measureService.getByDaysBefore(100, 0)
            val dateMeasures = splitByDateStr(mc)

            dateMeasures
                .sortedBy { it.date }
                .forEach {
                    val dayMS = it.date.toEpochDay() * 24 * 3600 * 1000L

                    var migPoint = 0;
                    it.migraines.forEach {
                        migPoint += it.calcMigPoint()
                    }

                    var panPoint = 0
                    it.panics.forEach {
                        panPoint += it.durationMin
                    }

                    _items.add(
                        DateAnalyticContainer(
                            dayMS, migPoint, panPoint
                        )
                    )
                }

            mc.sugars
                .sortedBy { it.timestamp }
                .forEach {
                    _sugDT.add(it.timestamp)
                    _sug.add(it.value)
                }

            mc.pressures.forEach {
                presDT.add(it.timestamp)
                presL.add(it.low)
                presDif.add(it.high - it.low)
            }
        }
    }
}