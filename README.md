
# 🕯️ Candle Management System

<img width="850" height="413" alt="FinalUmLT3 drawio" src="https://github.com/user-attachments/assets/523e2fa5-96e2-45a8-88b5-f34289bb23e2" />

## 🚀 Quick Start

### Prerequisites
- Node.js >= 16.0.0
- Yarn >= 1.22.0
- Java 11+ (for Spring Boot backend)
- Maven 3.6+ (for backend dependencies)

### Installation

1. **Clone the repository:**
```bash
git clone <your-repo-url>
cd ADP3_Capstone_CandleSystem
```

2. **Install dependencies:**
```bash
# Install backend dependencies
mvn clean install

# For frontend projects, navigate to respective directories and run:
cd ../Repsentationlayer-Candle-System
yarn install
```

3. **Start the backend:**
```bash
# From ADP3_Capstone_CandleSystem directory
mvn spring-boot:run
```

4. **Start the frontend applications:**
```bash
# From Repsentationlayer-Candle-System directory
yarn run mobile    # Start mobile app
yarn run web       # Start web app
yarn run both      # Start both apps simultaneously
```

## 🏗️ Project Structure

```
ADP3_Capstone_CandleSystem/          # Spring Boot Backend
├── src/main/java/ac/za/cput/        # Java source code
│   ├── controller/                   # REST controllers
│   ├── domain/                       # Entity models
│   ├── service/                      # Business logic
│   ├── repository/                   # Data access layer
│   └── factory/                      # Factory patterns
└── src/main/resources/              # Configuration files

Repsentationlayer-Candle-System/     # Frontend Applications
├── mobile-app/                      # React Native mobile app
├── web-app/                         # React web application
└── services/                        # Shared API services
```



### Frontend (Yarn Commands)
```bash
# Root level commands
yarn install         # Install all dependencies
yarn run mobile      # Start mobile app
yarn run web         # Start web app
yarn run both        # Start both apps
yarn run build:all   # Build both apps
yarn run clean       # Clean node_modules
```

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
