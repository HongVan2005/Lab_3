# 🗄️ SQL Server Setup Guide

## 📋 Yêu Cầu

- SQL Server 2019+ hoặc SQL Server Express
- SQL Server Management Studio (SSMS) - tùy chọn
- Port 1433 available

## 1️⃣ Cài Đặt SQL Server

### Windows

#### Option A: SQL Server Express (Miễn Phí)
1. Download từ: https://www.microsoft.com/en-us/sql-server/sql-server-downloads
2. Chọn "Express"
3. Chạy installer
4. Chọn "Express" installation
5. Chấp nhận cấu hình mặc định

#### Option B: SQL Server Developer (Miễn Phí)
1. Download từ: https://www.microsoft.com/en-us/sql-server/sql-server-downloads
2. Chọn "Developer"
3. Chạy installer
4. Chấp nhận điều khoản
5. Hoàn thành setup

### Verify Installation

```bash
# Kiểm tra SQL Server service đang chạy
# Windows: Services (services.msc)
# Tìm: SQL Server (MSSQLSERVER) hoặc SQL Server (SQLEXPRESS)
```

---

## 2️⃣ Tạo Database

### Using SQL Server Management Studio (SSMS)

1. **Mở SSMS**
   - Nếu chưa cài: Download từ https://ssms.microsoft.com/

2. **Kết Nối**
   - Server name: `localhost` hoặc `localhost\SQLEXPRESS`
   - Authentication: Windows Authentication hoặc SQL Server Authentication
   - Username: `sa` (nếu dùng SQL Auth)
   - Password: (như trong cài đặt)

3. **Tạo Database**
   - Right-click "Databases" → "New Database"
   - Database name: `school`
   - Click "OK"

### Using SQL Commands

```sql
-- Tạo database
CREATE DATABASE school;

-- Chọn database
USE school;

-- Tạo bảng students
CREATE TABLE students (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    age INT,
    gender NVARCHAR(50),
    canDelete BIT DEFAULT 1
);

-- Thêm sample data
INSERT INTO students (name, email, age, gender) VALUES
('Nguyễn Văn A', 'a@gmail.com', 20, 'Nam'),
('Trần Thị B', 'b@gmail.com', 21, 'Nữ'),
('Hoàng Văn C', 'c@gmail.com', 19, 'Nam'),
('Phạm Thị D', 'd@gmail.com', 22, 'Nữ');

-- Kiểm tra
SELECT * FROM students;
```

---

## 3️⃣ Cấu Hình Connection String

Ứng dụng sử dụng connection string này trong `application.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;databaseName=school
spring.datasource.username=sa
spring.datasource.password=123456
```

### Giải Thích

| Parameter | Giá Trị | Ý Nghĩa |
|-----------|---------|---------|
| `host` | `localhost` | Server SQL |
| `port` | `1433` | Port mặc định SQL Server |
| `databaseName` | `school` | Database để sử dụng |
| `username` | `sa` | System administrator account |
| `password` | `123456` | Password (đặt trong setup) |
| `encrypt` | `true` | Mã hóa connection |
| `trustServerCertificate` | `true` | Tin tưởng certificate |

---

## 4️⃣ Kiểm Tra Kết Nối

### Cách 1: Sử dụng SSMS
1. Mở SSMS
2. Nhập: `localhost\SQLEXPRESS` (hoặc `localhost` cho default instance)
3. Username: `sa`
4. Password: `123456` (hoặc password bạn đặt)
5. Click "Connect"

### Cách 2: Command Line (PowerShell/CMD)
```bash
# Windows PowerShell
sqlcmd -S localhost -U sa -P 123456

# Kết nối thành công nếu thấy "1>"
# Kiểm tra databases:
# SELECT name FROM sys.databases;
# GO

# Exit:
# EXIT
```

### Cách 3: Qua Ứng Dụng
- Start ứng dụng Spring Boot
- Nếu không có lỗi database → Connection OK ✅

---

## 5️⃣ Troubleshooting

### Problem: "Cannot connect to localhost:1433"

**Nguyên Nhân**: SQL Server không chạy hoặc sai port

