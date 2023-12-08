# Transactor App

This is a simple app following the requirements of the [Application Challenge](https://github.com/MobileSocialXD/ml-app-challenge?tab=readme-ov-file#application-challenge)

## Installation
Clone the repository and open it in Android Studio. Then run the app on a device or emulator.

## Architecture
For the most part this app follows MVVM with clean architecture. The main components are:
- **View**: The view is the UI of the app. It is implemented using Jetpack Compose.
- **ViewModel**: The view model transforms the data collected from repositories to fit the UI. 
- **UseCase**: The use case is the business logic of the app. Avoided using use cases for this app since it is very simple.
- **Repository**: The repository is the data layer of the app. 


## Testing
The app has some unit tests for the view model functions. In a real case scenario, the app would have more tests for the use cases.
