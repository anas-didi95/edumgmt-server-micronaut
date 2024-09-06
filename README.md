# **edumgmt API**

### Education Management System API - OpenAPI 3.0

## 1. **Project Overview**
The **edumgmt** API is an education management system designed to manage students, attendance records, and users within an educational institution. The API is built using the OpenAPI v3 specification and supports JWT authentication.

- **Version**: v1.0.0
- **Contact**:
  - **Name**: Anas Juwaidi Bin Mohd Jeffry
  - **Email**: anas.didi95@gmail.com

## 2. **API Specification**
- **OpenAPI Version**: 3.0.1
- **Base URL**: `/edumgmt`

### Available Tags
- **attendance**: Attendance record management
- **auth**: User authentication and key management
- **student**: Student record management
- **user**: User record management

### **Authentication**
- The API uses JWT tokens for authentication.
- Security scheme: `Bearer` tokens (JWT)

## 3. **API Endpoints**

### **Attendance**
- **GET /edumgmt/attendance**: Search attendance records
- **POST /edumgmt/attendance**: Add new attendance record
- **GET /edumgmt/attendance/{attendanceId}**: Search attendance student record by attendance ID
- **POST /edumgmt/attendance/{attendanceId}**: Add a new attendance student record

### **Authentication**
- **POST /edumgmt/login**: Log in to the system
- **POST /edumgmt/logout**: Log out of the system

### **Student**
- **GET /edumgmt/student**: Search student records
- **POST /edumgmt/student**: Add a new student record
- **GET /edumgmt/student/{id}**: Retrieve a specific student record
- **PUT /edumgmt/student/{id}**: Update an existing student record
- **DELETE /edumgmt/student/{id}**: Delete a student record

### **User**
- **GET /edumgmt/user**: Search user records
- **POST /edumgmt/user**: Add a new user record
- **POST /edumgmt/user/{id}**: Update an existing user record
- **DELETE /edumgmt/user/{id}**: Delete a user record

## 4. **Installation**
1. Clone the repository:
    ```bash
    git clone https://github.com/anas-didi95/edumgmt-server-micronaut.git
    cd edumgmt-server-micronaut
    ```

2. Install dependencies:
    ```bash
    chmod +x gradlew
    ./gradlew build
    ```

3. Start the server:
    ```bash
    ./gradlew run
    ```

## 5. **Usage**
Once the server is running, access the API at `http://localhost:8080/edumgmt`.
- You can access the API documentation by opening [Swagger UI](http://localhost:8080/edumgmt/swagger-ui/index.html) in your browser.

## 6. **Environment Variables**

The **edumgmt** API requires the following environment variables to be set for proper configuration:

### **Basic Authentication**
- `BASIC_AUTH_USERNAME`: The username used for basic authentication. Default: `anas`.
- `BASIC_AUTH_PASSWORD`: The password used for basic authentication. Default: `password`.

### **Database Configuration**
- `DB_URL`: The JDBC URL for the database connection. Default: `jdbc:h2:./.h2/edumgmt;AUTO_SERVER=TRUE;AUTO_SERVER_PORT=9090`.
- `DB_USERNAME`: The database username. Default: `sa`.
- `DB_PASSWORD`: The database password. Default: `sa`.

### **JWT Authentication**
- `JWT_ACCESS_TOKEN_SECRET`: The secret key used to sign JWT access tokens. **Important: Change this to a secure, random value in production.**
- `JWT_REFRESH_TOKEN_SECRET`: The secret key used to sign JWT refresh tokens. **Important: Change this to a secure, random value in production.**

### **Micronaut Environment**
- `MICRONAUT_ENVIRONMENTS`: Defines the active environments in the Micronaut application. Default: `dev`.

### **Super Admin Passwords**
- `SUPER_ADMIN_PASSWORD_1`: The first part of the super admin password. Default: `pre`.
- `SUPER_ADMIN_PASSWORD_2`: The second part of the super admin password. Default: `post`.

Ensure these environment variables are set before starting the server to configure the application correctly.

## 7. **References**
- [Micronaut References](./REFERENCES.md)

## 8. **Contact**
Developed by [Anas Juwaidi Bin Mohd Jeffry](mailto:anas.didi95@gmail.com)
