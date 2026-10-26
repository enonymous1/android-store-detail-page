# Store Detail Page

An Android app-detail page, rebuilt the way a real listing screen is
built: one data model, rendered in Jetpack Compose, instead of a single
static XML layout with the same review block pasted five times.

It shows a fictional app — Field Notes, by the fictional Northlight
Studio — with a masthead, rating, Install/Open and Share actions, a
What's New section, screenshots, reviews, and a developer footer. Tap
Install and it becomes Open; tap Read More and What's New expands; tap
All Reviews and the full list appears; Share sends a real Android share
intent. Nothing here is a real app, a real review, or a screenshot of a
real store listing.

Originally a Full Sail assignment in Java and XML; v2.0 rewrote it in
Kotlin with Compose and a ViewModel. See [CHANGELOG.md](CHANGELOG.md)
for what changed and what was deliberately left out.

This public repository is a squashed export of that v2.0.0
modernization: a single commit, with the earlier private history
intentionally not included. It is not affiliated with Google Play or
any app store.

## Tech stack

- Kotlin 2.2.0, Jetpack Compose, Material 3
- ViewModel + StateFlow, `SavedStateHandle` for process recreation
- Android Gradle Plugin 8.13.0, Gradle 8.14.3 (via the wrapper), JDK 17
- compile/target SDK 36, minSdk 24
- JUnit 4, Compose UI tests

The application ID is `com.enonymous1.storedetail`.

## Features

- Masthead, app header, rating, and review count from one data model.
- Install toggles to Open; Open explains honestly that the featured
  app is fictional demo data, rather than pretending to launch it.
- Share sends the real Android share sheet with the featured app's
  name, developer, and rating.
- What's New expands and collapses; reviews show 3 at first, then all 5.
- Screenshots are labeled demo cards, not fake captures of a real app.
- Page state (installed, expanded, all reviews) survives process
  recreation through `SavedStateHandle`.

## Folder structure

```text
store-detail-page/
├── .github/workflows/android.yml     # CI: unit tests + debug build
├── app/
│   ├── build.gradle.kts             # App module configuration
│   ├── proguard-rules.pro           # Release shrinker rules
│   └── src/
│       ├── androidTest/             # Instrumentation + Compose UI tests
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/enonymous1/storedetail/
│       │   │   ├── MainActivity.kt         # Compose host, edge-to-edge
│       │   │   ├── StoreDetailScreen.kt    # Material 3 UI
│       │   │   ├── StoreDetailViewModel.kt # Page state, SavedStateHandle
│       │   │   └── StoreDetail.kt          # Data model + pure formatting
│       │   └── res/                 # Strings, themes, icons, backup rules
│       └── test/                    # Local JVM unit tests
├── gradle/
│   ├── libs.versions.toml           # Version catalog
│   └── wrapper/                     # Gradle Wrapper configuration
├── build.gradle.kts                 # Root plugin configuration
├── gradle.properties                # AndroidX and Gradle settings
├── gradlew / gradlew.bat            # Gradle Wrapper scripts
└── settings.gradle.kts              # Project and module registration
```

## Requirements

- Android Studio with Android SDK 36 installed
- JDK 17 (Android Studio's bundled JDK works)
- An Android emulator or physical device running API 24 or newer

## Setup and run

1. Clone the repository and open the project directory in Android Studio.
2. Let Android Studio sync the Gradle project and install any requested
   SDK components.
3. Select an emulator or connected Android device.
4. Run the `app` configuration from Android Studio.

From a terminal at the project root, the debug build can also be created with:

```powershell
.\gradlew.bat assembleDebug
```

The generated APK is written to `app/build/outputs/apk/debug/app-debug.apk`.
For a release build, use `assembleRelease` (R8 shrinking is enabled).

## Testing

Run local unit tests with:

```powershell
.\gradlew.bat testDebugUnitTest
```

Run connected-device instrumentation tests with:

```powershell
.\gradlew.bat connectedDebugAndroidTest
```

Unit tests cover rating/review-count formatting, initials, star text,
What's New expansion, collapsed vs. full review lists, share text, and
ViewModel install/expand toggles. The UI tests are deterministic —
featured app and first review visible, Install becomes Open, Read More
expands What's New, All Reviews reveals the fifth review.

## Non-goals

This is a detail-page demo, not a store. It installs nothing, opens no
real app, contacts no developer, and loads no real listing, screenshots,
or reviews. It collects nothing, uses no network, and requests no
permissions.

## License

MIT — see [LICENSE](LICENSE).
