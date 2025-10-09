# Spring Security Implementation for Candle System

## Overview
This implementation provides basic application security using Spring Security with form-based authentication. It uses your existing entities (Admin, Driver, RetailStore) without creating new classes.

## Security Features

### 1. Authentication
- **Form-based login** at `/login`
- **Custom UserDetailsService** that authenticates users from Admin, Driver, and RetailStore tables
- **Password encoding** using BCrypt
- **Session management** with logout functionality

### 2. Authorization
- **Role-based access control** with three roles:
  - `ROLE_ADMIN` - Full system access
  - `ROLE_DRIVER` - Driver-specific operations
  - `ROLE_RETAIL_STORE` - Retail store operations

### 3. Protected Endpoints
- `/admin/**` - Admin only
- `/driver/**` - Admin and Driver access
- `/store/**` - Admin and Retail Store access
- `/api/**` - Any authenticated user

## User Roles and Access

### Admin Role
- Can access all endpoints
- Can manage all entities
- Full system control

### Driver Role
- Can access driver-related endpoints
- Can view and update driver information
- Cannot delete drivers (Admin only)

### Retail Store Role
- Can access retail store endpoints
- Can manage store information
- Cannot access admin-only features

## How to Use

### 1. Login
Navigate to `/login` and use credentials from your existing entities:
- **Admin**: Use username from Admin table
- **Driver**: Use username from Driver table  
- **Retail Store**: Use storeEmail from RetailStore table

### 2. Dashboard
After successful login, users are redirected to `/dashboard` which shows:
- User information
- Role badge
- Available features based on role

### 3. Password Management
Use the `PasswordUtil` class to encode passwords:
```java
String encodedPassword = PasswordUtil.encodePassword("rawPassword");
```

## Security Configuration

The security is configured in `SecurityConfig.java` with:
- Form-based authentication
- Role-based authorization
- Method-level security annotations
- Custom user details service

## Testing Security

Use the test endpoints to verify security:
- `/api/security/user-info` - Shows current user info
- `/api/security/admin-only` - Admin only access
- `/api/security/driver-only` - Driver only access  
- `/api/security/retail-only` - Retail store only access

## Important Notes

1. **No new classes created** - Uses existing Admin, Driver, RetailStore entities
2. **Simple implementation** - Beginner-friendly security setup
3. **Role-based access** - Different permissions for different user types
4. **Form-based login** - Standard web login interface
5. **Session management** - Proper logout and session handling

## Security Annotations

Controllers use `@PreAuthorize` annotations:
```java
@PreAuthorize("hasRole('ADMIN')")
@PreAuthorize("hasRole('ADMIN') or hasRole('DRIVER')")
@PreAuthorize("hasRole('ADMIN') or hasRole('RETAIL_STORE')")
```

This provides method-level security without overcomplicating the implementation.
