package evg.echo.healthlog.util

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter

fun sendData(context: Context, measureContainer: MeasureContainer) {
    val csvBuffer = StringBuffer()

    /////////
    csvBuffer.append("Мигрени")
    csvBuffer.append('\n')

    csvBuffer.append("Дата и время;")
    csvBuffer.append("Интенсивность;")
    csvBuffer.append("Продолжительность (мин.);")
    csvBuffer.append("Комментарий;")
    csvBuffer.append('\n')

    measureContainer.migraines.forEach {
        csvBuffer.append(toDateTimeFileFormat(it.timestamp))
        csvBuffer.append(';')

        when (it.value) {
            1 -> csvBuffer.append("Слабая")
            2 -> csvBuffer.append("Средняя")
            3 -> csvBuffer.append("Сильная")
        }
        csvBuffer.append(';')

        csvBuffer.append(it.durationMin)
        csvBuffer.append(';')

        csvBuffer.append(it.comment)
        csvBuffer.append(';')

        csvBuffer.append('\n')
    }


    /////////
    csvBuffer.append('\n')
    csvBuffer.append("Давление")
    csvBuffer.append('\n')

    csvBuffer.append("Дата и время;")
    csvBuffer.append("Верхнее (мм. рт. ст.);")
    csvBuffer.append("Нижнее (мм. рт. ст.);")
    csvBuffer.append("Комментарий;")
    csvBuffer.append('\n')

    measureContainer.pressures.forEach {
        csvBuffer.append(toDateTimeFileFormat(it.timestamp))
        csvBuffer.append(';')

        csvBuffer.append(it.high)
        csvBuffer.append(';')

        csvBuffer.append(it.low)
        csvBuffer.append(';')

        csvBuffer.append(it.comment)
        csvBuffer.append(';')

        csvBuffer.append('\n')
    }


    /////////
    csvBuffer.append('\n')
    csvBuffer.append("Сахар")
    csvBuffer.append('\n')

    csvBuffer.append("Дата и время;")
    csvBuffer.append("Значение (ммоль/л);")
    csvBuffer.append("Комментарий;")
    csvBuffer.append('\n')

    measureContainer.sugars.forEach {
        csvBuffer.append(toDateTimeFileFormat(it.timestamp))
        csvBuffer.append(';')

        csvBuffer.append(it.value)
        csvBuffer.append(';')

        csvBuffer.append(it.comment)
        csvBuffer.append(';')

        csvBuffer.append('\n')
    }


    /////////
    csvBuffer.append('\n')
    csvBuffer.append("ПА")
    csvBuffer.append('\n')

    csvBuffer.append("Дата и время;")
    csvBuffer.append("Продолжительность (мин.);")
    csvBuffer.append("Комментарий;")
    csvBuffer.append('\n')

    measureContainer.panics.forEach {
        csvBuffer.append(toDateTimeFileFormat(it.timestamp))
        csvBuffer.append(';')

        csvBuffer.append(it.durationMin)
        csvBuffer.append(';')

        csvBuffer.append(it.comment)
        csvBuffer.append(';')

        csvBuffer.append('\n')
    }


    /*******************************/

    val outputDir = context.cacheDir
    try {
        val outputFile = File.createTempFile("health-data-", ".csv", outputDir)
        writeToFile(outputFile, csvBuffer.toString())

        val uri = FileProvider.getUriForFile(
            context,
            context.packageName,
            outputFile
        )


        val share = Intent()
        share.setAction(Intent.ACTION_SEND)
        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION and Intent.FLAG_ACTIVITY_NEW_TASK)
        share.setType("text/csv")
        share.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(Intent.createChooser(share, "Отправить как файл"))
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}

private fun writeToFile(outputFile: File, data: String) {
    try {
        val outputStreamWriter = OutputStreamWriter(FileOutputStream(outputFile))
        outputStreamWriter.write("\ufeff")
        outputStreamWriter.write(data)
        outputStreamWriter.close()
    } catch (e: IOException) {
        Log.e("Exception", "File write failed: $e")
    }
}