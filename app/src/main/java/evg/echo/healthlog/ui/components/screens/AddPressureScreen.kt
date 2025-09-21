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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import evg.echo.healthlog.R
import evg.echo.healthlog.services.MeasureService
import evg.echo.healthlog.ui.components.Routes
import evg.echo.healthlog.ui.components.widgets.sliders.PressureSlider
import org.koin.compose.koinInject


@Composable
fun AddPressureScreen(
    navController: NavHostController,
    measureService: MeasureService = koinInject<MeasureService>(),
) {
    var highValue by remember { mutableFloatStateOf(120f) }
    var lowValue by remember { mutableFloatStateOf(80f) }
    var comment by remember { mutableStateOf("") }

    val commentMaxChars = 256
    val context = LocalContext.current

    val savedStr = stringResource(R.string.saved)

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
                    text = stringResource(R.string.pres_reg),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Spacer(Modifier.height(8.dp))
                PressureSlider(
                    label = stringResource(R.string.pres_up),
                    value = highValue,
                    onValueChange = { it -> highValue = it }
                )

                Spacer(Modifier.height(8.dp))
                PressureSlider(
                    label = stringResource(R.string.pres_down),
                    value = lowValue,
                    onValueChange = { it -> lowValue = it }
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
                                        stringResource(R.string.additional_info),
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
                        // save
                        measureService.savePres(highValue.toInt(), lowValue.toInt(), comment)
                        Toast.makeText(context, savedStr, Toast.LENGTH_SHORT).show()
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
                    Text(stringResource(R.string.save), style = MaterialTheme.typography.labelLarge)
                }
            }

        }
    }

}

