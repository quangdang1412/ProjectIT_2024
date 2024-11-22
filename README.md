# Tạp Hóa IT - Technology Equipment E-commerce Website

Welcome to our **Tạp Hóa IT** project, this is the first our's project about Web Programming! This repository contains
the source code for our web programming project, developed collaboratively
by [Thanh Phong](https://github.com/tphong0903) and [Đăng Quang](https://github.com/quangdang1412).

---

## 🚀 Project Overview

This project is a built using **Spring Boot**, a framework for Java-based web development. The application
focuses on:

- **User-Friendliness:** The website features an intuitive, responsive user interface that is optimized for various
  devices, ensuring a smooth browsing experience.
- **Security:** The application implements OAuth2 for secure user authentication, along with JWT for access control to
  ensure that only authorized users can perform sensitive actions.
- **Product Management and Order Processing:** Tạp Hóa IT offers easy management of products, categories, promotions,
  and customer orders. Admins and sellers can update inventory and process orders efficiently.
- **Payment Integration:** The website integrates popular and secure payment methods such as PayOS, enabling customers
  to pay using various online options, including QR code scanning for seamless transactions.
- **Account Management and Order Tracking:** Customers can manage their accounts, update personal information, and view
  order histories.
  In addition, they can track their orders via email every time the order is updated to ensure transparency

---

## 🛠️ Features

- **RESTful APIs:** Backend services for smooth client-server interaction.
- **Database Integration:** CRUD operations with [MySQL] using Spring Data JPA.
- **User Authentication (OAuth2 & JWT):** Users can register and log in securely, including Google OAuth2 integration
  for easy login.
- **Product Search & Filtering:** Advanced search and filter functionality allow customers to find products by
  categories, brands, and specifications.
- **Product and Order Management:** Admins can manage the product catalog, including adding, updating, deleting
  products,can view, process, and update order statuses and Customers can place orders, manage their cart, and track
  their orders.
- **Payment Integration:** Supports secure payments via PayOS, offering methods like Cash on Delivery (COD) and QR code
  scanning for online payments.
- **Dynamic Views with Thymeleaf & Bootstrap:** The website uses Thymeleaf for dynamic content rendering and Bootstrap
  for responsive, mobile-friendly designs.
- **Customer Email Notifications:** Automatically sends email updates to customers for key events like order
  confirmation, shipping status, order delivery and reset password.
- **Image Storage with Firebase:** Product images are securely uploaded and stored using Firebase, enabling efficient
  image management and retrieval.

---

## 🛠️ Technologies Used

### Backend:

- **Spring Boot**
- **Spring Security** (OAuth2, JWT)
- **Spring Data JPA**
- **Firebase**
- **Java Mail**

### Frontend:

- **Thymeleaf**
- **Bootstrap 5**

### Payment Integration:

- **PayOS**

---

## 🛠️ Installation and Setup

### Prerequisites

- **Java JDK 21** or higher
- **Maven**
- **MySQL**

### Steps to Run Locally

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/quangdang1412/ProjectIT_2024.git
   cd ProjectIT_2024
2. **Configure the Database:**
   To set up the database for the application, follow these steps:
   Open the application.properties file located in the src/main/resources directory.
   ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/taphoait
    spring.datasource.username=your-database-username
    spring.datasource.password=your-database-password

3. **Add Required API Keys:**
   If you want to enable Firebase, email services, OAuth2, or PayOS for your application, make sure to add the following
   API keys to the application.properties file:

- **Firebase**

       app.firebase.file=your-file-key
       app.firebase.bucket=your-bucket

- **Email Service**

       spring.mail.from=your-email-from
       spring.mail.username=your-email-username
       spring.mail.password=your-email-key

- **OAuth2**

       oauth2.client-id=your-client-id
       oauth2.client-secret=your-client-secret

- **PayOS**

       payos.client-id=your-client-id
       payos.api-key=your-payos-api-key
       payos.checksum-key=your-checksum     

4. **Build and Run the Application:**
      ```bash
       mvn clean install
       mvn spring-boot:run
5. **Access the application:**

       Open your browser and navigate to http://localhost:8080.

---

## 📦 Folder Structure

      src/
      ├── main/
      │   ├── java/
      │   │   └── com.webbanhang.webbanhang/
      │   │       ├── Config/
      │   │       ├── Controller/
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

---

## 🤝 Contribution

This project was developed by:

- **Thanh Phong**: Responsible for backend logic, mail service, firebase service and employee frontend.

- **Đăng Quang**: Responsible for frontend logic, security logic, payment service, crawl data and customer
  frontend.

---


