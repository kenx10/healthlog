package evg.echo.healthlog.util

import evg.echo.healthlog.model.ent.Migraine
import evg.echo.healthlog.model.ent.Panic
import evg.echo.healthlog.model.ent.Pressure
import evg.echo.healthlog.model.ent.Sugar
import org.threeten.bp.LocalDate

class MeasureContainer(
    val migraines: MutableList<Migraine> = mutableListOf(),
    val pressures: MutableList<Pressure> = mutableListOf(),
    val sugars: MutableList<Sugar> = mutableListOf(),
    val panics: MutableList<Panic> = mutableListOf()
)

class DateMeasureContainer(
    val date: LocalDate,
    val migraines: MutableList<Migraine> = mutableListOf(),
    val pressures: MutableList<Pressure> = mutableListOf(),
    val sugars: MutableList<Sugar> = mutableListOf(),
    val panics: MutableList<Panic> = mutableListOf()
)

class DateAnalyticContainer(
    val dateEpocMs: Long,
    val migraines: Int,
    val panics: Int
)


fun splitByDateStr(
    mc: MeasureContainer
): List<DateMeasureContainer> {
    return splitByDateStr(
        mc.migraines, mc.pressures, mc.sugars, mc.panics
    )
}

fun splitByDateStr(
    migraines: List<Migraine> = mutableListOf(),
    pressures: List<Pressure> = mutableListOf(),
    sugars: List<Sugar> = mutableListOf(),
    panics: List<Panic> = mutableListOf()
): List<DateMeasureContainer> {
    val dayMap: MutableMap<LocalDate, MeasureContainer> = mutableMapOf()

    migraines.forEach { m ->
        val localDate = m.getAsLocalDateTime().toLocalDate()

        dayMap.getOrPut(localDate) {
            MeasureContainer()
        }.migraines.add(m)

    }

    pressures.forEach { p ->
        val localDate = p.getAsLocalDateTime().toLocalDate()

        dayMap.getOrPut(localDate) {
            MeasureContainer()
        }.pressures.add(p)
    }

    sugars.forEach { s ->
        val localDate = s.getAsLocalDateTime().toLocalDate()

        dayMap.getOrPut(localDate) {
            MeasureContainer()
        }.sugars.add(s)
    }

    panics.forEach { p ->
        val localDate = p.getAsLocalDateTime().toLocalDate()

        dayMap.getOrPut(localDate) {
            MeasureContainer()
        }.panics.add(p)
    }


    val res = mutableListOf<DateMeasureContainer>()
    dayMap.forEach {
        res.add(
            DateMeasureContainer(
                date = it.key,
                migraines = it.value.migraines,
                pressures = it.value.pressures,
                sugars = it.value.sugars,
                panics = it.value.panics
            )
        )
    }

    res.sortByDescending { it.date }

    return res
}