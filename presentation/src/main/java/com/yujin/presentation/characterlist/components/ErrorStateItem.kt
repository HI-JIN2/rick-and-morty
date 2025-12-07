package com.yujin.presentation.characterlist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun ErrorStateItem(
    error: Throwable,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    spacing: androidx.compose.ui.unit.Dp = 8.dp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${error.message}")
        Spacer(modifier = Modifier.height(spacing))
        Button(onClick = onRetry) {
            Text(
                text = "Retry",
            )
        }
    }
}
