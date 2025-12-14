# Backend Setup Guide

## Overview
The Spring Boot backend has been set up to connect with the Angular frontend and store data in MySQL database.

## Database Configuration

### 1. Install MySQL
Make sure MySQL is installed and running on your system.

### 2. Update Database Credentials
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### 3. Create Database
The application will automatically create the database `expense_tracker` if it doesn't exist (due to `createDatabaseIfNotExist=true`).

## Running the Backend

### Prerequisites
- Java 17 or higher
- Maven
- MySQL Server

### Steps
1. Navigate to the backend directory:
   ```bash
   cd Backend/EXPENCE-TRACKER
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The backend will start on `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login user

### Users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `PUT /api/users/{id}/profile` - Update user profile
- `GET /api/users/family/{familyId}` - Get all users in a family

### Families
- `POST /api/families/create` - Create a new family
- `POST /api/families/{familyId}/members` - Add family member
- `DELETE /api/families/{familyId}/members/{memberId}` - Remove family member
- `GET /api/families/{familyId}` - Get family by ID
- `GET /api/families/head/{headId}` - Get family by head ID
- `GET /api/families/{familyId}/members` - Get all family members

### Expenses
- `GET /api/expenses/user/{userId}` - Get expenses by user
- `GET /api/expenses/family/{familyId}` - Get all family expenses
- `GET /api/expenses/user/{userId}/date-range` - Get expenses by date range
- `GET /api/expenses/user/{userId}/category/{category}` - Get expenses by category
- `POST /api/expenses/user/{userId}` - Add expense
- `PUT /api/expenses/{expenseId}` - Update expense
- `DELETE /api/expenses/{expenseId}` - Delete expense
- `GET /api/expenses/{expenseId}` - Get expense by ID

### Budgets
- `GET /api/budgets/{familyId}/{year}/{month}` - Get budget for month
- `POST /api/budgets/{familyId}/{year}/{month}` - Set budget for month
- `POST /api/budgets/{familyId}/{year}/{month}/member/{userId}` - Set member budget
- `GET /api/budgets/{familyId}/{year}/{month}/member/{userId}` - Get member budget

## Frontend Integration

The frontend services have been updated to use HTTP calls instead of localStorage:
- `ApiService` - Central HTTP client for all API calls
- `AuthService` - Updated to use backend authentication
- `ExpenseService` - Updated to use backend expense APIs
- `FamilyService` - Updated to use backend family APIs
- `BudgetService` - Updated to use backend budget APIs

## CORS Configuration

CORS is configured to allow requests from `http://localhost:4200` (Angular default port).

## Security

- Passwords are hashed using BCrypt
- Spring Security is configured (currently permissive for development)
- CSRF is disabled for API endpoints

## Database Schema

The following tables will be created automatically:
- `users` - User accounts
- `families` - Family groups
- `family_members` - Family member relationships
- `expenses` - Expense records
- `budgets` - Monthly budgets
- `member_budgets` - Individual member budget allocations

## Testing

1. Start the backend server
2. Start the Angular frontend (`ng serve`)
3. Register a new user
4. Create a family
5. Add expenses and budgets

## Troubleshooting

### Database Connection Issues
- Verify MySQL is running
- Check database credentials in `application.properties`
- Ensure MySQL allows connections from localhost

### CORS Issues
- Verify frontend is running on `http://localhost:4200`
- Check CORS configuration in `SecurityConfig.java`

### Port Conflicts
- Backend default port: 8080
- Change in `application.properties` if needed: `server.port=8080`





