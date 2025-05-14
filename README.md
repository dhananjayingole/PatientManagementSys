# 🏥 Patient Management System

A complete **Patient Management Android Application** built with **Kotlin + Jetpack Compose**, powered by **Firebase Realtime Database**, and designed with a **responsive UI**. This app is built for doctors and patients to manage healthcare operations efficiently and digitally.

---

## ✨ Features

### 👩‍⚕️ For Doctors
- **Add & Manage Patients**: Store patient details including:
  - Name
  - Age
  - Prescription
- **Realtime Database**: All data is stored and updated in **Firebase Realtime Database** instantly.

### 👨‍⚕️ For Patients
- **Book Appointments** with doctors directly through the app.
- **Receive Notifications** from the Admin regarding health updates or other info.
- **Live Location Sharing** during emergency ambulance requests using **device GPS**.

---

## 🔐 Authentication
- Uses **Firebase Authentication** to allow secure login and registration for both doctors and patients.

---

## 🔔 Admin Notification System
- Only the **Admin** can send messages.
- Messages appear in a **Notification Section** visible to **all users** (patients).

---

## 🌐 External API Integrations
- **Yoga & Exercise Data**: Fetches useful health tips, yoga poses, and exercise routines using an API.

---

## 📍 Emergency Location Feature
- Accesses **current location (latitude & longitude)** via GPS.
- Enables **emergency ambulance call** support by sharing location data in real-time.

---

## 🧩 Tech Stack

| Tech | Description |
|------|-------------|
| Kotlin + Jetpack Compose | Modern Android UI framework |
| Firebase Authentication | Secure login/logout |
| Firebase Realtime Database | Store and sync patient data |
| REST API | For Yoga & Exercise content |
| Location API | Fetch current location for emergencies |

---

## 🛠️ Getting Started

1. Clone the repository:
   ```bash
   https://github.com/dhananjayingole/PatientManagementSys.git
