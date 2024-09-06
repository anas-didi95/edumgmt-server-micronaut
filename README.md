# **edumgmt API**

### Education Management System API - OpenAPI 3.0

## 1. **Project Overview**
The **edumgmt** API is an education management system designed to manage students, attendance records, and users within an educational institution. The API is built using the OpenAPI v3 specification and supports JWT authentication.

- **Version**: 0.1.0
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

## 6. References
- [Micronaut References](./REFERENCES.md)

## 7. Contact
Developed by [Anas Juwaidi Bin Mohd Jeffry](mailto:anas.didi95@gmail.com)
