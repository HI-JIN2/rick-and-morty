package com.yujin.rickandmorty.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Screen : NavKey {
    val route: String
    val icon: ImageVector?
    val label: String
}

@Serializable
data object CharacterList : Screen {
    override val route: String = "character_list"
    override val icon: ImageVector = Icons.Filled.List
    override val label: String = "List"
}

@Serializable
data class CharacterDetail(val characterId: Int) : Screen {
    override val route: String = "character_detail"
    override val icon = null
    override val label: String = "Character Detail"
}

@Serializable
data object Search : Screen {
    override val route: String = "search"
    override val icon: ImageVector = Icons.Filled.Search
    override val label: String = "Search"
}

object Screens {
    val bottomNavItems = listOf<Screen>(CharacterList, Search)
}
