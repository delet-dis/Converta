# Converta

<img src="https://user-images.githubusercontent.com/47276603/126767683-7115ed12-cc1f-4532-a208-f82699c7d841.png" height = "220" align="right" hspace="50">


[![Android CI](https://github.com/delet-dis/Converta/actions/workflows/android.yml/badge.svg)](https://github.com/delet-dis/Converta/actions/workflows/android.yml)

[![codebeat badge](https://codebeat.co/badges/cada9b50-8481-4948-95ac-e4f8eef758c5)](https://codebeat.co/projects/github-com-delet-dis-converta-master)
[![CodeFactor](https://www.codefactor.io/repository/github/delet-dis/converta/badge)](https://www.codefactor.io/repository/github/delet-dis/converta)

Converta is an [Android](https://en.wikipedia.org/wiki/Android_(operating_system)) app aimed at helping deaf and mute people to interact with others.

The goal of the project is to create a easy to access application that can convert speech to text and text to speech, save phrases by category for quick use.

Please check [CONTRIBUTING](CONTRIBUTING.md) page if you want to help.

## Project characteristics and tech-stack

<img src="https://user-images.githubusercontent.com/47276603/128665361-a4772996-030c-4cce-a87f-de1f4ea996da.png" width="336" align="right" hspace="20">

* Tech-stack
    * [100% Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations
    * [Flow](https://developer.android.com/kotlin/flow) - background-receiving data from database
    * [Jetpack](https://developer.android.com/jetpack)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - navigation inside Activity
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - notify observers about database changes
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - event handling based on lifecycle
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
        * [Room](https://developer.android.com/jetpack/androidx/releases/room) - store shortcuts data
        * [Android KTX](https://developer.android.com/kotlin/ktx) -  set of Kotlin extensions
        * [Fragment](https://developer.android.com/jetpack/androidx/releases/fragment) -  using multiple screens inside activity
        * [ViewBinding](https://developer.android.com/topic/libraries/view-binding) -  getting links to interface elements
    * [Firebase crashlytics](https://firebase.google.com/docs/crashlytics) - crash tracking
    * [Google speech services](https://play.google.com/store/apps/details?id=com.google.android.tts&hl=ru&gl=RU) - text recognition and pronunciation
* Modern Architecture
    * Layers architecture
    * MVVM
* CI 
  * [GitHub Actions](https://github.com/features/actions)
  * Automatic code analyzing by 3rd party online tools
* UI
    * [Material design](https://material.io/design)

## Architecture
The entire application follows `layers architecture`.

It contains components that strictly fulfill their functions, as well as parts that are not part of them.
<img src="https://user-images.githubusercontent.com/47276603/128665642-1e3fed60-d608-4cdb-8ae5-9f1ab423ef9c.png" width="700" hspace="5" vspace ="10">

## Data flows
The architecture of interaction between presentation and lower layers in both activities is similar.

All interaction occurs through the `ViewModel` of each `Activity` / `Fragment`, which then receives data from the repositories and notifies the view of the change.

Repositories, in turn, interact with the data services and broadcast receivers layer

> In fact, everything works through the `AndroidViewModel`, but this method differs only in that it can receive the `Application` inside itself. This is used when a `Context` is needed.
### 🛬 OnboardingActivity
<img src="https://user-images.githubusercontent.com/47276603/128665694-3048cb33-cec2-4be7-91d3-135edd72bf93.png" width="600" hspace="5" vspace ="10">

> <img src="https://user-images.githubusercontent.com/47276603/122640649-e3772500-d12a-11eb-98ae-43fc95d000ba.png" width="30" hspace="5"> - request </br>
> <img src="https://user-images.githubusercontent.com/47276603/122640648-e2de8e80-d12a-11eb-869c-e20de1a1f773.png" width="30" hspace="5"> - response

### 🔃 MainActivity

<img src="https://user-images.githubusercontent.com/47276603/128665760-e6ce7ae4-6632-4057-8436-a5de527cca04.png" width="600" hspace="5" vspace ="10">

> <img src="https://user-images.githubusercontent.com/47276603/122640649-e3772500-d12a-11eb-98ae-43fc95d000ba.png" width="30" hspace="5"> - request </br>
> <img src="https://user-images.githubusercontent.com/47276603/122640648-e2de8e80-d12a-11eb-869c-e20de1a1f773.png" width="30" hspace="5"> - response

## More project screenshots
<img src="https://user-images.githubusercontent.com/47276603/128665869-2a1aeb49-1c64-4645-be4b-ffd1c9f18223.jpg" width="200" hspace="5" align="left" vspace ="10">
<img src="https://user-images.githubusercontent.com/47276603/128665885-0c9fd7ee-6c94-4867-b861-77e156da0582.jpg" width="200" hspace="5" vspace ="10">
<br/>

<img src="https://user-images.githubusercontent.com/47276603/128665916-0169b0b1-dc07-4e9e-88b5-a640d4552b6e.jpg" width="200" hspace="5" align="left" vspace ="10">
<img src="https://user-images.githubusercontent.com/47276603/128665942-840d88df-7343-41cd-a088-aa70a9bd425e.jpg" width="200" hspace="5" vspace ="10">
<br/>

<img src="https://user-images.githubusercontent.com/47276603/128666069-a9857431-fdcb-4c22-a55e-4f7aa3af9310.jpg" width="200" hspace="5" align="left" vspace ="10">
<img src="https://user-images.githubusercontent.com/47276603/128666093-5f5e5531-ebc6-40d4-9e7a-f47c1c5f479b.jpg" width="200" hspace="5" vspace ="10">
<br/>

<img src="https://user-images.githubusercontent.com/47276603/128666121-63f8eb95-f3a8-4943-9766-93f7b213eb9d.png" width="200" hspace="5" align="left" vspace ="10">
<img src="https://user-images.githubusercontent.com/47276603/128666176-beb26aec-1419-4ec3-9ea7-5748d52d2914.png" width="200" hspace="5" vspace ="10">
<br/>

<img src="https://user-images.githubusercontent.com/47276603/128666199-6d69a203-d240-4f48-a879-bd5fbe4c07ae.png" width="200" hspace="5" align="left" vspace ="10">
<img src="https://user-images.githubusercontent.com/47276603/128666240-50969df8-7227-4b48-a70d-72bc86a87b2a.png" width="200" hspace="5" vspace ="10">
<br/>

<img src="https://user-images.githubusercontent.com/47276603/128666277-3648414e-d404-4a58-a37f-cbfd776c6bd9.png" width="200" hspace="5" vspace ="10">
<img src="https://user-images.githubusercontent.com/47276603/128666292-189048af-3f58-427c-806f-eae5bb8eb6b0.png" width="200" hspace="5" align="left" vspace ="10">
<br/>

<img src="https://user-images.githubusercontent.com/47276603/128666340-8daf2044-b0a9-43ba-ac05-1d044c50d3ed.png" width="200" hspace="5"  vspace ="10">
<br/>

## License
```
MIT License

Copyright (c) 2021 Igor Efimov

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
