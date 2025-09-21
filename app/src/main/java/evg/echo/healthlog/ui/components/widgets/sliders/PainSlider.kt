package evg.echo.healthlog.ui.components.widgets.sliders


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import evg.echo.healthlog.R

@Composable
fun PainSlider(
    onSelected: (Int) -> Unit
) {

    var selectedTabResId by remember { mutableIntStateOf(R.mipmap.mig_low) }

    val imageIdxs = listOf<Int>(
        R.mipmap.mig_low,
        R.mipmap.mig_middle,
        R.mipmap.mig_high
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        when (selectedTabResId) {
            R.mipmap.mig_low -> Text(
                stringResource(R.string.mig_weak_desc),
                style = MaterialTheme.typography.labelLarge
            )

            R.mipmap.mig_middle -> Text(
                stringResource(R.string.mig_millde_desc),
                style = MaterialTheme.typography.labelLarge
            )

            R.mipmap.mig_high -> Text(
                stringResource(R.string.mig_strong_desc),
                style = MaterialTheme.typography.labelLarge
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            imageIdxs.forEach { resId ->
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(
                            width = if (resId == selectedTabResId) 4.dp else 2.dp,
                            color = if (resId == selectedTabResId) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                            shape = CircleShape
                        )
                        .clickable {
                            selectedTabResId = resId;
                            onSelected(resId)
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }


}