**Giải Pháp**:
1. Kiểm tra SQL Server service
   ```bash
   # Windows Services (Win+R → services.msc)
   # Tìm "SQL Server (SQLEXPRESS)" → Start
   ```

2. Kiểm tra port
   ```bash
   # Netstat
   netstat -ano | findstr :1433
   # Nếu không thấy → port có vấn đề
   ```

3. Enable TCP/IP
   - SQL Server Configuration Manager
   - SQL Server Network Configuration
   - SQLEXPRESS (hoặc tên instance)
   - TCP/IP → Enable

---

### Problem: "Login failed for user 'sa'"

**Nguyên Nhân**: Username/password sai

**Giải Pháp**:
1. Kiểm tra setup password
2. Thay đổi password (SSMS):
   ```sql
   ALTER LOGIN sa WITH PASSWORD = '123456';
   ```

3. Kiểm tra Authentication Mode
   - Windows Authentication mặc định
   - Enable "SQL Server and Windows Authentication Mode"
   - Restart SQL Server service

---

### Problem: "Database 'school' does not exist"

**Nguyên Nhân**: Database chưa tạo

**Giải Pháp**:
```sql
-- SSMS: New Query
-- Chạy lệnh này:
CREATE DATABASE school;
```

---

### Problem: "Cannot reach server on 'localhost'"

**Giải Pháp**:
1. Kiểm tra firewall
   ```bash
   # Windows Defender Firewall with Advanced Security
   # Cho phép port 1433
   ```

2. Sử dụng IP thay vì hostname
   ```properties
   # Thay đổi:
   spring.datasource.url=jdbc:sqlserver://127.0.0.1:1433;...
   ```

---

## 6️⃣ Advanced Configuration

### Enable Encryption (Optional)
```sql
-- Cấu hình SSL/TLS (nâng cao)
-- Chi tiết: SQL Server docs
```

### Database Backups
```sql
-- Backup database
BACKUP DATABASE school TO DISK = 'C:\Backups\school.bak';

-- Restore database
RESTORE DATABASE school FROM DISK = 'C:\Backups\school.bak';
```

### View Connections
```sql
-- Xem active connections
SELECT 
    sp.spid,
    sp.loginame,
    sp.hostname,
    sp.program_name,
    sp.status
FROM sys.sysprocesses sp
WHERE sp.dbid = DB_ID('school');
```

---

## 7️⃣ Performance Tips

### Index Creation
```sql
-- Tạo index cho email (unique)
CREATE UNIQUE INDEX idx_email ON students(email);

-- Tạo index cho name (search)
CREATE INDEX idx_name ON students(name);
```

### Query Optimization
```sql
-- Xem execution plans
SET STATISTICS IO ON;
SELECT * FROM students WHERE name LIKE '%Nguyễn%';
SET STATISTICS IO OFF;
```

---

## 📊 Database Schema

```
Database: school
├── Tables
│   └── students
│       ├── id (INT, PK, Identity)
│       ├── name (NVARCHAR(100), NOT NULL)
│       ├── email (NVARCHAR(100), NOT NULL, UNIQUE)
│       ├── age (INT)
│       ├── gender (NVARCHAR(50))
│       └── canDelete (BIT, DEFAULT 1)
└── Views/Procedures (Tùy chọn)
```

---

## ✅ Checklist

- [ ] SQL Server cài đặt
- [ ] SQL Server service chạy
- [ ] Database 'school' tạo
- [ ] Bảng 'students' tạo
- [ ] Connection string chính xác
- [ ] Test kết nối thành công
- [ ] Application start không lỗi

---

## 🔗 Liên Kết Hữu Ích

- [SQL Server Express Download](https://www.microsoft.com/en-us/sql-server/sql-server-downloads)
- [SSMS Download](https://ssms.microsoft.com/)
- [SQL Server Docs](https://learn.microsoft.com/en-us/sql/)
- [Azure Data Studio](https://azure.microsoft.com/en-us/products/data-studio/)

---

## 💡 Alternative Tools

Nếu không muốn SSMS, có thể dùng:
1. **Azure Data Studio** - Lightweight, cross-platform
2. **DBeaver** - Free, open-source
3. **SQL Server Management Studio** - Official tool
4. **Command Line** (sqlcmd) - Minimal
