package xyz.leomurca.sporteventtracker.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import xyz.leomurca.sporteventtracker.R

@Composable
fun FilledCircleIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_circle_filled),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.size(25.dp)
    )
}

