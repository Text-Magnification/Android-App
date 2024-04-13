package com.example.textmag.ui

import android.os.Build
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    onBackButtonClick: () -> Unit,
    curFont: String,
    fontOptions: List<String>,
    onFontDropdownSelection: (String) -> Unit,
    curFontSize: String,
    fontSizeOptions: List<String>,
    onFontSizeDropdownSelection: (String) -> Unit,
    curTheme: String,
    themeOptions: List<String>,
    onThemeDropdownSelection: (String) -> Unit,
    arEnabled: Boolean,
    onArToggle: (Boolean) -> Unit,
    dynamicThemeEnabled: Boolean,
    onDynamicThemeToggle: (Boolean) -> Unit,
    onAboutUsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { SettingsScreenAppBar(onClick = onBackButtonClick) },
        modifier = modifier
    ) {innerPadding ->
        SettingsScreenBody(
            curFont,
            fontOptions,
            onFontDropdownSelection,
            curFontSize,
            fontSizeOptions,
            onFontSizeDropdownSelection,
            curTheme,
            themeOptions,
            onThemeDropdownSelection,
            arEnabled,
            onArToggle,
            dynamicThemeEnabled,
            onDynamicThemeToggle,
            onAboutUsClick,
            modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun SettingsScreenBody(
    curFont: String,
    fontOptions: List<String>,
    onFontDropdownSelection: (String) -> Unit,
    curFontSize: String,
    fontSizeOptions: List<String>,
    onFontSizeDropdownSelection: (String) -> Unit,
    curTheme: String,
    themeOptions: List<String>,
    onThemeDropdownSelection: (String) -> Unit,
    arEnabled: Boolean,
    onArToggle: (Boolean) -> Unit,
    dynamicThemeEnabled: Boolean,
    onDynamicThemeToggle: (Boolean) -> Unit,
    onAboutUsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val settingsItemPadding = 8.dp
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            item {
                ListItem(
                    headlineContent = { SettingsText(content = "Font Settings") }
                )
            }

            item {
                ListItem(
                    headlineContent = { SettingsText(content = "Font") },
                    trailingContent = {
                        SettingsDropdown(
                            options = fontOptions,
                            selection = curFont,
                            updateSelection = onFontDropdownSelection,
                            modifier = Modifier
                                .fillMaxWidth(fraction = 0.4f)
                                .fillMaxHeight(fraction = 0.5f)
                        )
                    },
                    modifier = Modifier.padding(start = settingsItemPadding)
                )
            }

            item {
                ListItem(
                    headlineContent = { SettingsText(content = "Font Size") },
                    trailingContent = {
                        SettingsDropdown(
                            options = fontSizeOptions,
                            selection = curFontSize,
                            updateSelection = onFontSizeDropdownSelection,
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                        )
                    },
                    modifier = Modifier.padding(start = settingsItemPadding)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Divider()
            }

            item {
                ListItem(
                    headlineContent = { SettingsText(content = "Display Settings") }
                )
            }

            item {
                ListItem(
                    headlineContent = { SettingsText(content = "Theme") },
                    supportingContent = { SettingsInfo(title = "About theme", content = "Allows you to switch between themes") },
                    trailingContent = {
                        SettingsDropdown(
                            options = themeOptions,
                            selection = curTheme,
                            updateSelection = onThemeDropdownSelection,
                            enabled = true,
                            modifier = Modifier
                                .fillMaxWidth(fraction = 0.4f)
                                .fillMaxHeight(fraction = 0.5f)
                        )
                    },
                    modifier = Modifier.padding(start = settingsItemPadding)
                )
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                item {
                    ListItem(
                        headlineContent = { SettingsText(content = "Dynamic Themes") },
                        supportingContent = { SettingsInfo(title = "About dynamic themes", content = "Changes the color scheme based on your system colors") },
                        trailingContent = {
                            Switch(
                                checked = dynamicThemeEnabled,
                                onCheckedChange = onDynamicThemeToggle,
                                enabled = true
                            )
                        },
                        modifier = Modifier.padding(start = settingsItemPadding)
                    )
                }
            }

            item {
                ListItem(
                    headlineContent = { SettingsText(content = "Display Overlays (Experimental)") },
                    supportingContent = { SettingsInfo(title = "About Overlays", content = "Displays rectangular overlays in the live camera feed around text passages (Note that this is an experimental feature)") },
                    trailingContent = {
                        Switch(
                            checked = arEnabled,
                            onCheckedChange = onArToggle,
                        )
                    },
                    modifier = Modifier.padding(start = settingsItemPadding)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Divider()
            }

            item {
                Spacer(modifier = Modifier.padding(16.dp))
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "About Us",
                        )
                    },
                    selected = true,
                    onClick = onAboutUsClick,
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                )
                Spacer(modifier = Modifier.padding(8.dp))
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Show Tutorial",
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                    selected = true,
                    onClick = { /*TODO*/ },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenAppBar(
    onClick: () -> Unit
) {
    // TODO: Remove hardcoding of colors to accommodate themes
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Settings")
        },
        navigationIcon = { IconButton(onClick = onClick) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun SettingsText(
    content: String
) {
    Text(
        text = content,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsInfo(
    title: String,
    content: String
) {
    val tooltipState = remember { RichTooltipState() }
    val scope = rememberCoroutineScope()
    RichTooltipBox(
        title = { Text(text = title) },
        text = { Text(text = content) },
        tooltipState = tooltipState
    ) {
        IconButton(
            onClick = { scope.launch { tooltipState.show() } },
            modifier = Modifier.tooltipAnchor()
        ) {
            Icon(Icons.Outlined.Info, contentDescription = "info")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDropdown(
    options: List<String>,
    selection: String,
    updateSelection: (String) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier
                .menuAnchor()
                .width(IntrinsicSize.Min),
            readOnly = true,
            value = selection,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            enabled = enabled,
            singleLine = true,
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        updateSelection(selectionOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
