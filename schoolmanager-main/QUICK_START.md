# ⚡ Quick Start Guide - School Manager API

## 🎯 5-Minute Setup

### Prerequisites
- Java 21+ installed
- SQL Server running on localhost:1433
- Maven installed
- 3GB free disk space

---

## 🚀 Step-by-Step Setup

### 1️⃣ Create Database (2 minutes)
```sql
-- Open SQL Server Management Studio (SSMS)
-- Run this:
CREATE DATABASE school;

-- Create table:
USE school;

CREATE TABLE students (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    age INT,
    gender NVARCHAR(50),
    canDelete BIT DEFAULT 1
);
```

### 2️⃣ Configure Database Credentials (1 minute)
Edit: `src/main/resources/application.properties`

```properties
# Database credentials
spring.datasource.username=sa              # Your SQL Server username
spring.datasource.password=123456          # Your SQL Server password
```

### 3️⃣ Build Project (2 minutes)
```bash
cd schoolmanager-main
mvn clean package -DskipTests
```

### 4️⃣ Run Application (30 seconds)
```bash
java -jar target/schoolmanager-0.0.1-SNAPSHOT.jar
```

**Expected output**:
```
Started SchoolmanagerApplication in 5.782 seconds
Tomcat started on port 8080
```

---

## ✅ Verify Installation

### 1. Check Server Running
```bash
curl http://localhost:8080/students/test
```
**Response**: `SERVER OK` ✅

### 2. Get All Students
```bash
curl http://localhost:8080/students
```
**Response**: `[]` (empty list) ✅

### 3. Open Swagger UI
Visit: **http://localhost:8080/swagger-ui.html** ✅

---

## 🧪 Test API (2 minutes)

### Add a Student
```bash
curl -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Nguyễn Văn A",
    "email": "a@gmail.com",
    "age": 20,
    "gender": "Nam"
  }'
```

**Response**:
```json
{
  "id": 1,
  "name": "Nguyễn Văn A",
  "email": "a@gmail.com",
  "age": 20,
  "gender": "Nam",
  "canDelete": true
}
```

### Get Student
```bash
curl http://localhost:8080/students/1
```

### Update Student
```bash
curl -X PUT http://localhost:8080/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Name",
    "email": "updated@gmail.com",
    "age": 21,
    "gender": "Nam"
  }'
```

### Delete Student
```bash
curl -X DELETE http://localhost:8080/students/1
```

---

## 🔗 Important URLs

| Purpose | URL |
|---------|-----|
| API Swagger UI | http://localhost:8080/swagger-ui.html |
| API Docs (JSON) | http://localhost:8080/v3/api-docs |
| Test Endpoint | http://localhost:8080/students/test |
| Get All | http://localhost:8080/students |
| Get By ID | http://localhost:8080/students/{id} |

---

## 📖 Documentation

| File | Content |
|------|---------|
| `README.md` | Full API documentation |
| `TESTING.md` | Detailed testing guide |
| `SETUP_DATABASE.md` | Database configuration |
| `DEVELOPMENT.md` | Development guide |
| `CHANGELOG.md` | Version history |

---

## ⚠️ Troubleshooting

### Issue: "Cannot connect to database"
```bash
# Check SQL Server is running
# Windows: Services → SQL Server (SQLEXPRESS)

# Verify connection string in application.properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;...databaseName=school
spring.datasource.username=sa
spring.datasource.password=123456
```

### Issue: "Port 8080 already in use"
```bash
# Find process
netstat -ano | findstr :8080

# Kill it
taskkill /PID <PID> /F

# Or use different port
java -jar schoolmanager-0.0.1-SNAPSHOT.jar --server.port=8081
```

### Issue: "Database doesn't exist"
```sql
-- Create database
CREATE DATABASE school;

-- Create table
USE school;
CREATE TABLE students (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    age INT,
    gender NVARCHAR(50),
    canDelete BIT DEFAULT 1
);
```

---

## 🎓 What's Included

✅ Complete REST API (CRUD operations)
✅ Swagger/OpenAPI documentation
✅ Input validation
✅ Error handling
✅ Database integration
✅ Advanced search
✅ CORS support

---

## 🚀 Next Steps

1. **Explore API** → Visit http://localhost:8080/swagger-ui.html
2. **Test Endpoints** → Use cURL or Postman
3. **Read Documentation** → Check README.md
4. **Develop** → See DEVELOPMENT.md for extending the app

---

## 💡 Tips

- Swagger UI is great for testing without cURL
- Use IDE debugger for step-by-step execution
- Check logs when something fails
- Keep database running at all times
- Build with `mvn clean package` before deploying

---

## 🆘 Help

**If something fails:**
1. Check console logs
2. Review TROUBLESHOOTING section above
3. Verify database connection
4. Ensure Java 21+ is installed
5. Check SQL Server is running

---

## 🎉 You're Ready!

Your School Manager API is now:
- ✅ Built
- ✅ Running on http://localhost:8080
- ✅ Documented at /swagger-ui.html
- ✅ Ready to use

**Happy coding! 🚀**
