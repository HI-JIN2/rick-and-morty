package com.yujin.presentation.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.yujin.designsystem.Dimens
import com.yujin.presentation.R

@Composable
fun ErrorItem(
    error: Throwable,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    spacing: Dp = Dimens.SpacingSmall
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${error.message}")
        Spacer(modifier = Modifier.height(spacing))
        Button(onClick = onRetry) {
            Text(
                text = stringResource(R.string.retry),
            )
        }
    }
}
