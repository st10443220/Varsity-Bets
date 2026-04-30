# Varsity Bets🃏

**A Full-Stack Betting Session & Bankroll Tracker**
Varsity Bets is a robust session logger designed to help users track gaming performance, manage bankrolls, and visualize win/loss trends across various categories.

---

## 🚀 Current Project Status: Phase 1 & 2 (Integration)
The project has successfully bridged the gap between the mobile client and the containerized backend.

### **Completed Milestones**
*   **Identity & Onboarding:** Integrated **Firebase Authentication** with a custom "Registration Success" flow that handles user transition from account creation to login.
*   **Secure API Synchronization:** Established a secure communication channel between Android and the .NET API using **Firebase JWT Bearer Tokens**.
*   **Backend Orchestration:** Fully containerized the environment using **Docker Compose**, with the API now successfully managing many-to-one relationships between Users and Sessions.
*   **Live Dashboard Hub:** Developed a central hub using **Jetpack Compose** that fetches real-time profile data and calculates performance metrics (Total Profit & current Win Streaks) on-the-fly.

---

## 📅 Upcoming Phases (Roadmap)

### **Phase 2: Local Persistence & Categorization**
*   **RoomDB Integration:** Implementing local-first storage to cache profile data and support offline session logging.
*   **Category Engine:** Adding support for pre-created default categories (Poker, Slots, Sports) and custom user-defined categories with icons.
*   **Session Management:** Developing the UI and logic to start, end, and edit betting sessions directly from the mobile app.

### **Phase 3: Multimedia & Goal Tracking**
*   **Photo Attachments:** Enabling users to attach screenshots of winning slips or strategy notes to specific sessions.
*   **Bankroll Goals:** Implementing a tracking system where users set monthly limits and category-specific profit targets.
*   **Advanced Analytics:** Visualizing session history with charts to provide deeper insights into win/loss fluctuations over time.
