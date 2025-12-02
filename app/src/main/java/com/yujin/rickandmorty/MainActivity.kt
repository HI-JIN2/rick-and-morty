package com.yujin.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.yujin.presentation.characterdetail.CharacterDetailRoute
import com.yujin.presentation.characterlist.CharacterListRoute
import com.yujin.presentation.search.SearchRoute
import com.yujin.rickandmorty.ui.theme.RickAndMortyTheme
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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                val bottomNavItems = listOf(CharacterList, Search)
                val topLevelBackStack = remember { TopLevelBackStack<NavKey>(CharacterList) }
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBar {
                                bottomNavItems.forEach { item ->
                                    val selected = topLevelBackStack.topLevelKey == item
                                    NavigationBarItem(
                                        selected = selected,
                                        onClick = {
                                            topLevelBackStack.switchTopLevel(item)
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = item.icon,
                                                contentDescription = item.title
                                            )
                                        },
                                        label = {
                                            Text(item.title)
                                        },
                                    )
                                }
                            }

                        }
                    },
                    modifier = Modifier.fillMaxSize(),

                    )
                { innerPadding ->
                    val screenModifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                    NavDisplay(
                        backStack = topLevelBackStack.backStack,
                        onBack = { topLevelBackStack.removeLast() },
                        entryProvider = entryProvider {
                            entry<CharacterList> {
                                CharacterListRoute(
//                                        onDetailClick = { topLevelBackStack.add(HomeDetail) },
//                                        modifier = screenModifier
                                )
                            }
                            entry<CharacterDetail> {
                                CharacterDetailRoute(
//                                        onBackClick = { topLevelBackStack.removeLast() },
//                                        modifier = screenModifier
                                )
                            }
                            entry<Search> {
                                SearchRoute(
//                                        onNoteClick = { id -> topLevelBackStack.add(NoteDetail(id)) },
//                                        onCreateClick = { topLevelBackStack.add(NoteCreate) },
//                                        modifier = screenModifier
                                )
                            }
                        }
                    )
//                    }
                }

            }
        }
    }
}


class TopLevelBackStack<T : NavKey>(private val startKey: T) {

    private var topLevelBackStacks: HashMap<T, SnapshotStateList<T>> = hashMapOf(
        startKey to mutableStateListOf(startKey)
    )

    var topLevelKey by mutableStateOf(startKey)
        private set

    val backStack = mutableStateListOf<T>(startKey)

    private fun updateBackStack() {
        backStack.clear()
        val currentStack = topLevelBackStacks[topLevelKey] ?: emptyList()

        if (topLevelKey == startKey) {
            backStack.addAll(currentStack)
        } else {
            val startStack = topLevelBackStacks[startKey] ?: emptyList()
            backStack.addAll(startStack + currentStack)
        }
    }

    fun switchTopLevel(key: T) {
        if (topLevelBackStacks[key] == null) {
            topLevelBackStacks[key] = mutableStateListOf(key)
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T) {
        topLevelBackStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast() {
        val currentStack = topLevelBackStacks[topLevelKey] ?: return

        if (currentStack.size > 1) {
            currentStack.removeLastOrNull()
        } else if (topLevelKey != startKey) {
            topLevelKey = startKey
        }
        updateBackStack()
    }

    fun replaceStack(vararg keys: T) {
        topLevelBackStacks[topLevelKey] = mutableStateListOf(*keys)
        updateBackStack()
    }

}