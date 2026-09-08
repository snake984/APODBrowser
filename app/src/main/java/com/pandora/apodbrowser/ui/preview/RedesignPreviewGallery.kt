package com.pandora.apodbrowser.ui.preview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pandora.apodbrowser.ui.theme.APODBrowserTheme
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.apodbrowser.ui.theme.icons.ic_arrow_back
import com.pandora.apodbrowser.ui.theme.icons.ic_favorite
import com.pandora.apodbrowser.ui.theme.icons.ic_home
import com.pandora.apodbrowser.ui.theme.icons.ic_info
import com.pandora.apodbrowser.ui.theme.icons.ic_search

private val PreviewItems = listOf(
    PicOfTheDayItem(
        title = "The Pillars of Creation",
        date = "2024-10-01",
        url = "",
        hdUrl = "",
        explanation = "A stellar nursery shaped by light, dust, and time.",
        copyright = "NASA / ESA"
    ),
    PicOfTheDayItem(
        title = "Moonlit Mountains",
        date = "2024-09-28",
        url = "",
        hdUrl = "",
        explanation = "A quiet landscape beneath a bright lunar sky.",
        copyright = "NASA"
    ),
    PicOfTheDayItem(
        title = "Aurora Over Earth",
        date = "2024-09-21",
        url = "",
        hdUrl = "",
        explanation = "Charged particles paint ribbons across the atmosphere.",
        copyright = "NASA"
    ),
    PicOfTheDayItem(
        title = "A Distant Spiral",
        date = "2024-09-14",
        url = "",
        hdUrl = "",
        explanation = "A galaxy turning slowly through the dark.",
        copyright = "NASA / ESA"
    )
)

@Preview(
    name = "Redesign gallery",
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFFF7F8FC
)
@Composable
fun RedesignPreviewGallery() {
    APODBrowserTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    PreviewHeading(
                        eyebrow = "APODBrowser redesign",
                        title = "A calmer way to explore the universe",
                        description = "Preview direction inspired by the official Now in Android sample."
                    )
                }
                item { PreviewLabel("Home") }
                item { PreviewHomeContent() }
                item { PreviewLabel("Favorites") }
                item { PreviewFavoritesContent() }
                item { PreviewLabel("Picture detail") }
                item { PreviewDetailContent(item = PreviewItems.first()) }
            }
        }
    }
}

@Preview(name = "Home", showBackground = true, showSystemUi = true)
@Composable
fun RedesignHomePreview() {
    APODBrowserTheme { PreviewHomeContent() }
}

@Preview(name = "Favorites", showBackground = true, showSystemUi = true)
@Composable
fun RedesignFavoritesPreview() {
    APODBrowserTheme { PreviewFavoritesContent() }
}

@Preview(name = "Picture detail", showBackground = true, showSystemUi = true)
@Composable
fun RedesignPictureDetailPreview(
    @PreviewParameter(PreviewItemProvider::class) item: PicOfTheDayItem
) {
    APODBrowserTheme { PreviewDetailContent(item) }
}

@Composable
private fun PreviewHomeContent() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { PreviewNavigation(selectedIndex = 0) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                PreviewTopBar(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "Good evening, stargazer",
                    subtitle = "Discover something beautiful"
                )
            }
            item {
                PreviewSearchBar(Modifier.padding(horizontal = 20.dp))
            }
            item {
                PreviewSectionHeading(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "Today in space",
                    action = "See all"
                )
            }
            item {
                PreviewHeroCard(
                    item = PreviewItems.first(),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
            item {
                PreviewSectionHeading(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "More to explore",
                    action = "View collection"
                )
            }
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PreviewItems.drop(1).take(2).forEach { item ->
                        PreviewMiniCard(item, Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun PreviewFavoritesContent() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { PreviewNavigation(selectedIndex = 1) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                PreviewTopBar(
                    title = "Your collection",
                    subtitle = "The skies you want to remember"
                )
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PreviewFilterChip("All", selected = true)
                    PreviewFilterChip("Nebulae")
                    PreviewFilterChip("Planets")
                }
            }
            items(PreviewItems) { item ->
                PreviewFavoriteRow(item)
            }
        }
    }
}

