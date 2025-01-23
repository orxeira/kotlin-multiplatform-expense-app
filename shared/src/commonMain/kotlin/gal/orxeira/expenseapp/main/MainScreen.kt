package gal.orxeira.expenseapp.main

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import expenseapp.shared.generated.resources.Res
import expenseapp.shared.generated.resources.add_expense
import gal.orxeira.expenseapp.core.ui.navigation.AppNavHost
import gal.orxeira.expenseapp.core.ui.navigation.BottomNav
import gal.orxeira.expenseapp.core.ui.navigation.Screens
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainScreen(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("?")
        ?: Screens.Home::class.qualifiedName.orEmpty()
    val showBottomNavigation =
        currentRoute in BottomNav.entries.map { it.route::class.qualifiedName }

    Scaffold(
        content = { innerPadding ->
            AppNavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            if (showBottomNavigation) {
                FloatingActionButton(
                    modifier = Modifier
                        .offset(y = 65.dp)
                        .size(60.dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        navController.navigate(Screens.AddExpense())
                    },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 2.dp,
                    ),
                    shape = CircleShape,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(Res.string.add_expense),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(40.dp),
                    )
                }
            }
        },
        bottomBar = {
            if (showBottomNavigation) {
                BottomNavigation(
                    backgroundColor = MaterialTheme.colorScheme.background
                ) {
                    BottomNav.entries
                        .forEach { navigationItem ->
                            val isSelected by remember(currentRoute) {
                                derivedStateOf { currentRoute == navigationItem.route::class.qualifiedName }
                            }
                            BottomNavigationItem(
                                modifier = Modifier
                                    .testTag(navigationItem.name)
                                    .offset(
                                        x = when (navigationItem.index) {
                                            0 -> (-24).dp
                                            1 -> 24.dp
                                            else -> 0.dp
                                        },
                                    ),
                                selected = isSelected,
                                label = {
                                    Text(
                                        text = stringResource(navigationItem.labelRes),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(if (isSelected) navigationItem.selectedIcon else navigationItem.unselectedIcon),
                                        contentDescription = stringResource(navigationItem.labelRes),
                                        tint = if (isSelected) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.onBackground
                                        },
                                    )
                                },
                                onClick = {
                                    navController.navigate(navigationItem.route)
                                },
                            )
                        }
                }
            }
        },
    )
}
