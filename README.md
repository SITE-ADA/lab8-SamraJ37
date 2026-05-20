# WM2 Lab 8 – University Management System

## Overview

This project is a microservice-based University Management System developed for **WM2 Lab 8**.

The system contains two main services:

- **student-service** – manages students and provides student search functionality.
- **course-service** – manages courses, enrollments, prerequisite validation, and course retrieval by student name.

The project extends the original university system with the following features:

- Store enrollment date when a student enrolls in a course
- Add optional prerequisite course support
- Validate prerequisite completion before enrollment
- Retrieve courses by student name
- Provide Swagger/OpenAPI documentation in Azerbaijani
- Handle errors with meaningful API responses

---

## Technologies Used

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- OpenFeign
- RestTemplate
- Swagger / OpenAPI
- Gradle
- Lombok

---

## Project Structure

```text
university-system/
├── student-service/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   └── resources/
│
├── course-service/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   ├── client/
│   └── resources/
│
├── build.gradle
├── settings.gradle
└── README.md
Services
student-service

The student-service is responsible for student-related operations.

Main features:

Create student
Get all students
Get student by ID
Update student
Delete student
Search students by first name or last name

Default port:

9090
course-service

The course-service is responsible for course and enrollment operations.

Main features:

Create course
Get all courses
Get course by ID
Update course
Delete course
Enroll student into course
Store enrollment date
Validate prerequisite course before enrollment
Get students enrolled in a course
Get courses by student name

Default port:

8081

The course-service communicates with student-service using:

OpenFeign for student validation and student search
RestTemplate for retrieving detailed student data
Database Setup

This project uses PostgreSQL.

Create two databases before running the services:

CREATE DATABASE "studentDB";
CREATE DATABASE "courseDB";

Database usage:

Service	Database
student-service	studentDB
course-service	courseDB
Application Configuration
student-service configuration

File:

student-service/src/main/resources/application.properties

Example configuration:

spring.application.name=student-service
server.port=9090

spring.datasource.url=jdbc:postgresql://localhost:5432/studentDB
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=passwordc

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
course-service configuration

File:

course-service/src/main/resources/application.properties

Example configuration:

spring.application.name=course-service
server.port=8081

spring.datasource.url=jdbc:postgresql://localhost:5432/courseDB
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=passwordc

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

student.service.base-url=${STUDENT_SERVICE_BASE_URL:http://localhost:9090}

If your local PostgreSQL password is different, update the spring.datasource.password value in both services.

How to Run the Project

Run the commands from the project root folder.

1. Start student-service

Windows PowerShell:

.\gradlew.bat :student-service:bootRun

Linux/macOS:

./gradlew :student-service:bootRun

The service will run on:

http://localhost:9090
2. Start course-service

Open a second terminal.

Windows PowerShell:

.\gradlew.bat :course-service:bootRun

Linux/macOS:

./gradlew :course-service:bootRun

The service will run on:

http://localhost:8081
Swagger URLs

Student Service Swagger:

http://localhost:9090/swagger-ui/index.html

Course Service Swagger:

http://localhost:8081/swagger-ui/index.html

Swagger endpoint descriptions and DTO field descriptions are written in Azerbaijani.

API Examples
1. Create Student

Endpoint:

POST /api/v1/students

Request body:

{
  "firstName": "Nicat",
  "lastName": "Aliyev",
  "email": "nicat.aliyev@example.com",
  "age": 20
}

Example response:

{
  "id": 1,
  "firstName": "Nicat",
  "lastName": "Aliyev",
  "email": "nicat.aliyev@example.com",
  "age": 20
}
2. Search Students by Name

Endpoint:

GET /api/v1/students/search?name=Nicat

Example response:

[
  {
    "id": 1,
    "firstName": "Nicat",
    "lastName": "Aliyev",
    "email": "nicat.aliyev@example.com",
    "age": 20
  }
]

The search works by first name or last name.

3. Create Course Without Prerequisite

Endpoint:

POST /api/v1/courses

Request body:

{
  "title": "Programming Basics",
  "code": "PB101",
  "credits": 6,
  "prerequisiteCourseId": null
}

Example response:

{
  "id": 1,
  "title": "Programming Basics",
  "code": "PB101",
  "credits": 6,
  "prerequisiteCourseId": null
}
4. Create Course With Prerequisite

Endpoint:

POST /api/v1/courses

Request body:

{
  "title": "Advanced Java",
  "code": "JAVA201",
  "credits": 6,
  "prerequisiteCourseId": 1
}

Example response:

{
  "id": 2,
  "title": "Advanced Java",
  "code": "JAVA201",
  "credits": 6,
  "prerequisiteCourseId": 1
}
5. Enroll Student into Course

Endpoint:

POST /api/v1/courses/{courseId}/students/{studentId}

Example request:

POST /api/v1/courses/1/students/1

Example response:

{
  "enrollmentId": 1,
  "courseId": 1,
  "studentId": 1,
  "enrollmentDate": "2026-05-20",
  "message": "Student enrolled successfully."
}

The enrollmentDate field is automatically generated when the enrollment is created.

6. Get Students Enrolled in a Course

Endpoint:

GET /api/v1/courses/{courseId}/students

Example:

GET /api/v1/courses/1/students

Example response:

{
  "courseId": 1,
  "courseTitle": "Programming Basics",
  "students": [
    {
      "id": 1,
      "firstName": "Nicat",
      "lastName": "Aliyev",
      "email": "nicat.aliyev@example.com",
      "age": 20
    }
  ]
}
7. Get Courses by Student Name

Endpoint:

GET /api/v1/courses/by-student-name?name=Nicat

Example response:

[
  {
    "id": 1,
    "title": "Programming Basics",
    "code": "PB101",
    "credits": 6,
    "prerequisiteCourseId": null
  },
  {
    "id": 2,
    "title": "Advanced Java",
    "code": "JAVA201",
    "credits": 6,
    "prerequisiteCourseId": 1
  }
]

If no matching student or enrollment exists, the response will be:

[]
Prerequisite Validation

Courses may optionally have a prerequisite course.

If a course has no prerequisite, the field value is:

"prerequisiteCourseId": null

If a course requires another course first, the field contains the required course ID:

"prerequisiteCourseId": 1

Before enrollment, the system checks whether the student is already enrolled in the prerequisite course.

If the prerequisite is not completed, the enrollment is rejected.

Example invalid request:

POST /api/v1/courses/2/students/1

Example error response:

{
  "timestamp": "2026-05-20T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Student with id 1 cannot enroll in course 2 because prerequisite course 1 has not been completed.",
  "path": "/api/v1/courses/2/students/1"
}

To complete the process correctly:

Enroll the student into the prerequisite course.
Then enroll the student into the advanced course.
Error Handling

The project includes meaningful error handling for common cases:

Course not found
Remote student not found
Duplicate enrollment
Missing prerequisite enrollment
Validation errors
Student-service communication errors

Example duplicate enrollment response:

{
  "timestamp": "2026-05-20T12:00:00",
  "status": 409,
  "error": "Conflict",
  "message": "Enrollment already exists for course id 1 and student id 1",
  "path": "/api/v1/courses/1/students/1"
}
Recommended Testing Flow
Start PostgreSQL.
Create studentDB and courseDB.
Start student-service.
Start course-service.
Open Student Swagger.
Create a student.
Open Course Swagger.
Create a basic course without prerequisite.
Create an advanced course with prerequisite.
Try to enroll the student into the advanced course before completing prerequisite.
Confirm that the system returns 400 Bad Request.
Enroll the student into the prerequisite course.
Enroll the student into the advanced course.
Test course retrieval by student name.
Important Notes
Both services must be running for enrollment and course search by student name.
course-service depends on student-service.
PostgreSQL databases must exist before running the application.
spring.jpa.hibernate.ddl-auto=update is used, so tables are updated automatically.
Swagger documentation is available for both services.
API endpoint and DTO descriptions are written in Azerbaijani.