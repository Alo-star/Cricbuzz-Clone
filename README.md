Cricbuzz Clone (Android)














A Cricbuzz-inspired Android application UI/UX clone built using Java, XML, MVVM architecture, Retrofit, and Room DB.
This project focuses on replicating a real-world cricket app experience with clean UI, modular architecture, and offline support.

📱 About the Project

This is a learning-based Cricbuzz clone app that replicates the UI/UX of a cricket score application.

🎯 Goals:
Practice Android development
Learn MVVM architecture
Implement offline caching
Build real-world UI screens
✨ Features
🏠 Home screen with match cards
🏏 Live & Upcoming matches UI
📊 Match details & scorecard screen
📰 Cricket news feed layout
👥 Team & player profiles
📅 Series & tournaments listing
⭐ Favorites system (Room DB)
🔍 Match details with commentary UI
📱 Bottom navigation + drawer menu
💾 Offline caching support
🔄 Pull-to-refresh & auto update simulation
⚡ Smooth UI animations
🛠️ Tech Stack
Layer	Technology
Language	Java
UI	XML (Material Design)
Architecture	MVVM
Networking	Retrofit + OkHttp
Database	Room + SQLite
Async	LiveData + ViewModel
IDE	Android Studio
🧠 Architecture Overview

This project follows MVVM (Model–View–ViewModel) architecture:

View (UI Layer)
Activities & Fragments handle UI rendering only
ViewModel
Holds UI-related data and logic
Repository
Single source of truth (API + Local DB)
Data Sources
Remote: Retrofit API
Local: Room Database
📸 Screenshots

Add your app screenshots here

Home	Live Matches	Match Details
📷	📷	📷
Teams	News	Player Profile
📷	📷	📷
🚀 Getting Started
1. Clone the repository
git clone https://github.com/Alo-star/Cricbuzz-Clone.git
2. Open in Android Studio
File → Open → Select project folder
3. Sync Gradle
Wait for dependencies to download
4. Run the app
Click ▶ Run or press Shift + F10
📁 Project Structure
app/
 ├── ui/
 │   ├── activities/
 │   ├── fragments/
 │   └── adapters/
 │
 ├── viewmodel/
 ├── repository/
 │
 ├── data/
 │   ├── local/   (Room DB)
 │   └── remote/  (Retrofit API)
 │
 ├── model/
 ├── utils/
 └── App.java
⚙️ Key Modules
📡 Networking
Retrofit API integration
OkHttp interceptor for API key
Centralized API service layer
💾 Local Database
Room database for caching
Offline match data support
Favorites storage
🔄 Performance
Live updates using ViewModel
Auto refresh simulation
Smooth RecyclerView animations
⚠️ Disclaimer

This project is created for educational purposes only.
It is not affiliated with Cricbuzz or any official cricket organization.
No real-time official data is used.

👨‍💻 Author

Alo-star
GitHub: https://github.com/Alo-star

📄 License

This project is licensed under the MIT License.

