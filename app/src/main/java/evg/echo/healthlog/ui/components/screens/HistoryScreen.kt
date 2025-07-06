package evg.echo.healthlog.ui.components.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import evg.echo.healthlog.data.dateFormat
import evg.echo.healthlog.ui.components.widgets.AppBar
import evg.echo.healthlog.ui.components.widgets.cards.DayCard
import evg.echo.healthlog.vm.HistoryViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HistoryScreen(
    navController: NavHostController,
    historyViewModel: HistoryViewModel = koinInject<HistoryViewModel>()
) {
    Scaffold(
        topBar = { AppBar(navController, text = "История наблюдений") }
    ) { paddingValues ->

        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            val items = historyViewModel.dayMeasures
            val listState = rememberLazyListState()

            LaunchedEffect(listState) {
                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .collect { index ->
                        if (index == items.lastIndex && !historyViewModel.isLoading) {
                            historyViewModel.loadMore()
                        }
                    }
            }

            LazyColumn(state = listState) {
                items(items) {
                    DayCard(
                        dateFormat.format(it.date),
                        it.migraines,
                        it.pressures,
                        it.sugars,
                        it.panics,
                        onDelete = {
                            historyViewModel.delete(it)
                        }
                    )
                }

                if (historyViewModel.isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
