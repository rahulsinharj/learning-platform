# Learning Platform

A comprehensive Learning Management System (LMS) built with Spring Boot, designed to facilitate interaction between teachers and learners. Teachers can create and manage courses, while learners can discover, subscribe to, and consume course content.

## 🚀 Features

### For Teachers
*   **Course Management**: Create and manage new courses.
*   **Content Creation**: Add modules (videos, notes, quizzes) to courses.
*   **Dashboard**: View all created courses and their status.

### For Learners
*   **Course Discovery**: Browse available courses on the platform.
*   **Subscription System**: Subscribe to courses of interest.
*   **Learning Dashboard**: Track subscribed courses and progress.
*   **Course Consumption**: Access course modules and content.

### General
*   **Authentication**: Secure login and registration system.
*   **Role-Based Access**: Distinct portals for Teachers and Learners.
*   **In-Memory Database**: Pre-configured H2 database for easy testing and development.

## 🛠️ Tech Stack

*   **Language**: Java 21
*   **Framework**: Spring Boot 3.4.0
*   **Build Tool**: Maven
*   **Template Engine**: Thymeleaf
*   **Database**: H2 Database (In-memory)
*   **Security**: Spring Security
*   **Utilities**: Lombok

## 📂 Project Structure

```
src/main/java/com/antigravity/learningplatform
├── config/         # Security and application configuration
├── controller/     # Web controllers (Auth, Learner, Teacher)
├── entity/         # JPA Entities (User, Course, Module, etc.)
├── repository/     # Spring Data JPA Repositories
└── service/        # Business logic services
```

## ⚙️ Getting Started

### Prerequisites
*   Java Development Kit (JDK) 21
*   Maven

### Installation & Running

1.  **Clone the repository**
    ```bash
    git clone <repository-url>
    cd learning-platform
    ```

2.  **Build the project**
    ```bash
    mvn clean install
    ```

3.  **Run the application**
    ```bash
    mvn spring-boot:run
    ```

4.  **Access the application**
    *   Open your browser and navigate to `http://localhost:8080`
    *   Login/Register to start using the platform.

### Database Console
The application uses an H2 in-memory database. You can access the console at:
*   **URL**: `http://localhost:8080/h2-console`
*   **JDBC URL**: `jdbc:h2:mem:testdb`
*   **Username**: `sa`
*   **Password**: `password`

## 🔌 API Endpoints & Routes

### Authentication
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/login` | Login page |
| GET | `/register` | Registration page |
| POST | `/register` | Register new user |

### Learner Portal
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/learner/dashboard` | Learner's main dashboard |
| GET | `/learner/courses` | List of available courses to subscribe |
| POST | `/learner/course/{id}/subscribe` | Subscribe to a specific course |
| GET | `/learner/course/{id}` | View course content (if subscribed) |

### Teacher Portal
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/teacher/dashboard` | Teacher's main dashboard |
| GET | `/teacher/course/create` | Form to create a new course |
| POST | `/teacher/course/create` | Submit new course details |
| GET | `/teacher/course/{id}/module/add` | Form to add a module to a course |
| POST | `/teacher/course/{id}/module/add` | Submit new module details |

## 🗄️ Database Schema

*   **User**: Stores user credentials and roles (LEARNER/TEACHER).
*   **Course**: Contains course details, linked to an Instructor (User).
*   **Module**: Content units (videos, notes) associated with a Course.
*   **Subscription**: Links a Learner (User) to a Course, tracking progress.
