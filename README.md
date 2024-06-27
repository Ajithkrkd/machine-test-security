
# Authentication And Authorization with Audit Log , Spring Security ,Custom TaskExcecutionTime Annotation

## Overview
This project provides a collection of REST API endpoints for authentication and authorization using Spring Security with JWT tokens. Additionally, it incorporates auditing capabilities using Aspect-Oriented Programming (AOP).


## Table of Contents
- [Project Setup](#project-setup)
  - [Prerequisites](#prerequisites)
  - [Installation and Configuration](#installation-and-configuration)
  - [Running the Application](#running-the-application)
  - [Accessing the API](#accessing-the-api)
  - [Additional Notes](#additional-notes)
  - [Custome annotation](#custom-annotation)


## Project Setup

### Prerequisites
Make sure you have the following installed:
- Java Development Kit (JDK) version "20.0.1" higher


### Installation and Configuration
1. Clone the repository:
   ```bash
   git clone https://github.com/Ajithkrkd/machine-test-security.git
   ```
2. Open project in any IDE (intelij) and change DB settings

   ```bash
    spring:
        datasource:
            url: jdbc:postgresql://localhost:5432/your_DB
            username: your_USERNAME
            password: your_PASSWORD
            driver-class-name: org.postgresql.Driver
        jpa:
            hibernate:
            ddl-auto: 'create-drop'
   ```
3. Before running the application, update the JWT expiration time and signing key in the application.properties file according to your requirements:
```
application.security.jwt.secret-key="YOUR_SECRETKEY"
application.security.jwt.expiration="YOUR_EXPIRATION_TIME"
```
### Running the Application

1. After the application is up and running, you can view the Swagger API documentation at the following endpoint.
```
http://localhost:8080/swagger-ui/index.html#/
```

### Jwt structure 
![image](https://github.com/Ajithkrkd/Pedal_Planet_Ecommerce/assets/131735228/5b105bed-8f63-4b19-bfcb-849039b8f720)


### Accessing the API

1. for register and login:
```
baseurl = http://localhost:8080/api/v1/auth/register
baseurl = http://localhost:8080/api/v1/auth/login
```

## All the APIs below require an access_token.

1. for user endpoints the base url is :
```
baseurl = http://localhost:8080/api/v1/users/**

```
2. for admin endpoints the base url is :
```
baseurl = http://localhost:8080/api/v1/admin/**

```
## user endpoints
#### change password

```http
  POST /api/v1/users/change_password
```

#### update details

```http
  POST /api/v1/users/update
```
#### Get details

```http
  GET /api/v1/users/details
```

## Address endpoints
#### Adding and updating address we can use this api

```http
  POST /api/v1/users/address/add
```
#### Get address 

```http
  GET /api/v1/users/address/get
```
#### Delete address 

```http
  DELETE /api/v1/users/address/delete
```

## Admin endpoints
#### Admin can view all users by this endpoint

```http
  GET /api/v1/admin/user/all
```
#### Admin can block and unblock user with this same endpoint by toggle

```http
  POST  /api/v1/admin/user/block/{id}
```
#### Admin can view each user by this endpoint

```http
  POST  /api/v1/admin/user/{id}
```

## Role endpoints
#### Admin can create roles

```http
  POST /api/v1/admin/roles/create
```
#### Admin can delete roles

```http
  POST /api/v1/admin/roles/delete/{id}
```
#### Admin view all roles

```http
  GET /api/v1/admin/roles/getAll
```
#### Admin view  single role

```http
  GET /api/v1/admin/roles/{id}
```
#### Admin assing new role to a person 

```http
  GET /api/v1/admin/roles/assignRole/{userId}/{roleId}
```

## Audit endpoints
#### view all database opperation using this endpoint

```http
  GET /api/v1/admin/audit/getAllLog
```


# Additional Notes


1. You'll get a clear idea about the endpoint request-response via Swagger documentation once you run the application. If you have any queries, please feel free to contact me.

2. The `register` and `login` APIs do not require authentication. Once a user registers, their information will be saved to the database. Upon logging in, we will provide an `access_token` valid for one day. This token will grant access to all other endpoints.

3. I have used token extraction from the request to identify the user, which is why I do not request the user ID for certain endpoints. For example, consider the following endpoint:
```
 POST /api/v1/users/change_password
```
Here, I retrieve the user details from the token provided by the user who accesses this endpoint. This allows them to change their password based solely on their token.


## API response Examples
### user email already exist exception
![image](https://github.com/Ajithkrkd/Pedal_Planet_Ecommerce/assets/131735228/2b07fe84-a453-4be9-b191-c4c1117f1fcb)

### user success login

![image](https://github.com/Ajithkrkd/Pedal_Planet_Ecommerce/assets/131735228/f0f7d8cf-9aab-4f20-b329-bdbfe8d2f1d5)

## custom-annotation
### Custome annotation for checking the time to excecute a method
1. implemented a custom annotation which will calculate the time to excecute a method. we can simply annotate this annotation  to any method it will log the time taken to complete the method 
![image](https://github.com/Ajithkrkd/machine-test-security/assets/131735228/0f9a1199-d3ba-4c02-8723-398dd21706ac)

![image](https://github.com/Ajithkrkd/machine-test-security/assets/131735228/1453dc3e-2a87-4a63-89ae-0354aef6983d)
