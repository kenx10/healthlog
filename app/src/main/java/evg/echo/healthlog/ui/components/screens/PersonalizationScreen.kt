package evg.echo.healthlog.ui.components.screens


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import evg.echo.healthlog.ui.components.Routes
import evg.echo.healthlog.ui.components.widgets.GenderSelectorWithIcons
import evg.echo.healthlog.ui.components.widgets.GradientDatePicker
import evg.echo.healthlog.ui.components.widgets.LocationPermissionSwitch
import evg.echo.healthlog.vm.PersonalizationViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PersonalizationScreen(
    navController: NavHostController,
    personalizationViewModel: PersonalizationViewModel = koinInject<PersonalizationViewModel>()
) {
    val context = LocalContext.current

    Scaffold(
        topBar = { /*AppBar(navController, text = "Персонализация")*/ }
    ) { paddingValues ->

        Box(
            modifier = Modifier.padding(paddingValues)
        ) {

            var name by personalizationViewModel.name
            var email by personalizationViewModel.email
            var gender by personalizationViewModel.gender
            var dateInMillis by personalizationViewModel.dateInMillis

            var emailError by remember { mutableStateOf(false) }
            val emailRegex = remember {
                Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}\$")
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Настройки",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                LocationPermissionSwitch(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

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
                        text = "Персональные данные",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(alignment = Alignment.End)
                            .offset(x = (-16).dp)
                            .background(MaterialTheme.colorScheme.background)
                            .padding(horizontal = 4.dp)
                    )

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Ваше имя") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Имя"
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(12.dp)
                        )


                        OutlinedTextField(
                            value = email ?: "",
                            onValueChange = {
                                emailError = it.isNotBlank() && !emailRegex.matches(it)
                                email = it
                            },
                            isError = emailError,
                            label = { Text("Email") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = "Email"
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(12.dp)
                        )

                        GenderSelectorWithIcons(
                            gender = gender,
                            onSelect = { gender = it }
                        )

                        GradientDatePicker(
                            dateInMillis = dateInMillis,
                            onSelect = { dateInMillis = it }
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Кнопка отправки
                Button(
                    onClick = {
                        if (emailError)
                            email = null

                        personalizationViewModel.save(name, email, gender, dateInMillis)

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
                    Text("Сохранить", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}
