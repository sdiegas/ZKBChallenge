# Challenge

## Description
Target was to create a simple Registration App in Android.

## Features
- Registration Form with Name, Email and Birthday Validation
- Confirmation Screen with Registration Information
- Persist Registration Data securely in the App
- Load persisted name and email and set it in Registration View (exception for birthday)

### Technologies
- Data Binding
- LiveData
- Hilt
- EncryptedSharedPreferences

### Improvements

Due to the limited time, the solution is not yet perfect.

- Currently Error Messages are defined in Constants, better to move them to String ressources to also make them localizable.
- There are no InstrumentedTests yet, compared to Unit Tests, InstrumentedTests are more time intensive to write and offer less benefit, with more time -> write Instrumented Tests.
- Live Input Validation, instead of only validating the user input when clicking on the button, we could validate on typing.
- Organise Dependencies better, introduce versions.gradle file.
- Layouts and drawables currently in one folder, create sub folders.
- Kotlinx.Serialization is still experimental had to add @OptIn(ExperimentalSerializationApi::class) Annotation, maybe using Jackson or GSON would be better.
- Working with more Interfaces, it makes sense, to work with more interfaces, specially in the domain and data layer.
- We persist the Registration data name, email and birthday and set it, when restarting the App after a successful registration. To still be able to test the birthday validator, the birthday is not set.

There might be more improvements, which we can discuss in the Interview.
