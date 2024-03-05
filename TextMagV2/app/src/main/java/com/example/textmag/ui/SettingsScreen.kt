package com.example.textmag.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.textmag.ui.theme.TextMagTheme

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
    modifier: Modifier = Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        item {
            ListItem(
                headlineContent = { Text(text = "Font Settings") }
            )
        }

        item {
            ListItem(
                headlineContent = { Text(text = "Font") },
                trailingContent = {
                    SettingsDropdown(
                        options = fontOptions,
                        selection = curFont,
                        updateSelection = onFontDropdownSelection,
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.4f)
                            .fillMaxHeight(fraction = 0.5f)
                    )
                }
            )
        }

        item {
            ListItem(
                headlineContent = { Text(text = "Font Size") },
                trailingContent = {
                    SettingsDropdown(
                        options = fontSizeOptions,
                        selection = curFontSize,
                        updateSelection = onFontSizeDropdownSelection,
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.3f)
                            .fillMaxHeight(fraction = 0.5f)
                    )
                }
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Divider()
        }

        item {
            ListItem(
                headlineContent = { Text(text = "Display Settings") }
            )
        }

        item {
            ListItem(
                headlineContent = { Text(text = "Theme") },
                trailingContent = {
                    SettingsDropdown(
                        options = themeOptions,
                        selection = curTheme,
                        updateSelection = onThemeDropdownSelection,
                        enabled = false,
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.4f)
                            .fillMaxHeight(fraction = 0.5f)
                    )
                }
            )
        }

        item {
            ListItem(
                headlineContent = { Text(text = "Display Overlays") },
                trailingContent = {
                    Switch(
                        checked = arEnabled,
                        onCheckedChange = onArToggle,
                        enabled = false
                    )
                }
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Divider()
        }

        item {
            Spacer(modifier = Modifier.padding(16.dp))
            NavigationDrawerItem(
                label = { Text(text = "Show Tutorial") },
                selected = true,
                onClick = { /*TODO*/ },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color(243, 219, 243)
                )
            )
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
                tint = Color.White
            )
        }},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(121, 79, 130),
            titleContentColor = Color.White
        )
    )
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
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selection,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            enabled = enabled
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
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
