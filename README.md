# Tạp Hóa IT - Technology Equipment E-commerce Website

Welcome to our **Tạp Hóa IT** project, this is the first our's project about Web Programming! This repository contains the source code for our web programming project, developed collaboratively by [Thanh Phong](https://github.com/tphong0903) and [Quang Đăng](https://github.com/quangdang1412).

---

## 🚀 Project Overview

This project is a  built using **Spring Boot**, a robust framework for Java-based web development. The application focuses on:

- **Scalability:** Designed to handle increasing workloads.
- **User-Friendliness:** Provides an intuitive and responsive user interface.
- **Security:** Implements OAuth2 for secure authentication.

---

## 🛠️ Features

- **RESTful APIs:** Backend services for smooth client-server interaction.
- **Database Integration:** CRUD operations with [MySQL/PostgreSQL/etc.] using Spring Data JPA.
- **Dynamic Views:** Responsive and interactive pages powered by Thymeleaf.
- **Secure Login:** OAuth2 integration for authentication.
- **Scalable Architecture:** Configured for real-world deployment.

---

## 🛠️ Technologies Used

### Backend:
- **Spring Boot**  
- **Spring Security** (OAuth2, JWT)  
- **Spring Data JPA**  

### Frontend:
- **Thymeleaf**  
- **Bootstrap 5**  

### Payment Integration:
- **PayOS**  

---

## 🛠️ Installation and Setup

### Prerequisites
- **Java JDK 21** or higher
- **Maven** (or your preferred build tool)
- **MySQL** installed and running

### Steps to Run Locally

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-repo-name.git
   cd your-repo-name
2. **Configure the Database:**
  To set up the database for the application, follow these steps:
  Open the application.properties file located in the src/main/resources directory.
   ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/taphoait
    spring.datasource.username=your-database-username
    spring.datasource.password=your-database-password

3. **Add Required API Keys:**
  If you want to enable Firebase, email services, OAuth2, or PayOS for your application, make sure to add the following API keys to the application.properties file:
  3.1 Firebase

       app.firebase.file=your-file-key
       app.firebase.bucket=your-bucket
  3.2 Email Service
  
       spring.mail.from=your-email-from
       spring.mail.username=your-email-username
       spring.mail.password=your-email-key
  3.3 OAuth2

       oauth2.client-id=your-client-id
       oauth2.client-secret=your-client-secret
  3.4 PayOS
  
       payos.client-id=your-client-id
       payos.api-key=your-payos-api-key
       payos.checksum-key=your-checksum     
4. **Build and Run the Application:**
      ```bash
       mvn clean install
       mvn spring-boot:run
5. **Access the application:**

       Open your browser and navigate to http://localhost:8080.

📦 Folder Structure
src/
├── main/
│   ├── java/
│   │   └── com.webbanhang.webbanhang/
│   │       ├── Config/
│   │       ├── Controller/
│   │       ├── DAO/
│   │       ├── DTO/
│   │       ├── Exception/
│   │       ├── Mapper/
│   │       ├── Model/
│   │       ├── Repository/
│   │       ├── Service/
│   │       ├── Util/
│   │       └── WebbanhangApplication.java
│   └── resources/
│       ├── database/
│       ├── static/
│       ├── templates/
│       ├── application-mail.yml
│       ├── application-oauth2.yml
│       └── application-payOS.yml
└── test/

🤝 Contribution
This project was developed by:

Thanh Phong: Responsible for backend logic, database integration, and admin frontend.
Quang Đăng: Responsible for authorization, authentication, security logic, system integration, and customer frontend.
