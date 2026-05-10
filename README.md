# Varsity Bets 🃏

**A Full-Stack Betting Session & Bankroll Tracker**

Varsity Bets is a robust session logger designed to help users track gaming performance, manage bankrolls, and visualize win/loss trends across various categories.

---

## 🚀 Current Project Status: Phase 1 & 2 (Integration & Automation)

The project has successfully bridged the gap between the mobile client and the containerized backend, with a now-verified deployment pipeline.

### **Completed Milestones**

* **Identity & Onboarding:** Integrated **Firebase Authentication** with a custom "Registration Success" flow that handles user transition from account creation to login.
* **Secure API Synchronization:** Established a secure communication channel between Android and the .NET API using **Firebase JWT Bearer Tokens**.
* **Backend Orchestration:** Fully containerized the environment using **Docker Compose**, with the API managing many-to-one relationships between Users and Sessions.
* **Automated API Validation:** Implemented a comprehensive suite of **Newman (Postman CLI) tests** to ensure endpoint reliability, data integrity, and correct status code responses across the API.
* **Continuous Integration (CI):** Configured a **GitHub Actions Pipeline** that automatically triggers on pushes, handling builds and test execution to maintain high code quality and prevent regressions.
* **Live Dashboard Hub:** Developed a central hub using **Jetpack Compose** that fetches real-time profile data and calculates performance metrics (Total Profit & current Win Streaks).
* **Direct Session Initiation:** Implemented the "Start Session" interface, enabling the mobile client to post new session data directly to the API and persist it within the SQL Server database.

---

## 📅 Upcoming Phases (Roadmap)

### **Phase 2: Local Persistence & Refined Control**

* **RoomDB Integration:** Implementing local-first storage to cache profile data and support offline session logging.
* **Category Engine:** Adding support for pre-created default categories (Poker, Slots, Sports) and custom user-defined categories with icons.
* **Active Session Management:** Developing the UI logic to end, edit, and track the duration of active betting sessions in real-time.

### **Phase 3: Multimedia & Goal Tracking**

* **Photo Attachments:** Enabling users to attach screenshots of winning slips or strategy notes to specific sessions.
* **Bankroll Goals:** Implementing a tracking system where users set monthly limits and category-specific profit targets.
* **Advanced Analytics:** Visualizing session history with charts to provide deeper insights into win/loss fluctuations over time.

---

### **Tech Stack**

* **Frontend:** Kotlin, Jetpack Compose, Retrofit, RoomDB
* **Backend:** .NET Core API, Entity Framework
* **Database:** MS SQL Server, Firebase Auth
* **DevOps:** Docker, GitHub Actions, Newman/Postman
