package evg.echo.healthlog.util

import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.text.DecimalFormat

@OptIn(FormatStringsInDatetimeFormats::class)
val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM")

@OptIn(FormatStringsInDatetimeFormats::class)
val dateTimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd.MM")

@OptIn(FormatStringsInDatetimeFormats::class)
val dateTimeFileFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")


fun toDateFormat(mills: Long): String {
    val instant = Instant.ofEpochMilli(mills)
    return dateFormat.format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()))
}

fun toDateTimeFormat(mills: Long): String {
    val instant = Instant.ofEpochMilli(mills)
    return dateTimeFormat.format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()))
}

fun toDateTimeFileFormat(mills: Long): String {
    val instant = Instant.ofEpochMilli(mills)
    return dateTimeFileFormat.format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()))
}

class DecimalExtCartesianValueFormatter(
    val suff: String = "баллов"
) : CartesianValueFormatter {
    override fun format(
        context: CartesianMeasuringContext,
        value: Double,
        verticalAxisPosition: Axis.Position.Vertical?,
    ): CharSequence {
        return DecimalFormat("#.##").format(value) + " $suff"
    }
}