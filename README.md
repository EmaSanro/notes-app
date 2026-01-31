# Notes App – Fullstack Application

This is a fullstack notes application that allows creating, listing, editing, archiving and deleting notes.

The frontend is built with Angular and served using Nginx.  
The backend is developed with Spring Boot and connects to a MySQL database.  
The entire application can be started with a single command using Docker Compose.

---

## 🧱 Tech Stack

### Frontend
- Angular **21**
- TypeScript
- SCSS
- Node.js **20.x**
- npm **10.x**
- Nginx **1.25 (alpine)**

### Backend
- Java **21**
- Spring Boot **3.x**
- Maven **3.9.x**

### Database
- MySQL **8.0**

### Infrastructure
- Docker **24.x**
- Docker Compose **v2.x**
- Bash / Zsh (for the startup script)

---

## 🧰 Prerequisites

To run this application, you need the following tools installed on your system:

### Required
- **Docker Desktop**
  - Version: 24.x or newer
  - Includes:
    - Docker Engine
    - Docker Compose v2

### Optional (only for local development without Docker)
- Node.js **20.x**
- npm **10.x**
- Java JDK **21**
- Maven **3.9.x**
- PostgreSQL **17.6.1.063**

---

## 🚀 Running the Application

The application can be started with a single command.

### 1️⃣ Give execution permissions to the script (Linux / macOS)

```
chmod +x run.sh
```

### 🔗 Visit the website
The application is hosted on Render, both its frontend and backend, and the database is hosted by Supabase.

[**Try it!**](https://notes-app-front-k10q.onrender.com/)
