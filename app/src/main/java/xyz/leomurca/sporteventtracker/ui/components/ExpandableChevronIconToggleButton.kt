package xyz.leomurca.sporteventtracker.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableChevronIconToggleButton(isExpanded: Boolean, onClick: () -> Unit) {
    val rotation by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "")

    IconToggleButton(
        checked = isExpanded,
        onCheckedChange = { onClick() },
        content = {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 6.dp)
                    .rotate(rotation)
            )
        }
    )
}
