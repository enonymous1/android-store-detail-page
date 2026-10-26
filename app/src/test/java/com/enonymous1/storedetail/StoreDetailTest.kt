package com.enonymous1.storedetail

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StoreDetailTest {

    @Test
    fun rating_formatsToOneDecimal() {
        assertEquals("4.6", formatRating(4.6))
        assertEquals("5.0", formatRating(5.0))
    }

    @Test
    fun reviewCount_formatsWithThousandsSeparator() {
        assertEquals("1,284 reviews", formatReviewCount(1284))
        assertEquals("42 reviews", formatReviewCount(42))
    }

    @Test
    fun initials_useFirstLettersOfFirstTwoWords() {
        assertEquals("MR", initials("Maya R."))
        assertEquals("P", initials("Priya"))
    }

    @Test
    fun starText_clampsAndPadsToFive() {
        assertEquals("★★★★☆", starText(4))
        assertEquals("★★★★★", starText(9))
        assertEquals("★☆☆☆☆", starText(0))
    }

    @Test
    fun whatsNew_collapsedShowsPreviewExpandedShowsFull() {
        assertEquals(sampleApp.whatsNewPreview, whatsNewText(sampleApp, false))
        assertEquals(sampleApp.whatsNewFull, whatsNewText(sampleApp, true))
        assertTrue(sampleApp.whatsNewFull.length > sampleApp.whatsNewPreview.length)
    }

    @Test
    fun visibleReviews_collapsedShowsThreeExpandedShowsAll() {
        assertEquals(COLLAPSED_REVIEW_COUNT, visibleReviews(sampleApp, false).size)
        assertEquals(sampleApp.reviews.size, visibleReviews(sampleApp, true).size)
        assertEquals(5, sampleApp.reviews.size)
    }

    @Test
    fun shareText_namesAppAndDeveloper() {
        val text = shareText(sampleApp)
        assertTrue(text.contains("Field Notes"))
        assertTrue(text.contains("Northlight Studio"))
        assertTrue(text.contains("4.6"))
    }

    @Test
    fun pureToggles_flipExactlyOneFlag() {
        val start = StoreDetailUiState()
        assertTrue(toggleInstall(start).isInstalled)
        assertFalse(toggleInstall(start).whatsNewExpanded)
        assertTrue(toggleWhatsNew(start).whatsNewExpanded)
        assertTrue(toggleShowAllReviews(start).showAllReviews)
    }

    @Test
    fun viewModel_installClickInstallsThenOpens() {
        val viewModel = StoreDetailViewModel(SavedStateHandle())
        assertFalse(viewModel.uiState.value.isInstalled)
        var opened = false
        viewModel.onInstallClick { opened = true }
        assertTrue(viewModel.uiState.value.isInstalled)
        assertFalse(opened)
        viewModel.onInstallClick { opened = true }
        assertTrue(opened)
    }

    @Test
    fun viewModel_togglesWhatsNewAndReviews() {
        val viewModel = StoreDetailViewModel(SavedStateHandle())
        viewModel.onWhatsNewClick()
        assertTrue(viewModel.uiState.value.whatsNewExpanded)
        viewModel.onAllReviewsClick()
        assertTrue(viewModel.uiState.value.showAllReviews)
    }
}
