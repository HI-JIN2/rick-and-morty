package com.yujin.presentation.navigation.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import com.yujin.presentation.navigation.Screen

@Composable
fun BottomNavigationBar(
    bottomNavItems: List<Screen>,
    currentTopLevelKey: NavKey,
    onItemClick: (NavKey) -> Unit
) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            val selected = currentTopLevelKey == item
            item.icon?.let { icon ->
                val label = stringResource(item.labelResId)
                NavigationBarItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = label
                        )
                    },
                    label = {
                        Text(label)
                    },
                )
            }
        }
    }
}

