package evg.echo.healthlog.ui.components.widgets.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.cartesianLayerPadding
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.stacked
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
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
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.LegendItem
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import evg.echo.healthlog.R
import evg.echo.healthlog.util.DecimalExtCartesianValueFormatter

private val LegendLabelKey = ExtraStore.Key<Set<String>>()

@Composable
fun TwoValColumnChart(
    modifier: Modifier = Modifier,
    timeMills: List<Long>,
    timeMillsFormatter: (Long) -> String,
    y1: List<Number>,
    y2: List<Number>,
    columnColors: List<Color>,
    legendNames: Set<String>,
    ySuff: String = stringResource(R.string.analytics_unit)
) {
    val legendItemLabelComponent = rememberTextComponent(vicoTheme.textColor)
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            columnSeries {
                series(y1)
                series(y2)
            }
            extras { it[LegendLabelKey] = legendNames }
        }
    }

    CartesianChartHost(
        chart =
            rememberCartesianChart(
                rememberColumnCartesianLayer(
                    columnProvider =
                        ColumnCartesianLayer.ColumnProvider.series(
                            columnColors.map {
                                rememberLineComponent(fill = fill(it), thickness = 16.dp)
                            }
                        ),
                    columnCollectionSpacing = 32.dp,
                    mergeMode = { ColumnCartesianLayer.MergeMode.stacked() }
                ),
                startAxis =
                    VerticalAxis.rememberStart(
                        valueFormatter = DecimalExtCartesianValueFormatter(ySuff),
                        itemPlacer = VerticalAxis.ItemPlacer.step({ 0.5 }),
                    ),
                bottomAxis =
                    HorizontalAxis.rememberBottom(
                        valueFormatter = CommonCartesianValueFormatter(
                            timeMills,
                            timeMillsFormatter
                        ),
                        itemPlacer = remember { HorizontalAxis.ItemPlacer.aligned() }
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
        modifier = modifier,
    )
}
