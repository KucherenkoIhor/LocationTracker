# LocationTracker
An example that shows how to track location when app is in background. It tracks location using ```FusedLocationProvideClient``` every 15 minutes when app in background and every 20 seconds in foreground.
When app goes in background we schedule a task using the ```setExactAndAllowWhileIdle``` of ```AlarmManager``` that launches a foreground service and saves received location to local database. App follows the Clean Architecture pattern.

The test folder contains a test of use case using the Spek framework.

## Screenshots
App contains a screen with a map that shows the last saved location if it exist and it is not older than 1 hour. It also request location updates and moves a marker according to updates.
<p>
  <img src ="https://raw.githubusercontent.com/KucherenkoIhor/LocationTracker/master/screenshots/2.jpg", height=500/>
</p>
 App also contains a screen with a history of locations saved to database in background.
<p>
 <img src ="https://raw.githubusercontent.com/KucherenkoIhor/LocationTracker/master/screenshots/1.jpg", height=500/>
</p>

## Libraries
* [Android Support Library][support-lib]
* [Android Architecture Components][arch]
* [Kodein][kodein]
* [Conductor][conductor]
* [Spek][spek]

[support-lib]: https://developer.android.com/topic/libraries/support-library/index.html
[arch]: https://developer.android.com/arch
[kodein]: https://kodein.org
[conductor]: https://github.com/bluelinelabs/Conductor
[spek]: https://spekframework.org
