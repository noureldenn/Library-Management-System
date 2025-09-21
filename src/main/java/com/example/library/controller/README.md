# Library Management System

A Spring Boot application for managing a library, including books, authors, categories, publishers, members, borrow/return records, and user roles with authentication.

---

## Features

- Manage books with details: title, language, publish year, ISBN, edition, summary, cover image, publisher, categories, authors.
- Manage members and borrow/return books.
- User roles: ADMIN, LIBRARIAN, STAFF.
- Basic authentication with Spring Security.
- Logging of actions (create/update/delete) for users.
- Search books by title.
- Pagination support for book listing.

---

## Technologies Used

- Java 19
- Spring Boot 3.x
- Spring Data JPA / Hibernate
- Spring Security
- H2 / MySQL (or any relational database)
- Maven
- RESTful APIs
- Postman for testing

---

## Getting Started

1. Clone the repository:

```bash
git clone https://github.com/noureldenn/Library-Management-System.git
cd Library-Management-System


## Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


erDiagram
BOOKS ||--o{ AUTHORS : "has"
BOOKS ||--o{ CATEGORIES : "belongs to"
BOOKS }o--|| PUBLISHERS : "published byBORROW_RECORDS }o--|| BOOKS : "borrowed book"
 BORROW_RECORDS }o--|| MEMBERS : "borrowed by"
   USERS ||--o{ ROLES : "has"

##link postman collection
https://web.postman.co/workspace/My-Workspace~f13fbb73-617c-43d4-86fb-f8707615bd10/collection/28582846-c3111368-8464-4d24-8c21-642f074c0035?action=share&source=copy-link&creator=28582846