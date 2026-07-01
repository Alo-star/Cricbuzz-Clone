🏏 Cricbuzz Clone (Android)

A Cricbuzz-inspired Android application UI/UX clone built using native Android (Java + XML).
This project focuses on replicating real-world cricket app design with clean layouts, modular screens, and offline data handling.

📱 Overview

This project is a static + local-data cricket app clone inspired by Cricbuzz.
It is built purely for learning Android development, UI design, and app architecture practice.

Key focus:

Clean UI replication of Cricbuzz
Multi-screen navigation flow
Local database integration (SQLite)
Production-style architecture (MVVM upgrade)
✨ Features
🏠 Home screen with match cards
🏏 Live / Upcoming match UI
📊 Static scorecard screen
📰 News feed layout
👥 Team & player profiles
📅 Series & tournament listing
⭐ Favorites system (Room DB)
🔍 Match details with commentary UI
📱 Bottom navigation + drawer menu
💾 Offline support (cached data)
🔄 Pull-to-refresh & auto update simulation
🛠️ Tech Stack
Layer	Technology
Language	Java
UI	XML (Material Design)
Architecture	MVVM
Networking	Retrofit + OkHttp
Database	SQLite + Room
Async	LiveData / ViewModel
IDE	Android Studio
🧠 Architecture (Production Upgrade)
MVVM Pattern
ViewModel handles UI logic
Repository manages data sources
Activities only render state
API Layer
Retrofit-based networking
OkHttp interceptor for API key handling
Local Storage
Room database for caching matches
Favorites stored offline
State Handling
Loading / Success / Error states
Cached fallback when offline
📸 Screenshots

Add your screenshots here for best presentation

📌 Suggested layout:

| Home Screen | Live Matches | Match Details |
|-------------|--------------|----------------|
| 🖼️          | 🖼️           | 🖼️             |

| Teams Page  | News Feed    | Player Profile |
|-------------|--------------|----------------|
| 🖼️          | 🖼️           | 🖼️             |
🚀 Getting Started
1. Clone the repo
git clone https://github.com/Alo-star/Cricbuzz-Clone.git
2. Open in Android Studio
File → Open → Select project folder
3. Sync Gradle
Let dependencies download automatically
4. Run the app
Click ▶ Run or press Shift + F10
Cricbuzz-Clone/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │
│   │   │   ├── java/com/cricbuzzclone/
│   │   │   │
│   │   │   │   ├── ui/
│   │   │   │   │   ├── activities/
│   │   │   │   │   │   ├── MainActivity.java
│   │   │   │   │   │   ├── HomeActivity.java
│   │   │   │   │   │   ├── MatchDetailsActivity.java
│   │   │   │   │   │   ├── TeamProfileActivity.java
│   │   │   │   │   │   ├── PlayerProfileActivity.java
│   │   │   │   │   │   ├── SeriesActivity.java
│   │   │   │   │   │   └── NewsActivity.java
│   │   │   │   │
│   │   │   │   │   ├── fragments/
│   │   │   │   │   │   ├── HomeFragment.java
│   │   │   │   │   │   ├── LiveMatchesFragment.java
│   │   │   │   │   │   ├── UpcomingMatchesFragment.java
│   │   │   │   │   │   ├── FavoritesFragment.java
│   │   │   │   │   │   └── NewsFragment.java
│   │   │   │   │
│   │   │   │   │   └── adapters/
│   │   │   │   │       ├── MatchAdapter.java
│   │   │   │   │       ├── TeamAdapter.java
│   │   │   │   │       ├── PlayerAdapter.java
│   │   │   │   │       └── NewsAdapter.java
│   │   │   │
│   │   │   │   ├── viewmodel/
│   │   │   │   │   ├── LiveMatchViewModel.java
│   │   │   │   │   ├── UpcomingMatchViewModel.java
│   │   │   │   │   ├── MatchDetailsViewModel.java
│   │   │   │   │   └── FavoritesViewModel.java
│   │   │   │
│   │   │   │   ├── repository/
│   │   │   │   │   ├── MatchRepository.java
│   │   │   │   │   ├── TeamRepository.java
│   │   │   │   │   └── NewsRepository.java
│   │   │   │
│   │   │   │   ├── data/
│   │   │   │   │   ├── local/
│   │   │   │   │   │   ├── AppDatabase.java
│   │   │   │   │   │   ├── dao/
│   │   │   │   │   │   │   ├── MatchDao.java
│   │   │   │   │   │   │   ├── TeamDao.java
│   │   │   │   │   │   │   └── FavoritesDao.java
│   │   │   │   │   │   └── entity/
│   │   │   │   │   │       ├── MatchEntity.java
│   │   │   │   │   │       ├── TeamEntity.java
│   │   │   │   │   │       └── FavoriteEntity.java
│   │   │   │   │
│   │   │   │   │   ├── remote/
│   │   │   │   │   │   ├── ApiService.java
│   │   │   │   │   │   ├── RetrofitClient.java
│   │   │   │   │   │   └── Interceptor.java
│   │   │   │   │
│   │   │   │   ├── model/
│   │   │   │   │   ├── Match.java
│   │   │   │   │   ├── Team.java
│   │   │   │   │   ├── Player.java
│   │   │   │   │   └── News.java
│   │   │   │
│   │   │   │   ├── utils/
│   │   │   │   │   ├── Constants.java
│   │   │   │   │   ├── Resource.java
│   │   │   │   │   ├── NetworkUtils.java
│   │   │   │   │   └── AppExecutors.java
│   │   │   │
│   │   │   │   └── App.java
│   │   │
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── drawable/
│   │   │   ├── menu/
│   │   │   ├── navigation/
│   │   │   ├── values/
│   │   │   └── xml/
│   │   │
│   │   └── AndroidManifest.xml
│   │
│   ├── build.gradle
│
├── gradle/
├── build.gradle
├── settings.gradle
└── README.md

This project is created only for educational purposes.
It is not affiliated with Cricbuzz or any official cricket organization.
No real-time or official data API is used.

👨‍💻 Author

Alo-star
GitHub: Cricbuzz Clone Repo

📄 License

This project is licensed under the MIT License.
