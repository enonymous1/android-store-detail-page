package com.enonymous1.storedetail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Deterministic UI checks only. Formatting and state rules are covered by
 * the JVM [StoreDetailTest]; this test does not depend on timing.
 */
@RunWith(AndroidJUnit4::class)
class StoreDetailScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun featuredAppAndFirstReviewAreDisplayed() {
        // "Field Notes" appears twice (masthead and header), so check the
        // unique developer name instead.
        composeRule.onNodeWithText("Northlight Studio").assertIsDisplayed()
        composeRule.onNodeWithText("Maya R.").assertIsDisplayed()
    }

    @Test
    fun installButtonBecomesOpenAfterTap() {
        composeRule.onNodeWithTag("install_button").performClick()
        composeRule.onNodeWithText("Open").assertIsDisplayed()
    }

    @Test
    fun readMoreExpandsWhatsNew() {
        composeRule.onNodeWithTag("read_more_button").performClick()
        composeRule.onNodeWithText(sampleApp.whatsNewFull).assertIsDisplayed()
    }

    @Test
    fun allReviewsRevealsFifthReview() {
        // The fifth review is hidden while the list is collapsed, and may
        // be off-screen in the lazy list once revealed, so scroll to it.
        composeRule.onNodeWithTag("all_reviews_button").performClick()
        composeRule.onNodeWithText("Alex J.").performScrollTo().assertIsDisplayed()
    }
}
