package com.yujin.rickandmorty.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

interface BottomNavItem {
    val icon: ImageVector
    val title: String
}

@Serializable
data object CharacterList : NavKey, BottomNavItem {
    override val icon: ImageVector = Icons.Filled.List
    override val title: String = "List"
}

@Serializable
data object CharacterDetail : NavKey

@Serializable
data object Search : NavKey, BottomNavItem {
    override val icon: ImageVector = Icons.Filled.Search
    override val title: String = "Notes"
}