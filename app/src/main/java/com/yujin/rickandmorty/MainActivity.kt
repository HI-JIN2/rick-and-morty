package com.yujin.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.yujin.presentation.characterdetail.CharacterDetailRoute
import com.yujin.presentation.characterlist.CharacterListRoute
import com.yujin.presentation.search.SearchRoute
import com.yujin.rickandmorty.navigation.BottomNavigationBar
import com.yujin.rickandmorty.navigation.CharacterDetail
import com.yujin.rickandmorty.navigation.CharacterList
import com.yujin.rickandmorty.navigation.Screens
import com.yujin.rickandmorty.navigation.Search
import com.yujin.rickandmorty.navigation.TopLevelBackStack
import com.yujin.rickandmorty.ui.theme.RickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                val topLevelBackStack = remember { TopLevelBackStack<NavKey>(CharacterList) }
                val currentScreen = remember {
                    derivedStateOf {
                        topLevelBackStack.backStack.lastOrNull()
                    }
                }
                val hideBottomBar = remember {
                    derivedStateOf {
                        currentScreen.value !in Screens.bottomNavItems
                    }
                }
                
                Scaffold(
                    bottomBar = {
                        if (!hideBottomBar.value) {
                            BottomNavigationBar(
                                bottomNavItems = Screens.bottomNavItems,
                                currentTopLevelKey = topLevelBackStack.topLevelKey,
                                onItemClick = { item ->
                                    topLevelBackStack.switchTopLevel(item)
                                }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    val screenModifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                    NavDisplay(
                        backStack = topLevelBackStack.backStack,
                        onBack = { topLevelBackStack.removeLast() },
                        entryProvider = entryProvider {
                            entry<CharacterList> {
                                CharacterListRoute(
                                    onDetailClick = { characterId ->
                                        topLevelBackStack.add(CharacterDetail(characterId))
                                    },
                                    modifier = screenModifier
                                )
                            }
                            entry<CharacterDetail> { key ->
                                CharacterDetailRoute(
                                    characterId = key.characterId,
                                    onBackClick = { topLevelBackStack.removeLast() },
                                    modifier = Modifier
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
                }
            }
        }
    }
}
