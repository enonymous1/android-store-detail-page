# Changelog

## 2.0.0 — 2026-10-26

Modernization of the original Full Sail store-detail-page assignment
(v1.0). The page shows the same sections — masthead, app header,
What's New, screenshots, reviews, developer footer — but as a
data-driven Compose screen with buttons that actually do something.

This public repository is a squashed export of that modernization: the
code and tests below are the v2.0.0 tree, in a single commit, without
the earlier private history.

### Changed

- Rewrote in Kotlin with Jetpack Compose and Material 3; the 624-line
  XML layout, AppCompat Activity, and ConstraintLayout are gone.
- Replaced five copy-pasted review blocks with one `ReviewRow`
  composable rendered from a review list, and the whole page with one
  `FeaturedApp` data model.
- Replaced the original's dead buttons: Install toggles to Open, Share
  sends a real share intent, Read More expands What's New, and All
  Reviews shows the full list. Open and the footer rows say plainly
  that the featured app is fictional, instead of pretending to act.
- Replaced the original course assets with original demo content: a
  gradient masthead and a simple "FN" app icon, a fictional featured
  app (Field Notes / Northlight Studio), fictional reviews, and
  labeled screenshot cards. The original masthead (a stock iOS-vs-
  Android graphic) and its screenshots placeholders are not carried over.
- Renamed the application ID and package from
  `com.example.lyonsjustin_ce04` to `com.enonymous1.storedetail`,
  and the app name to Store Detail Page.
- Toolchain: AGP 8.13.0, Gradle 8.14.3, Kotlin 2.2.0, Java 17,
  compile/target SDK 36, minSdk 24, Kotlin DSL build scripts, and a
  version catalog (`gradle/libs.versions.toml`).
- Release builds now use R8 shrinking (`minifyEnabled`,
  `shrinkResources`).
- Removed the assignment TODO comments, the `.idea` directory (now
  ignored), and the stale "GitHub Preparation" instructions from the
  README.

### Added

- Real tests: rating/review-count formatting, initials, star text,
  What's New expansion, collapsed vs. full reviews, share text, and
  ViewModel toggles, plus a package-name instrumentation test and
  deterministic Compose UI tests for install, expansion, and reviews.
- Page state (installed, expanded, all reviews) is kept in a
  `ViewModel` and mirrored into `SavedStateHandle`, so process
  recreation restores it.
- GitHub Actions CI: unit tests plus a debug build on every push and
  pull request to `main`.
- MIT LICENSE, this changelog, and a `.gitattributes` file normalizing
  line endings to LF.

### Notes and non-goals

- This is a detail-page demo, not a store: it installs nothing, opens
  no real app, and loads no real listing. That is deliberate — the
  original was a look-and-feel exercise, and faking a real listing
  would be worse than a clearly fictional one.
- The screenshots are labeled demo cards, not captures of a real app.

## 1.0 — 2026-09-06

Original release: Java 8, AGP 8.0.2, SDK 33, a single Activity hosting
a static XML recreation of an app-detail page. Buttons had no click
handling; reviews were placeholder text; screenshots were empty
placeholders.
