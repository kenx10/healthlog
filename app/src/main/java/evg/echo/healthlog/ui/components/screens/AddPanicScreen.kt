package evg.echo.healthlog.ui.components.screens

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import evg.echo.healthlog.R
import evg.echo.healthlog.services.MeasureService
import evg.echo.healthlog.ui.components.Routes
import evg.echo.healthlog.ui.components.widgets.sliders.DurationSlider
import org.koin.compose.koinInject


@Composable
fun AddPanicScreen(
    navController: NavHostController,
    measureService: MeasureService = koinInject<MeasureService>()
) {
    var durationValue by remember { mutableFloatStateOf(20f) }
    var panicValue by remember { mutableIntStateOf(R.mipmap.mig_low) }
    var comment by remember { mutableStateOf("") }

    val commentMaxChars = 256
    val context = LocalContext.current

    Scaffold(
        /* topBar = { AppBar(navController) } */
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Регистрация панической атаки",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 32.dp)
                )


                Spacer(Modifier.height(8.dp))
                DurationSlider(
                    value = durationValue,
                    onValueChange = { it -> durationValue = it }
                )

                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp)
                ) {
                    BasicTextField(
                        value = comment,
                        onValueChange = { if (it.length <= commentMaxChars) comment = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.outline),
                        decorationBox = { innerTextField ->
                            Column {
                                if (comment.isEmpty()) {
                                    Text(
                                        "Опишите внешний триггер...",
                                        color = Color.Gray,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }

                Spacer(Modifier.height(8.dp))
                // Кнопка отправки
                Button(
                    onClick = {
                        measureService.savePan(durationValue.toInt(), comment)

                        Toast.makeText(context, "Сохранено", Toast.LENGTH_SHORT).show()
                        navController.navigate(Routes.MAIN)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 4.dp
                    )
                ) {
                    Text("Записать", style = MaterialTheme.typography.labelLarge)
                }
            }

        }
    }

}

