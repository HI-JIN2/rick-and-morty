package com.yujin.rickandmorty.navigation.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import com.yujin.rickandmorty.navigation.Screen

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
                NavigationBarItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = item.label
                        )
                    },
                    label = {
                        Text(item.label)
                    },
                )
            }
        }
    }
}
