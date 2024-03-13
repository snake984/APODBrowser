package com.pandora.apodbrowser.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pandora.apodbrowser.R
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem

@Composable
fun SearchResultsView(
    modifier: Modifier,
    searchResults: List<PicOfTheDayItem>,
    onItemClick: (PicOfTheDayItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 8.dp),

        ) {
        items(searchResults) {
            FullWidthPictureItem(modifier, it, onItemClick)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    val text = rememberSaveable {
        mutableStateOf("")
    }

    TextField(
        value = text.value,
        singleLine = true,
        onValueChange = {
            text.value = it
            onValueChange(it)
        },
        trailingIcon = {
            if (text.value.isNotEmpty()) {
                Icon(
                    modifier = Modifier.clickable(enabled = true) {
                        text.value = ""
                        onValueChange(text.value)
                    },
                    imageVector = Icons.Default.Clear,
                    contentDescription = null
                )
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = null
            )
        }, colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface
        ), placeholder = {
            Text(stringResource(R.string.placeholder_search))
        }, modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}