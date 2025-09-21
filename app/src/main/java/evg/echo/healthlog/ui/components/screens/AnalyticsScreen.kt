package evg.echo.healthlog.ui.components.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import evg.echo.healthlog.R
import evg.echo.healthlog.ui.components.widgets.AppBar
import evg.echo.healthlog.ui.components.widgets.analytics.OneValColumnChart
import evg.echo.healthlog.ui.components.widgets.analytics.TwoValColumnChart
import evg.echo.healthlog.util.toDateFormat
import evg.echo.healthlog.util.toDateTimeFormat
import evg.echo.healthlog.vm.AnalyticsViewModel
import org.koin.compose.koinInject

@Composable
fun AnalyticsScreen(
    navController: NavHostController,
    analyticsViewModel: AnalyticsViewModel = koinInject<AnalyticsViewModel>()
) {
    Scaffold(
        topBar = { AppBar(navController, text = stringResource(R.string.app_analytics)) }
    ) { paddingValues ->

        Box(
            modifier = Modifier.padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.analytics_mig),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                if (analyticsViewModel.migDT.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp), // немного отступов от краев
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.analytics_nodata),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                } else {
                    OneValColumnChart(
                        color = Color(0xffffc002),
                        timeMills = analyticsViewModel.migDT,
                        timeMillsFormatter = {
                            return@OneValColumnChart toDateFormat(it)
                        },
                        y = analyticsViewModel.mig
                    )
                }
                Spacer(Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.analytics_pan),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                if (analyticsViewModel.panDT.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp), // немного отступов от краев
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.analytics_nodata),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                } else {
                    OneValColumnChart(
                        color = Color(0xFF3286FD),
                        timeMills = analyticsViewModel.panDT,
                        timeMillsFormatter = {
                            return@OneValColumnChart toDateFormat(it)
                        },
                        y = analyticsViewModel.pan,
                        ySuff = stringResource(R.string.analytics_pan_unit)
                    )
                }
                Spacer(Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.analytics_sug),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                if (analyticsViewModel.sugDT.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp), // немного отступов от краев
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.analytics_nodata),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                } else {
                    OneValColumnChart(
                        color = Color(0xFFF44336),
                        timeMills = analyticsViewModel.sugDT,
                        y = analyticsViewModel.sug,
                        timeMillsFormatter = {
                            return@OneValColumnChart toDateTimeFormat(it)
                        },
                        ySuff = stringResource(R.string.analytics_sug_unit)
                    )
                }
                Spacer(Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.analytics_pres),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                if (analyticsViewModel.presDT.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp), // немного отступов от краев
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.analytics_nodata),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                } else {
                    TwoValColumnChart(
                        columnColors = listOf(
                            Color(0xFF673AB7),
                            Color(0xff3490de)
                        ),
                        legendNames = linkedSetOf(stringResource(R.string.pres_down), stringResource(R.string.pres_up)),
                        timeMills = analyticsViewModel.presDT,
                        y1 = analyticsViewModel.presL,
                        y2 = analyticsViewModel.presDif,
                        timeMillsFormatter = {
                            return@TwoValColumnChart toDateTimeFormat(it)
                        },
                        ySuff = stringResource(R.string.analytics_pres_unit)
                    )
                }
            }
        }


    }
}
