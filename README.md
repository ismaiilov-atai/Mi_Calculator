# Mi_Calculator
The Mi_Calculator app was copied from Mi build Calculator app written in Kotlin. The app provides standard, scientific, and display sharing with app choosen by you functionality,
as well as a set of converters between various units of measurement and currencies.

![rsz_4firs_page](https://user-images.githubusercontent.com/63174725/118951122-a3325480-b97c-11eb-97a3-8f65630dd8c5.jpg)

# Features
* Standard Calculator functionality which offers basic operations and evaluates commands immediately as they are entered.
* Scientific Calculator functionality which offers expanded operations and evaluates commands using order of operations.
* Date Calculation functionality which offers the difference between two dates, as well as the ability to add/subtract years, months and/or days to/from a given input date.
* Calculation history and memory capabilities.
* Conversion between many units of measurement.
* Currency conversion based on data retrieved from https://api.fastforex.io.
* Infinite precision for basic arithmetic operations (addition, subtraction, multiplication, division) so that calculations never lose precision.

![second](https://user-images.githubusercontent.com/63174725/118953719-e68dc280-b97e-11eb-8c4c-f98e74d6b614.jpg)

# Getting started

* Get the code.

```
https://github.com/AtaiDev/Mi_Calculator.git
```
* Open src\Mi-Calculator in Android Studio to build and run the Calculator app.
* To run and use full functionality you need to check for your android phone version.

# Animation and Graphics 

* Adding animation to calculator i believe that it gives end-user great experience. 
* To that end-user experience added slide-animation, alert  dialogs, pick effects, share images by app which you desired. 
* Below gifts will demonstrate which animation you will face during using this application.
* 

![5a98pg](https://user-images.githubusercontent.com/63174725/118954706-d9bd9e80-b97f-11eb-99a3-ccead7d2a032.gif) ![extra_view](https://user-images.githubusercontent.com/63174725/118956408-64eb6400-b981-11eb-94fd-7d1c4c18ad7b.gif) ![5a9ha6](https://user-images.githubusercontent.com/63174725/118967830-fb715280-b98c-11eb-9d79-d7dc07358cdd.gif)

# Currency Converter

Since converting money is one of the important thing in our life and I just wanted to describe the functionality of this converter. 
Its USD based converter which will convert only from the USD to other currencies.  

# Used libraries

* The Room persistence library https://developer.android.com/training/data-storage/room
* The Retrofit https://square.github.io/retrofit/
* Kotlin Extensions and Coroutines support for Room https://developer.android.com/kotlin/coroutines
* Firebase Crashlytics https://firebase.google.com/docs/crashlytics
* Firebase Analytics https://firebase.google.com/docs/analytics

# Architecture

* MVVM
* Separation of concerns
* Drive UI from a model
* BaseActivity 
* BaseFragment
* BaseDialog
* BaseViewModel

