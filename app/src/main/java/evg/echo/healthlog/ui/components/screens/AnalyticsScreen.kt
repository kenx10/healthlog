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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.cartesianLayerPadding
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.stacked
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.component.shapeComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.insets
import com.patrykandpatrick.vico.compose.common.rememberHorizontalLegend
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.LegendItem
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import evg.echo.healthlog.ui.components.widgets.AppBar
import evg.echo.healthlog.ui.components.widgets.analytics.OneValColumnChart
import evg.echo.healthlog.ui.components.widgets.analytics.TwoValColumnChart
import evg.echo.healthlog.util.toDateFormat
import evg.echo.healthlog.util.toDateTimeFormat
import evg.echo.healthlog.vm.AnalyticsViewModel
import org.koin.compose.koinInject
import java.text.DecimalFormat

@Composable
fun AnalyticsScreen(
    navController: NavHostController,
    analyticsViewModel: AnalyticsViewModel = koinInject<AnalyticsViewModel>()
) {
    Scaffold(
        topBar = { AppBar(navController, text = "Аналитика") }
    ) { paddingValues ->

        val dates = mutableListOf<Long>()
        val mig = mutableListOf<Int>()
        val pan = mutableListOf<Int>()

        analyticsViewModel.analytics.forEach {
            dates.add(it.dateEpocMs)
            mig.add(it.migraines)
            pan.add(it.panics)
        }


        Box(
            modifier = Modifier.padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Мигрени (сила * время)",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                if (analyticsViewModel.analytics.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp), // немного отступов от краев
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Данных недостаточно",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                } else {
                    OneValColumnChart(
                        color = Color(0xffffc002),
                        timeMills = dates,
                        timeMillsFormatter = {
                            return@OneValColumnChart toDateFormat(it)
                        },
                        y = mig
                    )
                }
                Spacer(Modifier.height(16.dp))

                Text(
                    text = "ПА (суммарно)",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                if (analyticsViewModel.analytics.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp), // немного отступов от краев
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Данных недостаточно",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                } else {
                    OneValColumnChart(
                        color = Color(0xFF3286FD),
                        timeMills = dates,
                        timeMillsFormatter = {
                            return@OneValColumnChart toDateFormat(it)
                        },
                        y = pan,
                        ySuff = "мин."
                    )
                }
                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Сахар",
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
                            text = "Данных недостаточно",
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
                        ySuff = "ммоль/л"
                    )
                }
                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Давление",
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
                            text = "Данных недостаточно",
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
                        legendNames = linkedSetOf("Диастолическое", "Систолическое"),
                        timeMills = analyticsViewModel.presDT,
                        y1 = analyticsViewModel.presL,
                        y2 = analyticsViewModel.presDif,
                        timeMillsFormatter = {
                            return@TwoValColumnChart toDateTimeFormat(it)
                        },
                        ySuff = "мм рт. ст."
                    )
                }
            }
        }


    }
}


private val LegendLabelKey = ExtraStore.Key<Set<String>>()
private val YDecimalFormat = DecimalFormat("#.## h")
private val StartAxisValueFormatter = CartesianValueFormatter.decimal(YDecimalFormat)
private val StartAxisItemPlacer = VerticalAxis.ItemPlacer.step({ 0.5 })

@Composable
private fun JetpackComposeDailyDigitalMediaUse(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier,
) {
    val columnColors = listOf(Color(0xff6438a7), Color(0xff3490de), Color(0xff73e8dc))
    val legendItemLabelComponent = rememberTextComponent(vicoTheme.textColor)
    CartesianChartHost(
        chart =
            rememberCartesianChart(
                rememberColumnCartesianLayer(
                    columnProvider =
                        ColumnCartesianLayer.ColumnProvider.series(
                            columnColors.map { color ->
                                rememberLineComponent(fill = fill(color), thickness = 16.dp)
                            }
                        ),
                    columnCollectionSpacing = 32.dp,
                    mergeMode = { ColumnCartesianLayer.MergeMode.stacked() },
                ),
                startAxis =
                    VerticalAxis.rememberStart(
                        valueFormatter = StartAxisValueFormatter,
                        itemPlacer = StartAxisItemPlacer,
                    ),
                bottomAxis =
                    HorizontalAxis.rememberBottom(
                        itemPlacer = remember { HorizontalAxis.ItemPlacer.segmented() }
                    ),
                layerPadding = {
                    cartesianLayerPadding(
                        scalableStart = 16.dp,
                        scalableEnd = 16.dp
                    )
                },
                legend =
                    rememberHorizontalLegend(
                        items = { extraStore ->
                            extraStore[LegendLabelKey].forEachIndexed { index, label ->
                                add(
                                    LegendItem(
                                        shapeComponent(
                                            fill(columnColors[index]),
                                            CorneredShape.Pill
                                        ),
                                        legendItemLabelComponent,
                                        label,
                                    )
                                )
                            }
                        },
                        padding = insets(top = 16.dp),
                    ),
            ),
        modelProducer = modelProducer,
        modifier = modifier.height(252.dp),
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}

private val x = (2008..2018).toList()

private val y =
    mapOf(
        "Laptop/desktop" to listOf<Number>(2.2, 2.3, 2.4, 2.6, 2.5, 2.3, 2.2, 2.2, 2.2, 2.1, 2),
        "Mobile" to listOf(0.3, 0.3, 0.4, 0.8, 1.6, 2.3, 2.6, 2.8, 3.1, 3.3, 3.6),
        //"Other" to listOf(0.2, 0.3, 0.4, 0.3, 0.3, 0.3, 0.3, 0.4, 0.4, 0.6, 0.7),
    )

@Composable
fun JetpackComposeDailyDigitalMediaUse(modifier: Modifier = Modifier) {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            // Learn more: https://patrykandpatrick.com/eji9zq.
            columnSeries { y.values.forEach { series(x, it) } }
            extras { it[LegendLabelKey] = y.keys }
        }
    }
    JetpackComposeDailyDigitalMediaUse(modelProducer, modifier)
}

