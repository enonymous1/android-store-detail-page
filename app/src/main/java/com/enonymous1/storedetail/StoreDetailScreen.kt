package com.enonymous1.storedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * A store detail page rebuilt as data-driven Compose. The original was a
 * single 624-line XML layout with five copy-pasted review blocks and no
 * click handling; here one [ReviewRow] renders every review from
 * [sampleApp], and every button does something.
 *
 * [onShare] receives the share text (the host sends a real share intent),
 * [onOpen] and [onInfo] receive a short message the host shows, because
 * the featured app is fictional demo data, not something installed here.
 */
@Composable
fun StoreDetailScreen(
    viewModel: StoreDetailViewModel = viewModel(),
    onShare: (String) -> Unit = {},
    onOpen: () -> Unit = {},
    onInfo: (String) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsState()
    val app = sampleApp

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            // Masthead: an original gradient panel, not the course image
            // (a stock iOS-vs-Android graphic) from the original project.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.tertiaryContainer,
                            ),
                        ),
                    )
                    .semantics { contentDescription = "Masthead image" },
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AppIcon(size = 72.dp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = app.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Text(
                        text = "Notes, anywhere.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AppIcon(size = 56.dp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = app.name,
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text = app.developer,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            text = "${formatRating(app.rating)} · ${formatReviewCount(app.reviewCount)}",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .testTag("install_button"),
                        onClick = { viewModel.onInstallClick(onOpen) },
                    ) {
                        Text(if (state.isInstalled) "Open" else "Install")
                    }
                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f)
                            .testTag("share_button"),
                        onClick = { onShare(shareText(app)) },
                    ) {
                        Text("Share")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "WHAT'S NEW",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = whatsNewText(app, state.whatsNewExpanded))
                TextButton(
                    modifier = Modifier.testTag("read_more_button"),
                    onClick = { viewModel.onWhatsNewClick() },
                ) {
                    Text(if (state.whatsNewExpanded) "SHOW LESS" else "READ MORE")
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Screenshots",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    app.screenshots.forEach { screenshot ->
                        Card(
                            modifier = Modifier
                                .size(width = 140.dp, height = 200.dp)
                                .semantics { contentDescription = screenshot.title },
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = screenshot.title,
                                        style = MaterialTheme.typography.titleMedium,
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = screenshot.description,
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Reviews",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        visibleReviews(app, state.showAllReviews).forEach { review ->
            item {
                ReviewRow(
                    review = review,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )
            }
        }

        item {
            Column(modifier = Modifier.padding(16.dp)) {
                TextButton(
                    modifier = Modifier.testTag("all_reviews_button"),
                    onClick = { viewModel.onAllReviewsClick() },
                ) {
                    Text(if (state.showAllReviews) "SHOW FEWER" else "ALL REVIEWS")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "DEVELOPER",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                TextButton(onClick = { onInfo("Demo detail page: no real developer contact is linked.") }) {
                    Text("Send email")
                }
                TextButton(onClick = { onInfo("Demo detail page: this app requests no permissions.") }) {
                    Text("Permission details")
                }
                TextButton(onClick = { onInfo("Demo detail page: nothing to flag — all reviews here are fictional demo data.") }) {
                    Text("Flag as inappropriate")
                }
            }
        }
    }
}

@Composable
private fun AppIcon(size: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .semantics { contentDescription = "Field Notes app icon" },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "FN",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun ReviewRow(review: Review, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = initials(review.author),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = review.author, style = MaterialTheme.typography.titleSmall)
            Text(text = "${starText(review.rating)} · ${review.date}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = review.text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
