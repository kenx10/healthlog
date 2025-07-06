package evg.echo.healthlog.ui.components.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import evg.echo.healthlog.model.ent.Gender

@Composable
fun GenderSelectorWithIcons(
    gender: Gender,
    onSelect: (gender: Gender) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        GenderOption(
            isSelected = gender == Gender.MALE,
            onSelect = {
                onSelect(Gender.MALE)
            },
            icon = Icons.Default.Male,
            colors = listOf(Color(0xFF2196F3), Color(0xFF64B5F6))
        )

        GenderOption(
            isSelected = gender == Gender.FEMALE,
            onSelect = {
                onSelect(Gender.FEMALE)
            },
            icon = Icons.Default.Female,
            colors = listOf(Color(0xFFE91E63), Color(0xFFF06292))
        )
    }
}

@Composable
fun GenderOption(
    isSelected: Boolean,
    onSelect: () -> Unit,
    icon: ImageVector,
    colors: List<Color>
) {
    Icon(
        imageVector = icon,
        contentDescription = "",
        tint = colors[0],
        modifier = Modifier
            .size(80.dp)
            .padding(8.dp)
            .clip(CircleShape)
            .border(
                width = if (isSelected) 4.dp else 1.dp,
                color = if (isSelected) colors[0] else colors[1],
                shape = CircleShape
            )
            .clickable {
                onSelect()
            },
    )

    /*
    Image(
        painter = rememberVectorPainter(icon),
        contentDescription = "",
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp)
            .clip(CircleShape)
            .border(
                width = if (isSelected) 4.dp else 2.dp,
                color = if (isSelected) colors[0] else colors[1],
                shape = CircleShape
            ).clickable {
                onSelect()
            },
        contentScale = ContentScale.Crop
    )
    */

}