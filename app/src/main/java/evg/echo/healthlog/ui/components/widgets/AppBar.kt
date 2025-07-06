package evg.echo.healthlog.ui.components.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import evg.echo.healthlog.R
import evg.echo.healthlog.ui.components.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavHostController,
    text: String = stringResource(R.string.app_name)
) {

    return TopAppBar(
        title = { Text(text) },
        actions = {
            IconButton(onClick = { navController.navigate(Routes.ABOUT) }) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = stringResource(R.string.app_about)
                )
            }
        }
    )

}