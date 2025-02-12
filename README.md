# **Spring Boot CRUD with JDBC Template and Redis: A Scheduled Cache-First Approach**

## **📌 Project Overview**
This project implements a **CRUD (Create, Read, Update, Delete) API** using **Spring Boot**, where:
- **Redis** is used as the primary data store for fast access.
- **MySQL** serves as the persistent database.
- A **scheduler updates MySQL every hour** and reloads Redis when restarted.

## **🛠️ Technologies Used**
- **Spring Boot** - Backend framework
- **Spring Data JDBC** - Database access
- **MySQL** - Persistent storage
- **Redis** - In-memory cache for fast lookups
- **Spring Scheduler** - Periodic database updates
- **Docker (Optional)** - Running Redis and MySQL locally

## **🚀 Features**
- **CRUD operations** on user data
- **Data is retrieved from Redis for faster access**
- **Database is updated every hour** using a scheduler
- **Automatic Redis reload** from MySQL when Redis restarts

## **🛠️ Installation & Setup**
### **1️⃣ Clone the Repository**
```bash
git clone https://github.com/grithwikreddy/Spring-Boot-CRUD-with-JDBC-Template-and-Redis-A-Scheduled-Cache-First-Approach.git
cd Spring-Boot-CRUD-with-JDBC-Template-and-Redis-A-Scheduled-Cache-First-Approach
```

### **2️⃣ Configure MySQL Database**
Create a MySQL database and update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.redis.host=localhost
spring.redis.port=6379
```

### **3️⃣ Run Redis (If Not Installed)**
You can start Redis using Docker:
```bash
docker run --name redis-container -p 6379:6379 -d redis
```

### **4️⃣ Build and Run the Application**
```bash
mvn clean install
mvn spring-boot:run
```

## **🛠️ API Endpoints**
| HTTP Method | Endpoint | Description |
|------------|---------|-------------|
| **GET** | `/employee/read` | Get all users from Redis |
| **GET** | `/employee/read/{id}` | Get a single user by ID |
| **POST** | `/employee/insert` | Add a new user |
| **PUT** | `/employee/update/{id}` | Update user details |
| **DELETE** | `/employee/delete/{id}` | Delete a user |

## **📌 How the System Works**
1. **On application startup**, Redis is populated with data from MySQL.
2. **All read operations** (`GET /employees/read`, `GET /employees/read/{id}`) fetch data from Redis.
3. **On create, update, or delete operations**, MySQL is updated, and Redis cache is cleared.
4. **Every 1 hour, MySQL data is reloaded into Redis** via the Spring Scheduler.
5. **If Redis is restarted, data is reloaded from MySQL**.

## **📌 Scheduler Setup**
![image](https://github.com/user-attachments/assets/3798c377-36b9-454d-b2d1-564c43845c6c)

The following scheduler runs **every 1 hour** to sync Redis with MySQL:
```java
@Scheduled(fixedRate = 3600000) // Runs every 1 hour
public void updateRedisFromDatabase() {
    System.out.println("Scheduler running: Updating Redis from MySQL...");
    userService.loadDataFromDatabase();
}
```

