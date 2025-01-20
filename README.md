# App Demo

[weather_app_quick_demo.webm](https://github.com/user-attachments/assets/798a87bc-98e7-481d-b11e-ffa49162a6ae)

## Things I'd like to change
User having to click the button, I initially had it working but every keystroke was running a network request in the search field. I know the solution to that was to use the debounce on the UI but could not get it to work and spent too much time so I simplified it. Although it does hurt the UX of the app alright.

# Architecture Choices
I used to recommended google architecture practices for this app. I used this because it promotes good seperation of concern and testability.

I also like the fact that it has a unidirectional data flow. What I mean by that is that the state flow flows in one direction whereas the events flow in another direction.

I used the UI layer, domain layer(used very little) and the data layer. 

* The ui layer was used to display application data on screen. I called the UI Layer the presentation layer in my app.
* The domain layer is used for encapsulation of complicated business logic and use cases. In the case of my app, just for use cases.
* The data layer stores the business logic, it contains repositories and data sources. In my app I reused the same repository and the only data source is via network request. However if I was to add a Room DB it would also be a data source.

I also used MVVM architectural pattern which is Model, View and ViewModel because it is great for seperation of concern.

# Added Dependencies
## Retrofit, OKHTTP and Gson Convertor 
I used the above three to make network requests and change them from a Json response to a data class.

## Logging Interceptor
I used logging interceptor to log any issues with the requests, I could have used it also for authentication but in this case I did not need to.

## Koin
I used to Koin for a lightweight dependency injection. 

## Coil
I used coil to load images from a network request url to display in the UI.
