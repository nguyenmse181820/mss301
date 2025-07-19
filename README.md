# Car Rental Management System - Microservices

A comprehensive Spring Boot microservices-based car rental management system with JWT authentication, service discovery, and API gateway.

## 🚀 Quick Start

### Prerequisites
- Java 21+
- Maven 3.6+
- SQL Server 2019+
- Git

### Test Accounts

#### Customer Account
- **Email**: `cus@gmail.com`
- **Password**: `123456`
- **Role**: CUSTOMER
- **Access**: Can browse cars, create rentals, manage profile

#### Admin Account
- **Email**: `admin@gmail.com`
- **Password**: `123456`
- **Role**: ADMIN
- **Access**: Full system access, reports, car/customer management

### Additional Test Users
#### Customer Accounts:
- **john.doe@gmail.com** / `123456` (CUSTOMER)
- **jane.smith@example.com** / `123456` (CUSTOMER)
- **bob.johnson@test.com** / `123456` (CUSTOMER)

#### Admin Account:
- **admin.user@company.com** / `123456` (ADMIN)

## 🏗️ System Architecture

### Microservices
1. **Config Server** (Port 8888) - Centralized configuration
2. **Discovery Service** (Port 8761) - Eureka service registry
3. **API Gateway** (Port 8080) - Single entry point with JWT validation
4. **Customer Service** (Port 8083) - User management and authentication
5. **Car Service** (Port 8082) - Vehicle inventory management
6. **Renting Service** (Port 8084) - Rental transactions

### Databases
- **customer_db** - Customer information and authentication
- **car_db** - Vehicle inventory, manufacturers, suppliers
- **renting_db** - Rental transactions and details

## 🗄️ Database Export Instructions

### Method 1: SQL Server Management Studio (SSMS) - RECOMMENDED
1. **Connect to your SQL Server instance**
2. **For each database** (`customer_db`, `car_db`, `renting_db`):
   - Right-click on the database
   - Select **"Tasks" → "Generate Scripts..."**
   - Choose **"Script entire database and all database objects"**
   - Click **"Next"** → **"Advanced"** button
   - Set **"Types of data to script"** to **"Schema and data"**
   - Set **"Script for Server Version"** to your SQL Server version
   - Choose **"Save to file"** and specify filename
   - Save as: `customer_db_export.sql`, `car_db_export.sql`, `renting_db_export.sql`

### Method 2: PowerShell Script (Windows)
Create a file named `export_databases.ps1`:
```powershell
# Database export script
$server = "localhost"
$databases = @("customer_db", "car_db", "renting_db")

foreach ($db in $databases) {
    Write-Host "Exporting $db..."
    
    # Export schema and data
    sqlcmd -S $server -d $db -E -Q "
    EXEC sp_helpdb '$db';
    " -o "$db_info.txt"
    
    # Use bcp to export data
    bcp "SELECT * FROM customer" queryout "$db_customer_data.txt" -S $server -d customer_db -T -c
    bcp "SELECT * FROM car_information" queryout "$db_car_data.txt" -S $server -d car_db -T -c
    bcp "SELECT * FROM renting_transaction" queryout "$db_renting_data.txt" -S $server -d renting_db -T -c
}
Write-Host "Export completed!"
```

### Method 3: SQL Scripts for Manual Export
Execute these scripts in SQL Server Management Studio and save results:

#### Customer Database Export:
```sql
-- Connect to customer_db first
SELECT 'Customer Data Export - ' + CAST(GETDATE() AS VARCHAR(20)) AS export_info;
SELECT * FROM customer;
```

#### Car Database Export:
```sql
-- Connect to car_db first  
SELECT 'Car Data Export - ' + CAST(GETDATE() AS VARCHAR(20)) AS export_info;
SELECT * FROM manufacturer;
SELECT * FROM supplier;
SELECT * FROM car_information;
```

#### Renting Database Export:
```sql
-- Connect to renting_db first
SELECT 'Renting Data Export - ' + CAST(GETDATE() AS VARCHAR(20)) AS export_info;
SELECT * FROM renting_transaction;
SELECT * FROM renting_detail;
```
