# Learning Dashboard MVP

## 1. Architecture
I chose an **MVVM (Model-View-ViewModel) architecture with Clean Architecture principles**. 
- **Why:** It ensures a clear separation of concerns. The ViewModel manages UI state, the Repository handles data orchestration (Single Source of Truth), and the Data Layer deals with API and Room persistence. This makes the code modular, testable, and maintainable.

## 2. Offline Support
- **Persistence:** I am using **Room Database** as the local cache.
- **Loading:** The repository implements a "Single Source of Truth" strategy. It exposes a `Flow` from the Room database to the UI. When the app launches, the Repository performs a background sync to refresh the local cache if empty, ensuring the UI always displays the most recent data available, regardless of network connectivity.

## 3. Security
- In a production application, authentication tokens (e.g., JWTs) should **never be stored in plain text (SharedPreferences)**. I would use the **Android Keystore System** to encrypt and store sensitive tokens, accessed via the `EncryptedSharedPreferences` library, ensuring they are protected by hardware-backed security.

## 4. Scale (1M+ Users, Hundreds of Courses)
To scale this application, I would implement the following:
1. **Pagination:** Implement Room and API paging (`Paging 3` library) to load course lists incrementally rather than loading all items into memory at once.
2. **Advanced Caching Strategy:** Implement expiration policies (e.g., using `Room` with timestamps) to selectively invalidate and refresh cached data, rather than relying on a simple "is empty" check.
3. **Database Performance:** Optimize queries with proper indexing on frequently accessed columns (e.g., `courseId` in the `lessons` table).
4. **Backend Load Balancing & CDN:** Move away from hardcoded mock data to a robust backend infrastructure behind a CDN for faster content delivery and optimized API responses.
5. **Observability:** Integrate tools like Firebase Performance Monitoring and Crashlytics to track user experience bottlenecks and production crashes at scale.
