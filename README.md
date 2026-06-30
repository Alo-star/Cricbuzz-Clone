


# 🏏 Cricbuzz Clone

A cricket app UI/UX clone built for Android, inspired by Cricbuzz. This project focuses on replicating the look and feel of the Cricbuzz interface using native Android technologies — no live data or APIs involved.

---

## 📱 About the Project

This is a static UI/UX clone of the Cricbuzz Android app. The goal was to practice Android development by building clean, responsive screens that mirror the Cricbuzz design language — including navigation, layouts, and data display using a local database.

---

## 🛠️ Tech Stack

| Technology | Usage |
|------------|-------|
| **Java** | Core application logic and Android development |
| **XML** | UI layouts and screen design |
| **SQLite** | Local database for storing static cricket data |

---

## ✨ Features

- Home screen with match card layouts
- Series and tournament listing screens
- Team and player profile screens
- Navigation drawer / bottom navigation bar
- Static scorecard UI
- News feed style layout
- Responsive design for different screen sizes

---

## 📸 Screenshots

> *(Add screenshots of your app here)*

---

## 🚀 Getting Started

### Prerequisites

- Android Studio (latest version recommended)
- Android SDK (minimum API level 21)
- Java Development Kit (JDK 8 or above)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Alo-star/Cricbuzz-Clone.git
   ```

2. Open the project in **Android Studio**

3. Let Gradle sync and download dependencies

4. Run the app on an emulator or physical device:
   - Click **Run > Run 'app'** or press `Shift + F10`

---

## 📁 Project Structure

```
Cricbuzz-Clone/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   │
│   │   │   ├── java/com/example/cricbuzzclone/
│   │   │   │   │
│   │   │   │   ├── activities/
│   │   │   │   │   ├── MainActivity.java
│   │   │   │   │   ├── HomeActivity.java
│   │   │   │   │   ├── MatchDetailActivity.java
│   │   │   │   │   ├── TeamProfileActivity.java
│   │   │   │   │   ├── PlayerProfileActivity.java
│   │   │   │   │   ├── SeriesActivity.java
│   │   │   │   │   └── NewsActivity.java
│   │   │   │   │
│   │   │   │   ├── adapters/
│   │   │   │   │   ├── MatchAdapter.java
│   │   │   │   │   ├── TeamAdapter.java
│   │   │   │   │   ├── PlayerAdapter.java
│   │   │   │   │   └── NewsAdapter.java
│   │   │   │   │
│   │   │   │   ├── models/
│   │   │   │   │   ├── Match.java
│   │   │   │   │   ├── Team.java
│   │   │   │   │   ├── Player.java
│   │   │   │   │   └── News.java
│   │   │   │   │
│   │   │   │   └── database/
│   │   │   │       ├── DBHelper.java
│   │   │   │       └── DBConstants.java
│   │   │   │
│   │   │   ├── res/
│   │   │   │   │
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── activity_home.xml
│   │   │   │   │   ├── activity_match_detail.xml
│   │   │   │   │   ├── activity_team_profile.xml
│   │   │   │   │   ├── activity_player_profile.xml
│   │   │   │   │   ├── activity_series.xml
│   │   │   │   │   ├── activity_news.xml
│   │   │   │   │   ├── item_match_card.xml
│   │   │   │   │   ├── item_team.xml
│   │   │   │   │   ├── item_player.xml
│   │   │   │   │   └── item_news.xml
│   │   │   │   │
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── ic_launcher.xml
│   │   │   │   │   ├── ic_cricket_ball.xml
│   │   │   │   │   ├── bg_match_card.xml
│   │   │   │   │   └── (team logos, player images)
│   │   │   │   │
│   │   │   │   ├── menu/
│   │   │   │   │   ├── bottom_nav_menu.xml
│   │   │   │   │   └── drawer_menu.xml
│   │   │   │   │
│   │   │   │   └── values/
│   │   │   │       ├── colors.xml
│   │   │   │       ├── strings.xml
│   │   │   │       ├── dimens.xml
│   │   │   │       └── themes.xml
│   │   │   │
│   │   │   └── AndroidManifest.xml
│   │   │
│   │   └── test/
│   │
│   └── build.gradle
│
├── README.md
└── build.gradle
```

---

## ⚠️ Disclaimer

This project is built **purely for learning and practice purposes**. It is not affiliated with or endorsed by Cricbuzz or its parent company. No live scores, real-time data, or official APIs are used.

---

## 👨‍💻 Author

**Alo-star**  
[GitHub Profile](https://github.com/Alo-star)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).



⚠️ Disclaimer

This project is built purely for learning and practice purposes. It is not affiliated with or endorsed by Cricbuzz or its parent company. No live scores, real-time data, or official APIs are used.


👨‍💻 Author

Alo-star

GitHub Profile


📄 License

This project is open source and available under the MIT License.

## Production Upgrade Notes

This project was upgraded to a production-style architecture:

- **MVVM**: `viewmodel/` holds `LiveMatchViewModel`, `UpcomingMatchViewModel`, `MatchDetailsViewModel`; Activities only render `Resource` states.
- **Networking**: Retrofit + OkHttp in `api/`, with the RapidAPI key injected via an interceptor (no key in any Activity).
- **Offline caching**: Room database in `data/local/` caches the last successful live/upcoming results so the app still shows data with no internet.
- **Auto-refresh**: Live matches poll every ~12s, match details/commentary every ~15s, both paused in `onPause()`.
- **Favorites**: Star icon on any match/team writes to Room (`favorite_teams`, `favorite_matches`); view them on the new Favorites tab.
- **Match details**: Tapping a live match opens `MatchDetailsActivity` with scorecard + ball-by-ball commentary.
- **Loading states**: Shimmer skeleton placeholders while first loading, SwipeRefreshLayout for pull-to-refresh, fade-in animations on RecyclerView items.
- **Error handling**: No internet, empty API response, and HTTP/parse failures are all handled distinctly, falling back to cached data where possible.
- **Security**: `RAPIDAPI_KEY` continues to be read from a git-ignored `local.properties` and exposed only via `BuildConfig`.
