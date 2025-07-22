package evg.echo.healthlog.vm

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import evg.echo.healthlog.services.MeasureService
import evg.echo.healthlog.util.DateMeasureContainer
import evg.echo.healthlog.util.MeasureContainer
import evg.echo.healthlog.util.sendData
import evg.echo.healthlog.util.splitByDateStr
import kotlinx.coroutines.launch


class HistoryViewModel(
    val measureService: MeasureService
) : ViewModel() {
    private val _items = mutableStateListOf<DateMeasureContainer>()
    val dayMeasures: List<DateMeasureContainer> get() = _items

    private var currentOffset = 0
    private val pageSize = 7
    var isLoading by mutableStateOf(false)
        private set

    /***************************************/

    init {
        viewModelScope.launch {
            loadMore()
        }
    }

    /***************************************/

    fun loadMore() {
        if (isLoading) return

        isLoading = true
        viewModelScope.launch {
            val mc = measureService.getByDaysBefore(pageSize + currentOffset, currentOffset)
            _items.addAll(splitByDateStr(mc))

            currentOffset += pageSize
            isLoading = false
        }
    }

    fun delete(measure: Any) {
        viewModelScope.launch {
            measureService.delete(measure)
        }
    }

    /***************************************/

    fun sendLastYearData(context: Context) {
        viewModelScope.launch {
            val container = measureService.getByDaysBefore(365, 0)
            sendData(context, container)
        }
    }
}