# 🏗️ Candle Management System - Project Structure

## 📁 Clean Project Organization

### Backend (Spring Boot)
```
ADP3_Capstone_CandleSystem/
├── src/
│   ├── main/
│   │   ├── java/ac/za/cput/
│   │   │   ├── controller/          # REST API Controllers
│   │   │   ├── domain/             # Entity Models
│   │   │   ├── service/            # Business Logic Layer
│   │   │   ├── repository/         # Data Access Layer
│   │   │   ├── factory/            # Factory Pattern Implementation
│   │   │   ├── config/             # Configuration Classes
│   │   │   └── util/               # Utility Classes
│   │   └── resources/
│   │       ├── application.properties
│   │       └── img/                # Static Images
│   └── test/
│       └── java/ac/za/cput/        # Unit Tests
├── pom.xml                         # Maven Configuration
├── README.md                       # Project Documentation
├── SECURITY_README.md             # Security Documentation
├── REFERENCES.md                   # Academic References
└── PROJECT_STRUCTURE.md           # This File
```

### Frontend (React & React Native)
```
Repsentationlayer-Candle-System/
├── mobile-app/                     # React Native Mobile App
│   ├── app/                       # Expo Router Pages
│   ├── components/                # Reusable Components
│   ├── context/                   # State Management
│   ├── hooks/                     # Custom Hooks
│   ├── services/                  # API Services
│   └── assets/                    # Static Assets
├── web-app/                       # React Web Application
│   ├── src/
│   │   ├── components/            # Reusable Components
│   │   ├── pages/                # Page Components
│   │   ├── context/              # State Management
│   │   └── services/             # API Services
│   └── public/                     # Static Files
├── services/                      # Shared API Services
│   ├── config.js                 # Centralized Configuration
│   ├── *.Api.js                  # API Service Files
│   └── validation.js             # Input Validation
└── package.json                   # Monorepo Configuration
```

## 🧹 Cleanup Summary

### ✅ Removed Files
- **Temporary Documentation**: All `.txt` implementation files
- **Duplicate Documentation**: Removed duplicate `.md` files
- **Unused Components**: Removed test and demo components
- **Compiled Classes**: Cleaned `target/` directory
- **Duplicate Services**: Removed duplicate service files

### ✅ Cleaned Structure
- **Centralized Configuration**: Single `config.js` for all API services
- **Consistent Naming**: Standardized file and folder names
- **Removed Redundancy**: Eliminated duplicate code and files
- **Clean Dependencies**: Organized package.json files

## 🎯 Key Features Implemented

### Backend (Spring Boot)
- **RESTful API**: Complete CRUD operations
- **JWT Authentication**: Secure user authentication
- **Spring Security**: Role-based access control
- **JPA/Hibernate**: Database persistence
- **Email Service**: Order confirmation emails
- **CORS Configuration**: Cross-origin resource sharing

### Frontend Web (React)
- **Responsive Design**: Mobile-first approach
- **Authentication**: Login/Register functionality
- **Product Management**: CRUD operations
- **Shopping Cart**: Real-time cart management
- **Order Tracking**: Complete order lifecycle
- **Admin Dashboard**: User and order management

### Frontend Mobile (React Native)
- **Cross-Platform**: iOS and Android support
- **Expo Router**: Navigation system
- **Context API**: State management
- **API Integration**: Full backend connectivity
- **Offline Support**: Local data persistence

## 📚 Academic References

### Software Engineering Principles
- **Clean Code**: Robert C. Martin's principles
- **SOLID Principles**: Object-oriented design
- **Design Patterns**: Factory, Repository, Service Layer
- **Architecture**: Clean Architecture principles

### Technologies Used
- **Java 11+**: Backend development
- **Spring Boot**: Framework and ecosystem
- **React 18+**: Frontend web development
- **React Native**: Mobile development
- **MySQL**: Database management
- **JWT**: Authentication and authorization

## 🔧 Development Setup

### Prerequisites
- Java 11 or higher
- Node.js 16 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

### Quick Start
1. **Backend**: `mvn spring-boot:run`
2. **Web App**: `yarn run web`
3. **Mobile App**: `yarn run mobile`

## 📊 Project Statistics

### Code Quality
- **Clean Architecture**: Layered separation of concerns
- **SOLID Principles**: Maintainable and extensible code
- **Design Patterns**: Factory, Repository, Service Layer
- **Security**: JWT authentication and authorization
- **Testing**: Unit tests for critical components

### Technology Stack
- **Backend**: Java, Spring Boot, Spring Security, JPA, MySQL
- **Frontend Web**: React, React Router, Axios, CSS3
- **Frontend Mobile**: React Native, Expo, React Navigation
- **Database**: MySQL with JPA/Hibernate ORM
- **Authentication**: JWT-based security

---

*This project demonstrates professional software development practices with clean code, proper architecture, and comprehensive documentation suitable for academic review.*
