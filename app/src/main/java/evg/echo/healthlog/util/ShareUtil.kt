package evg.echo.healthlog.util

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import evg.echo.healthlog.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter

fun sendData(context: Context, measureContainer: MeasureContainer) {


    val csvBuffer = StringBuffer()

    /////////
    csvBuffer.append(context.getString(R.string.export_mig))
    csvBuffer.append('\n')

    csvBuffer.append("${context.getString(R.string.export_dt)} ;")
    csvBuffer.append("${context.getString(R.string.export_intence)} ;")
    csvBuffer.append("${context.getString(R.string.export_dur)} ;")
    csvBuffer.append("${context.getString(R.string.export_comment)} ;")
    csvBuffer.append('\n')

    measureContainer.migraines.forEach {
        csvBuffer.append(toDateTimeFileFormat(it.timestamp))
        csvBuffer.append(';')

        when (it.value) {
            1 -> csvBuffer.append(context.getString(R.string.mig_weak))
            2 -> csvBuffer.append(context.getString(R.string.mig_millde))
            3 -> csvBuffer.append(context.getString(R.string.mig_strong))
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
    csvBuffer.append(context.getString(R.string.export_pres))
    csvBuffer.append('\n')

    csvBuffer.append("${context.getString(R.string.export_dt)} ;")
    csvBuffer.append("${context.getString(R.string.export_up)} ;")
    csvBuffer.append("${context.getString(R.string.export_down)} ;")
    csvBuffer.append("${context.getString(R.string.export_comment)} ;")
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
    csvBuffer.append(context.getString(R.string.export_sug))
    csvBuffer.append('\n')

    csvBuffer.append("${context.getString(R.string.export_dt)} ;")
    csvBuffer.append("${context.getString(R.string.export_val)} ;")
    csvBuffer.append("${context.getString(R.string.export_comment)} ;")
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
    csvBuffer.append(context.getString(R.string.export_pa))
    csvBuffer.append('\n')

    csvBuffer.append("${context.getString(R.string.export_dt)} ;")
    csvBuffer.append("${context.getString(R.string.export_dur)} ;")
    csvBuffer.append("${context.getString(R.string.export_comment)} ;")
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
        context.startActivity(Intent.createChooser(share, context.getString(R.string.export_sfile)))
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