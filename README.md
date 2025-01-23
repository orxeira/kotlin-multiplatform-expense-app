# ExpenseApp
ExpenseApp is a technical test for a selection process. It is a very basic proof of concept for an app that registers and shows your expenses.

## Platforms
| ![](https://img.shields.io/badge/Android-black.svg?style=for-the-badge&logo=android) | ![](https://img.shields.io/badge/iOS-black.svg?style=for-the-badge&logo=apple) | ![](https://img.shields.io/badge/Desktop-black.svg?style=for-the-badge&logo=windows) | ![](https://img.shields.io/badge/Web-black.svg?style=for-the-badge&logo=google-chrome) |
|:------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------:|
|                                          ✅                                           |                                       ✅                                        |                                          ❌                                           |                                           ❌                                            |

## Architecture
The app is shared between Android and iOS. The shared code is written in Kotlin and the UI is built with Compose Multiplatform. Shared code, written in Kotlin, is compiled to JVM bytecode for Android with Kotlin/JVM and to native binaries for iOS with Kotlin/Native. 

### Modules
- shared:
  - contains all the shared code between the platforms
- android:
  - contains the android app
- ios:
  - contains the ios app

### Design
The app follows CLEAN architecture and the MVVM design pattern. 

- Domain layer: is found in `shared/[...]/core/domain`. This layer defines the business models and contracts. It is completely independent of any other layers.
- Data layer: is found in `shared/[...]/core/data`. This layer handles all data management. In this case it implements the repository interface defined by the domain in order to handle the requests from the presentation layer. It is independent of all layers except for domain.
- Presentation layer is split into different packages:\
  - `shared/[...]/core/ui`: Contains all the reusablel components, navigation, themes and utils.
  - `shared/[...]/main`: Contains the viwe MainScreen which is the entry point for the app which will host all the other views.
  - `shared/[...]/features`: Contains all the other Views and ViewModels.
- `shared/[...]/di`: Contains all dependency injection logic for Koin.
- `shared/[...]/platform`: Defines the expected classes and functions that must be implemented on the platform side. 

## Built with
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) - The Kotlin Multiplatform technology is designed to simplify the development of cross-platform projects.
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) -  a modern UI framework for Kotlin that makes building performant and beautiful user interfaces easy and enjoyable.
- [SQLDelight](https://github.com/cashapp/sqldelight) - SQLDelight is an open-source library developed by Cash App (formerly Square, Inc.) for working with SQL databases in Kotlin-based Android and multi-platform applications.
- [Koin](https://insert-koin.io/) - The pragmatic Kotlin & Kotlin Multiplatform Dependency Injection framework.
- [Type Safe Compose Navigation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation-routing.html) - Type safety in Kotlin DSL and Navigation Compose
- [AndroidX ViewModel](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-viewmodel.html) - The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way.
- [Kotlinx-datetime](https://github.com/Kotlin/kotlinx-datetime) - KotlinX multiplatform date/time library.
- [Kotlinx-serialization](https://github.com/Kotlin/kotlinx.serialization) - Kotlin multiplatform / multi-format serialization.
- [Compose Components Resources](https://mvnrepository.com/artifact/org.jetbrains.compose.components/components-resources) - Resources For Compose Multiplatform.
- [Material3 Window Size Multiplatform](https://github.com/chrisbanes/material3-windowsizeclass-multiplatform) - About Material 3 Window Size Class for Compose Multiplatform.

## Run project
### Android
To run the application on android device/emulator:
- open project in Android Studio and run imported android run configuration

### iOS
To run the application on iPhone device/simulator:
- Open `ios/iosApp.xcworkspace` in Xcode and run standard configuration
- Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio