package evg.echo.healthlog.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import evg.echo.healthlog.ui.components.Routes
import evg.echo.healthlog.ui.components.screens.AboutScreen
import evg.echo.healthlog.ui.components.screens.AddMigraineScreen
import evg.echo.healthlog.ui.components.screens.AddPanicScreen
import evg.echo.healthlog.ui.components.screens.AddPressureScreen
import evg.echo.healthlog.ui.components.screens.AddSugarScreen
import evg.echo.healthlog.ui.components.screens.AnalyticsScreen
import evg.echo.healthlog.ui.components.screens.HistoryScreen
import evg.echo.healthlog.ui.components.screens.MainScreen
import evg.echo.healthlog.ui.components.screens.PersonalizationScreen
import evg.echo.healthlog.ui.theme.HealthLogTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            HealthLogTheme {
                NavHost(
                    navController, startDestination = Routes.MAIN
                ) {
                    composable(Routes.MAIN) {
                        MainScreen(
                            navController = navController
                        )
                    }
                    composable(Routes.ADD_M) {
                        AddMigraineScreen(
                            navController = navController
                        )
                    }

                    composable(Routes.ADD_F) {
                        AddPanicScreen(
                            navController = navController
                        )
                    }
                    composable(Routes.ADD_P) {
                        AddPressureScreen(
                            navController = navController
                        )
                    }
                    composable(Routes.ADD_S) {
                        AddSugarScreen(
                            navController = navController
                        )
                    }
                    composable(Routes.HISTORY) {
                        HistoryScreen(navController = navController)
                    }
                    composable(Routes.ANALYTICS) {
                        AnalyticsScreen(navController = navController)
                    }
                    composable(Routes.PERSONALISATION) {
                        PersonalizationScreen(navController = navController)
                    }
                    composable(Routes.ABOUT) {
                        AboutScreen(navController = navController)
                    }
                }
            }
        }
    }
}




