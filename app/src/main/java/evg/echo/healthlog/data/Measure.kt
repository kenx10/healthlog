package evg.echo.healthlog.data

import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import kotlin.time.ExperimentalTime

@OptIn(FormatStringsInDatetimeFormats::class)
val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

@OptIn(FormatStringsInDatetimeFormats::class)
val timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

interface Measure {
    fun getTimestampMS(): Long

    @OptIn(ExperimentalTime::class)
    fun getAsLocalDateTime(): LocalDateTime {
        val instant = Instant.ofEpochMilli(getTimestampMS())
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    }

    @OptIn(ExperimentalTime::class)
    fun getAsFormattedTime(): String {
        val localDateTime = getAsLocalDateTime()
        return timeFormat.format(localDateTime)
    }
}