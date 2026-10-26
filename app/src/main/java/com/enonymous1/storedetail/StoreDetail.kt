package com.enonymous1.storedetail

import java.text.NumberFormat
import java.util.Locale

/**
 * Pure data and formatting for a store detail page. Nothing here touches
 * Android APIs, so every rule below is covered by a plain JVM unit test.
 *
 * The featured app, its screenshots, and its reviews are fictional demo
 * data written for this project. They are not a real app, real reviews,
 * or a screenshot of any real store listing.
 */
data class Review(
    val author: String,
    val rating: Int,
    val date: String,
    val text: String,
)

data class Screenshot(
    val title: String,
    val description: String,
)

data class FeaturedApp(
    val name: String,
    val developer: String,
    val rating: Double,
    val reviewCount: Int,
    val whatsNewPreview: String,
    val whatsNewFull: String,
    val screenshots: List<Screenshot>,
    val reviews: List<Review>,
)

data class StoreDetailUiState(
    val isInstalled: Boolean = false,
    val whatsNewExpanded: Boolean = false,
    val showAllReviews: Boolean = false,
)

/** Number of reviews shown before the user taps "All reviews". */
const val COLLAPSED_REVIEW_COUNT = 3

val sampleApp = FeaturedApp(
    name = "Field Notes",
    developer = "Northlight Studio",
    rating = 4.6,
    reviewCount = 1284,
    whatsNewPreview = "Offline notebooks and a faster editor.",
    whatsNewFull = "Offline notebooks and a faster editor. " +
        "Notes now sync when you are back online, search covers " +
        "attachments, and the editor keeps your place when you switch notebooks.",
    screenshots = listOf(
        Screenshot("Capture", "A quick note, started in one tap."),
        Screenshot("Organize", "Notebooks, tags, and search in one place."),
        Screenshot("Share", "Share a single note or a whole notebook."),
    ),
    reviews = listOf(
        Review("Maya R.", 5, "October 2026", "Offline mode is the reason I stayed. My notes are there on the trail, no signal needed."),
        Review("Daniel K.", 5, "September 2026", "Search actually finds things, including text inside attachments. That alone is worth it."),
        Review("Priya S.", 4, "September 2026", "Clean editor and sensible notebooks. I would like a few more export options."),
        Review("Tom W.", 5, "August 2026", "Switched from three other note apps. This is the first one I open every morning."),
        Review("Alex J.", 4, "August 2026", "Fast, quiet, and stays out of the way. Sync has been uneventful, in the best sense."),
    ),
)

/** "4.6" for a 4.6 rating; always one decimal place, never locale-dependent. */
fun formatRating(rating: Double): String =
    String.format(Locale.US, "%.1f", rating)

/** "1,284 reviews" for 1284, with a US thousands separator. */
fun formatReviewCount(reviewCount: Int): String =
    "${NumberFormat.getIntegerInstance(Locale.US).format(reviewCount)} reviews"

/** "Maya R." -> "MR", used for the avatar placeholder. */
fun initials(author: String): String =
    author.split(" ").filter { it.isNotEmpty() }.take(2)
        .joinToString("") { it.first().uppercaseChar().toString() }

/** "★★★★☆" for a 4-star review. Ratings are clamped to 1..5. */
fun starText(rating: Int): String {
    val clamped = rating.coerceIn(1, 5)
    return "★".repeat(clamped) + "☆".repeat(5 - clamped)
}

fun whatsNewText(app: FeaturedApp, expanded: Boolean): String =
    if (expanded) app.whatsNewFull else app.whatsNewPreview

fun visibleReviews(app: FeaturedApp, showAll: Boolean): List<Review> =
    if (showAll) app.reviews else app.reviews.take(COLLAPSED_REVIEW_COUNT)

fun shareText(app: FeaturedApp): String =
    "Check out ${app.name} by ${app.developer} " +
        "— rated ${formatRating(app.rating)} on this demo detail page."

fun toggleInstall(state: StoreDetailUiState): StoreDetailUiState =
    state.copy(isInstalled = !state.isInstalled)

fun toggleWhatsNew(state: StoreDetailUiState): StoreDetailUiState =
    state.copy(whatsNewExpanded = !state.whatsNewExpanded)

fun toggleShowAllReviews(state: StoreDetailUiState): StoreDetailUiState =
    state.copy(showAllReviews = !state.showAllReviews)
