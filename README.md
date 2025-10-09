
# 🕯️ Candle Management System

A comprehensive e-commerce system for candle inventory management, built with modern software engineering principles and clean architecture.

## 🎯 Project Overview

This project demonstrates a complete full-stack application with:
- **Backend**: Spring Boot REST API with JWT authentication
- **Frontend Web**: React responsive web application
- **Frontend Mobile**: React Native cross-platform mobile app
- **Database**: MySQL with JPA/Hibernate ORM

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher
- Node.js 16 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

### Installation

1. **Clone the repository:**
```bash
git clone <your-repo-url>
cd ADP3_Capstone_CandleSystem
```

2. **Install backend dependencies:**
```bash
mvn clean install
```

3. **Install frontend dependencies:**
```bash
cd ../Repsentationlayer-Candle-System
yarn install
```

4. **Start the applications:**
```bash
# Backend (from ADP3_Capstone_CandleSystem)
mvn spring-boot:run

# Frontend (from Repsentationlayer-Candle-System)
yarn run web       # Start web app
yarn run mobile    # Start mobile app
yarn run both      # Start both apps simultaneously
```

## 🏗️ Project Structure

### Backend (Spring Boot)
```
ADP3_Capstone_CandleSystem/
├── src/main/java/ac/za/cput/
│   ├── controller/          # REST API Controllers
│   ├── domain/             # Entity Models
│   ├── service/            # Business Logic Layer
│   ├── repository/         # Data Access Layer
│   ├── factory/            # Factory Pattern Implementation
│   ├── config/             # Configuration Classes
│   └── util/               # Utility Classes
├── src/main/resources/      # Configuration Files
├── src/test/               # Unit Tests
├── pom.xml                 # Maven Configuration
├── README.md               # Project Documentation
├── SECURITY_README.md     # Security Documentation
├── REFERENCES.md           # Academic References
└── PROJECT_STRUCTURE.md   # Project Structure Guide
```

### Frontend (React & React Native)
```
Repsentationlayer-Candle-System/
├── mobile-app/             # React Native Mobile App
├── web-app/                # React Web Application
├── services/               # Shared API Services
└── package.json            # Monorepo Configuration
```

## 🎓 Academic References

This project implements software engineering principles from:
- **Clean Code**: Robert C. Martin's principles
- **SOLID Principles**: Object-oriented design
- **Design Patterns**: Factory, Repository, Service Layer
- **Spring Framework**: Official Spring documentation
- **React Documentation**: Official React and React Native guides

See [REFERENCES.md](REFERENCES.md) for complete academic references.

## 🔧 Technology Stack

### Backend
- **Java 11+**: Programming language
- **Spring Boot**: Application framework
- **Spring Security**: Authentication and authorization
- **JPA/Hibernate**: Object-relational mapping
- **MySQL**: Database management
- **JWT**: Token-based authentication

### Frontend
- **React 18+**: Web application framework
- **React Native**: Mobile application framework
- **Expo**: React Native development platform
- **Axios**: HTTP client library
- **React Router**: Web routing
- **Context API**: State management

## 📚 Documentation

- [Project Structure](PROJECT_STRUCTURE.md) - Detailed project organization
- [References](REFERENCES.md) - Academic and technical references
- [Security Documentation](SECURITY_README.md) - Security implementation details

## 📱 Features

- **Product Management**: Complete CRUD operations for candle products
- **Order Processing**: Full order lifecycle management
- **User Authentication**: Secure login and registration
- **Inventory Tracking**: Real-time stock management
- **Payment Processing**: Multiple payment methods
- **Delivery Management**: Order tracking and status updates
- **Admin Dashboard**: Comprehensive management interface

## 🔌 API Endpoints

The backend provides RESTful APIs for:
- Product management (`/product/*`)
- Order processing (`/order/*`)
- User authentication (`/auth/*`)
- Payment handling (`/payment/*`)
- Delivery tracking (`/delivery/*`)
- Admin operations (`/admin/*`)

## 🚀 Development

1. **Backend Development**: Use your preferred Java IDE (IntelliJ IDEA Code)
2. **Frontend Development**: Use yarn for package management
3. **API Testing**: Use Postman or similar tools to test endpoints
4. **Database**: Configure your database connection in `application.properties`

## 📝 Notes

- This project uses **Yarn** as the package manager for all frontend dependencies
- The backend is built with Spring Boot and Maven
- Both mobile and web apps share the same API services
- All frontend commands should use `yarn` instead of `npm`
