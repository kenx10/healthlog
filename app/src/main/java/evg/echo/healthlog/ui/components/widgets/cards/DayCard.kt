package evg.echo.healthlog.ui.components.widgets.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import evg.echo.healthlog.model.ent.Migraine
import evg.echo.healthlog.model.ent.Panic
import evg.echo.healthlog.model.ent.Pressure
import evg.echo.healthlog.model.ent.Sugar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DayCard(
    date: String,
    migraines: MutableList<Migraine>,
    pressures: MutableList<Pressure>,
    sugars: MutableList<Sugar>,
    panics: MutableList<Panic>,
    onDelete: (Any) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var deleteTarget by remember { mutableStateOf<Any?>(null) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Удалить") },
            text = { Text("Удалить запись?") },
            confirmButton = {
                Button(onClick = {
                    when (deleteTarget) {
                        is Migraine -> {
                            migraines.remove(deleteTarget)
                            onDelete(deleteTarget!!)
                        }

                        is Panic -> {
                            panics.remove(deleteTarget)
                            onDelete(deleteTarget!!)
                        }

                        is Sugar -> {
                            sugars.remove(deleteTarget)
                            onDelete(deleteTarget!!)
                        }

                        is Pressure -> {
                            pressures.remove(deleteTarget)
                            onDelete(deleteTarget!!)
                        }

                        else -> {}
                    }
                    showDialog = false

                }) {
                    Text("Да")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Нет")
                }
            }
        )
    }

    if (!migraines.isEmpty()
        || !pressures.isEmpty()
        || !sugars.isEmpty()
        || !panics.isEmpty()
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ), colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(
                text = date,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .offset(x = (-16).dp)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 4.dp)
            )


            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                migraines.forEachIndexed { i, m ->
                    MigraineCard(
                        migraine = m,
                        onLongClick = {
                            deleteTarget = m
                            showDialog = true
                        }
                    )
                }

                pressures.forEachIndexed { i, p ->
                    PressureCard(
                        pressure = p,
                        onLongClick = {
                            deleteTarget = p
                            showDialog = true
                        }
                    )
                }

                sugars.forEachIndexed { i, s ->
                    SugarCard(
                        sugar = s,
                        onLongClick = {
                            deleteTarget = s
                            showDialog = true
                        }
                    )
                }

                panics.forEachIndexed { i, p ->
                    PanicCard(
                        panic = p,
                        onLongClick = {
                            deleteTarget = p
                            showDialog = true
                        }
                    )
                }
            }
        }

    }
}