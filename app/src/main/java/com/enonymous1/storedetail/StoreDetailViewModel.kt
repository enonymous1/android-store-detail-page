package com.enonymous1.storedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Owns the only interactive state on the page: whether the demo app is
 * "installed", whether What's New is expanded, and whether all reviews
 * are shown. Values are mirrored into [SavedStateHandle] so a recreated
 * process restores them instead of resetting the page.
 */
class StoreDetailViewModel(
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        StoreDetailUiState(
            isInstalled = savedStateHandle[KEY_INSTALLED] ?: false,
            whatsNewExpanded = savedStateHandle[KEY_WHATS_NEW] ?: false,
            showAllReviews = savedStateHandle[KEY_SHOW_ALL] ?: false,
        ),
    )
    val uiState: StateFlow<StoreDetailUiState> = _uiState.asStateFlow()

    fun onInstallClick(onOpen: () -> Unit) {
        if (_uiState.value.isInstalled) {
            onOpen()
        } else {
            updateState { toggleInstall(it) }
        }
    }

    fun onWhatsNewClick() {
        updateState { toggleWhatsNew(it) }
    }

    fun onAllReviewsClick() {
        updateState { toggleShowAllReviews(it) }
    }

    private fun updateState(transform: (StoreDetailUiState) -> StoreDetailUiState) {
        _uiState.update { current ->
            val next = transform(current)
            savedStateHandle[KEY_INSTALLED] = next.isInstalled
            savedStateHandle[KEY_WHATS_NEW] = next.whatsNewExpanded
            savedStateHandle[KEY_SHOW_ALL] = next.showAllReviews
            next
        }
    }

    companion object {
        private const val KEY_INSTALLED = "installed"
        private const val KEY_WHATS_NEW = "whats_new_expanded"
        private const val KEY_SHOW_ALL = "show_all_reviews"
    }
}
