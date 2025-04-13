# 💳 Bank Management System using JDBC and MySQL on Ubuntu

## 💡 Project Overview

This is a simple **Bank Management System** built using Java and **JDBC** to interact with a MySQL database running on **Ubuntu Server**. It supports operations like deposit, withdrawal, and balance checking.

---

## 🧰 Technologies Used

- Java
- JDBC (MySQL Connector 9.2.0)
- MySQL Server 8.0
- Ubuntu 22.04 LTS

---

## 🏗️ Database Setup

### 1. Create Database and Tables

```sql
CREATE DATABASE mcadb12;
USE mcadb12;

CREATE TABLE IF NOT EXISTS users (
    account_number INT PRIMARY KEY,
    pin INT,
    balance DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    account_number INT,
    transaction_type VARCHAR(20),
    amount DECIMAL(10,2),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 2. Sample Data

```sql
INSERT INTO users VALUES (1212, 5164, 217199.80), (1224, 8143, 900000.00);
```

---

## 🔗 JDBC Setup

### 1. Download Connector
Downloaded: `mysql-connector-java-9.2.0.jar`
Link for mysql connector:"https://cdn.mysql.com//Downloads/Connector-J/mysql-connector-j_9.2.0-1ubuntu22.04_all.deb"

### 2. Locate the JAR

```bash
find / -name "mysql-connector-java*.jar"
# Output: /usr/share/java/mysql-connector-java-9.2.0.jar
```

---

## 🧪 Compilation & Execution

### ✅ Compile the Java Program

```bash
javac -cp .:/usr/share/java/mysql-connector-java-9.2.0.jar Bank.java
```

### ▶️ Run the Program

```bash
java -cp .:/usr/share/java/mysql-connector-java-9.2.0.jar Bank
```

---

## 📷 Screenshots

### 💳 ATM Interface Output
![ATM UI](./images/atm_interface.jpeg)

### 🔐 MySQL Transactions Table
![MySQL Transactions](./images/mysql_transactions.jpeg)

### 🗃️ Users Table
![Users Table](./images/mysql_users.jpeg)

### 🛠️ Table Schema & Structure
![Table Schema](./images/table_schema.jpeg)

---

## 📌 Notes

- Ensure MySQL service is running.
- Java must be installed and added to the environment variables.
- Proper error handling should be added for real-world banking apps.
