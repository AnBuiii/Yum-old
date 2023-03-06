package com.example.yum.common.component

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.ConfigurationCompat
import com.example.yum.HomeScreenSection
import java.util.*

@Composable
fun RowScope.YumNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = YumNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = YumNavigationDefaults.navigationContentColor(),
            selectedTextColor = YumNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = YumNavigationDefaults.navigationContentColor(),
            indicatorColor = YumNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

@Composable
fun YumBottomBar(
    tabs: Array<HomeScreenSection>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        contentColor = YumNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
    ) {
        val currentSection = tabs.first { it.route == currentRoute }
        val configuration = LocalConfiguration.current
        val currentLocale: Locale =
            ConfigurationCompat.getLocales(configuration).get(0) ?: Locale.getDefault()

        tabs.forEach { section ->
            YumNavigationBarItem(
                selected = section == currentSection,
                onClick = { navigateToRoute(section.route) },
                icon = {
                    Icon(
                        imageVector = section.icon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(section.title).uppercase(currentLocale)) },
            )

        }
        Log.d("TAB LOG", currentRoute)
    }
}

object YumNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}