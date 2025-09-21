package evg.echo.healthlog.ui.components.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import evg.echo.healthlog.R
import evg.echo.healthlog.ui.components.widgets.AppBar

@Composable
fun AboutScreen(navController: NavHostController) {
    Scaffold(
        topBar = { AppBar(navController, text = stringResource(R.string.app_about)) },
        /*
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(2.dp),
                onClick = {

                },
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.on_shayr),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(200.dp, 100.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
        */
    ) { paddingValues ->

        Box(
            modifier = Modifier.padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    stringResource(R.string.app_about_part1),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    stringResource(R.string.app_about_part2),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    stringResource(R.string.app_about_part3),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(Modifier.height(20.dp))

                /*
                Text(
                    "Так же на этой странице вы можете угостить автора шаурмой ))",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(Modifier.height(50.dp))
                */
            }
        }
    }
}
