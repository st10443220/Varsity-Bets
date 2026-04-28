# Varsity Bets (BetWise) 🃏
**A Full-Stack Betting Session & Bankroll Tracker**

Varsity Bets is a robust session logger designed to help users track gaming performance, manage bankrolls, and visualize win/loss trends across various categories.

## 🚀 Current Project Status: Phase 1 (Infrastructure)
The project is in active development with the core infrastructure currently being established.

### **Completed Milestones**
* **Backend Orchestration:** Fully containerized the development environment using **Docker Compose**, including the .NET Core API and a SQL Server instance.
* **Identity Management:** Integrated **Firebase Authentication** for secure user registration and login flows.
* **Mobile UI Foundation:** Developed the core Android client using **Kotlin** and **Jetpack Compose**.
* **Database Schema:** Finalized the relational design for Categories and Sessions (1:N Relationship).

---

## 🛠️ Tech Stack
* **Mobile:** Kotlin, Jetpack Compose, RoomDB (Local Persistence), Firebase Auth.
* **Backend:** .NET Core Web API, Entity Framework Core.
* **DevOps/Data:** Docker Compose, SQL Server.

---

## 📅 Upcoming Phases (Roadmap)

### **Phase 2: Data Persistence & Categorization**
* **UI Design:** Completing the rest of the mobile UI following the current theme and patterns used amongst the Login and Register pages. (TODO: Implement account created page before going back to login page)
* **RoomDB Integration:** Implementing local-first storage for "Game Categories" (Poker, Slots, etc.) and individual sessions.
* **Category Engine:** Adding support for pre-created default categories and custom user-defined categories with descriptions and images.
* **API Linking:** Connecting the mobile frontend to the Dockerized API for cloud synchronization.

### **Phase 3: Multimedia & Goal Tracking**
* **Photo Attachments:** Enabling users to attach screenshots, strategy notes, or leaderboard images to specific sessions.
* **Chip Goals:** Implementing a performance tracking system where users set monthly minimum/maximum limits and category-specific goals.
* **Analytics Dashboard:** Visualizing session data to provide insights into win/loss fluctuations.
