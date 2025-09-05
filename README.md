# ChitChat Application

![Java](https://img.shields.io/badge/Java-11+-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7-green)
![MySQL](https://img.shields.io/badge/MySQL-Database-orange)

ChitChat is a lightweight **real-time chat application** built with **Spring Boot** and **MySQL**.  
Designed for learning and demonstrating secure backend development.

---

## Features
- Real-time messaging
- User authentication via environment variables
- MySQL database integration
- Easy configuration with `application.properties`

---

## Quick Start
```bash
git clone https://github.com/ritik-luck/chat-app.git
cd chat-app
export DB_PASSWORD=your_db_password
export ADMIN_PASSWORD=your_admin_password
mvn clean install
mvn spring-boot:run