@Composable
private fun PreviewDetailContent(item: PicOfTheDayItem) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(ic_arrow_back, contentDescription = "Back")
                }
                Text(
                    text = "Picture detail",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                PreviewArtwork(
                    title = item.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.12f)
                        .padding(horizontal = 20.dp)
                )
            }
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "October 1, 2024  •  ${item.copyright}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = item.explanation.orEmpty(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(onClick = {}) {
                            Icon(ic_favorite, contentDescription = null)
                            Spacer(Modifier.size(8.dp))
                            Text("Save")
                        }
                        TextButton(onClick = {}) {
                            Icon(ic_info, contentDescription = null)
                            Spacer(Modifier.size(8.dp))
                            Text("About this image")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PreviewHeroCard(item: PicOfTheDayItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHighest)
    ) {
        Column {
            PreviewArtwork(item.title, Modifier.fillMaxWidth().aspectRatio(1.65f))
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("PICTURE OF THE DAY", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Text(item.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(item.explanation.orEmpty(), maxLines = 2, overflow = TextOverflow.Ellipsis, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun PreviewMiniCard(item: PicOfTheDayItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column {
            PreviewArtwork(item.title, Modifier.fillMaxWidth().aspectRatio(1.1f))
            Text(item.title, Modifier.padding(12.dp), maxLines = 2, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun PreviewFavoriteRow(item: PicOfTheDayItem) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PreviewArtwork(
                item.title,
                Modifier.size(86.dp).clip(RoundedCornerShape(16.dp))
            )
            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 14.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(item.title, fontWeight = FontWeight.SemiBold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(item.date, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = {}) {
                Icon(ic_favorite, contentDescription = "Remove favorite", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun PreviewArtwork(title: String, modifier: Modifier = Modifier) {
    val palette = when {
        title.contains("Aurora") -> listOf(Color(0xFF112B4B), Color(0xFF2A8C91), Color(0xFFC6E895))
        title.contains("Moon") -> listOf(Color(0xFF0D1A3B), Color(0xFF374B85), Color(0xFFE2C99F))
        title.contains("Spiral") -> listOf(Color(0xFF170D36), Color(0xFF5A2F75), Color(0xFFEEB2D2))
        else -> listOf(Color(0xFF111A3B), Color(0xFF45306B), Color(0xFFE5A68F))
    }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(palette))
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val center = Offset(size.width * .66f, size.height * .38f)
            drawCircle(Color.White.copy(alpha = .92f), size.minDimension * .08f, center)
            repeat(18) { index ->
                val x = (index * 83f) % size.width
                val y = (index * 47f) % size.height
                drawCircle(Color.White.copy(alpha = .55f), 1.5f + (index % 3), Offset(x, y))
            }
            val path = Path().apply {
                moveTo(0f, size.height * .8f)
                cubicTo(size.width * .25f, size.height * .55f, size.width * .55f, size.height * .92f, size.width, size.height * .62f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(path, Color.Black.copy(alpha = .24f), style = Fill)
        }
        Text(
            text = title,
            modifier = Modifier.align(Alignment.BottomStart).padding(14.dp),
            color = Color.White.copy(alpha = .9f),
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun PreviewTopBar(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun PreviewHeading(eyebrow: String, title: String, description: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(eyebrow.uppercase(), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        Text(title, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold, lineHeight = 42.sp)
        Text(description, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun PreviewLabel(text: String) {
    Text(text, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
}

@Composable
private fun PreviewSectionHeading(modifier: Modifier = Modifier, title: String, action: String) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(title, Modifier.weight(1f), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        TextButton(onClick = {}) { Text(action) }
    }
}

@Composable
private fun PreviewSearchBar(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        Row(Modifier.padding(horizontal = 16.dp, vertical = 13.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(ic_search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.size(12.dp))
            Text("Search the cosmos", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun PreviewFilterChip(label: String, selected: Boolean = false) {
    Surface(
        shape = CircleShape,
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainer
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp),
            color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun PreviewNavigation(selectedIndex: Int) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(
            selected = selectedIndex == 0,
            onClick = {},
            icon = { Icon(ic_home, contentDescription = null) },
            label = { Text("Explore") }
        )
        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = {},
            icon = { Icon(ic_favorite, contentDescription = null) },
            label = { Text("Saved") }
        )
    }
}

private class PreviewItemProvider : PreviewParameterProvider<PicOfTheDayItem> {
    override val values = PreviewItems.asSequence()
}
