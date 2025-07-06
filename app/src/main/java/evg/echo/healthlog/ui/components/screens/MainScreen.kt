package evg.echo.healthlog.ui.components.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import evg.echo.healthlog.R
import evg.echo.healthlog.services.MeasureService
import evg.echo.healthlog.ui.components.Routes
import evg.echo.healthlog.ui.components.widgets.AppBar
import evg.echo.healthlog.ui.components.widgets.MenuButton
import org.koin.compose.koinInject


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    measureService: MeasureService = koinInject<MeasureService>()
) {
    Scaffold(
        topBar = { AppBar(navController) },
        floatingActionButton = {
            Row(
                modifier = Modifier.padding(3.dp)
            ) {
                FloatingActionButton(
                    modifier = Modifier.padding(2.dp),
                    onClick = { navController.navigate(Routes.ADD_M) },
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.ico_mig),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(5.dp)
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                FloatingActionButton(
                    modifier = Modifier.padding(2.dp),
                    onClick = { navController.navigate(Routes.ADD_F) },
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.ico_fob),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(5.dp)
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                FloatingActionButton(
                    modifier = Modifier.padding(2.dp),
                    onClick = { navController.navigate(Routes.ADD_P) },
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.ico_pres),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(5.dp)
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                FloatingActionButton(
                    modifier = Modifier.padding(2.dp),
                    onClick = { navController.navigate(Routes.ADD_S) },
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.ico_shug),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(5.dp)
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Первая строка кнопок
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        MenuButton(
                            title = "История",
                            icon = Icons.AutoMirrored.Outlined.List,
                            color = Color(0xFF4CAF50), // Зеленый
                            onClick = { navController.navigate(Routes.HISTORY) }
                        )

                        MenuButton(
                            title = "Аналитика",
                            icon = Icons.Default.Share,
                            color = Color(0xFF2196F3), // Синий
                            onClick = { navController.navigate(Routes.ANALYTICS) }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Вторая строка кнопок
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        MenuButton(
                            title = "Настройки",
                            icon = Icons.Default.Settings,
                            color = Color(0xFFFF9800), // Оранжевый
                            onClick = { navController.navigate(Routes.PERSONALISATION) }
                        )

                        MenuButton(
                            title = "О программе",
                            icon = Icons.Default.Info,
                            color = Color(0xFF9C27B0), // Фиолетовый
                            onClick = { navController.navigate(Routes.ABOUT) }
                        )
                    }
                }

                /*Button(
                    onClick = {
                        measureService.generate()
                    }
                ) {
                    Text("Generate")
                }*/
            }
        }
    }


}


