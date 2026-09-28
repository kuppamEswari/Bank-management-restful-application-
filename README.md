# Bank Management RESTful Application

A backend banking system built with **Spring Boot**, **Spring Data JPA**, **Hibernate**, and **PostgreSQL**. The application exposes RESTful APIs to handle core banking operations such as account lifecycle management, deposits, withdrawals, and secure fund transfers with complete data integrity and transactional consistency[cite: 1, 2].

---

## 🚀 Key Features

* **Account & Customer Management:** Full CRUD operations for managing customer accounts and records[cite: 1, 2].
* **Core Banking Transactions:** ACID-compliant handling for deposits, withdrawals, and inter-account fund transfers[cite: 1, 2].
* **Data Integrity & Validation:** Enforces unique constraints and business validation rules with custom global exception handling (`@RestControllerAdvice`)[cite: 1, 2].
* **Pagination & Sorting:** Server-side pagination and dynamic sorting across 30+ endpoints for optimized query performance[cite: 1, 2].
* **Relational Persistence:** Efficient entity relationships mapped via Hibernate and Spring Data JPA over a PostgreSQL database[cite: 1, 2].

---

## 🛠️ Tech Stack

* **Language:** Java (JDK 17+)[cite: 1, 2]
* **Framework:** Spring Boot[cite: 1, 2]
* **ORM & Data Access:** Spring Data JPA, Hibernate[cite: 1, 2]
* **Database:** PostgreSQL[cite: 1, 2]
* **API Architecture:** RESTful APIs[cite: 1, 2]
* **Testing & Documentation:** Postman[cite: 1, 2]
* **Build Tool:** Maven

---

## 📋 API Endpoints Overview

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/accounts` | Create a new customer bank account |
| `GET` | `/api/v1/accounts` | Retrieve accounts (Supports pagination & sorting) |
| `GET` | `/api/v1/accounts/{id}` | Get account details by ID |
| `POST` | `/api/v1/accounts/{id}/deposit` | Deposit funds into an account |
| `POST` | `/api/v1/accounts/{id}/withdraw` | Withdraw funds from an account |
| `POST` | `/api/v1/transfers` | Transfer funds between two accounts |
| `DELETE` | `/api/v1/accounts/{id}` | Close/delete an account |

---

## ⚙️ Getting Started

### Prerequisites
* Java JDK 17 or later installed
* PostgreSQL installed and running
* Maven installed

### Installation & Run

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/](https://github.com/)<your-username>/bank-management-rest-api.git
   cd bank-management-rest-api
