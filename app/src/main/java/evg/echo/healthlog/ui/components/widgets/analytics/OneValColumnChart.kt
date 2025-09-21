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
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import evg.echo.healthlog.R
import evg.echo.healthlog.util.DecimalExtCartesianValueFormatter

@Composable
fun OneValColumnChart(
    modifier: Modifier = Modifier,
    timeMills: List<Long>,
    timeMillsFormatter: (Long) -> String,
    y: List<Number>,
    color: Color,
    ySuff: String = stringResource(R.string.analytics_unit)
) {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            columnSeries {
                series(y)
            }
        }
    }

    CartesianChartHost(
        chart =
            rememberCartesianChart(
                rememberColumnCartesianLayer(
                    ColumnCartesianLayer.ColumnProvider.series(
                        rememberLineComponent(fill = fill(color), thickness = 16.dp)
                    )
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
                }
            ),
        modelProducer = modelProducer,
        modifier = modifier,
    )
}


class CommonCartesianValueFormatter(
    val timeMills: List<Long>,
    val timeMillsFormatter: (Long) -> String,
) : CartesianValueFormatter {
    override fun format(
        context: CartesianMeasuringContext,
        value: Double,
        verticalAxisPosition: Axis.Position.Vertical?,
    ): CharSequence {
        val idx = value.toInt()
        return timeMillsFormatter(timeMills[idx])
    }
}