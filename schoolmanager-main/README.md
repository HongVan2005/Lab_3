# School Manager API

Ứng dụng quản lý sinh viên Spring Boot với Swagger/OpenAPI, SQL Server, và REST API.

## 📋 Tính Năng

✅ **CRUD Operations** - Tạo, đọc, cập nhật, xóa sinh viên  
✅ **Advanced Search** - Tìm kiếm nâng cao theo ID, tên, tuổi, giới tính  
✅ **Swagger UI** - Tài liệu API tương tác  
✅ **Validation** - Kiểm tra dữ liệu đầu vào  
✅ **CORS Support** - Hỗ trợ yêu cầu từ các domain khác  
✅ **SQL Server Integration** - Kết nối với SQL Server  

## 🚀 Yêu Cầu

- **Java 21+**
- **Maven 3.9+**
- **SQL Server** (localhost:1433)
- **Database**: `school`
- **Credentials**: `sa` / `123456`

## 📦 Cài Đặt

```bash
# Clone hoặc tải dự án
cd schoolmanager-main

# Build dự án
mvn clean package

# Khởi động ứng dụng
java -jar target/schoolmanager-0.0.1-SNAPSHOT.jar

# Hoặc dùng Maven
mvn spring-boot:run
```

## 🌐 Endpoints

### Base URL: `http://localhost:8080`

#### 1. Lấy Tất Cả Sinh Viên
```http
GET /students
```
**Mô tả**: Trả về danh sách tất cả sinh viên

**Response**:
```json
[
  {
    "id": 1,
    "name": "Nguyễn Văn A",
    "email": "a@gmail.com",
    "age": 20,
    "gender": "Nam",
    "canDelete": true
  }
]
```

#### 2. Lấy Sinh Viên Theo ID
```http
GET /students/{id}
```
**Mô tả**: Lấy thông tin chi tiết của một sinh viên

**Example**: `GET /students/1`

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

#### 3. Thêm Sinh Viên Mới
```http
POST /students
Content-Type: application/json

{
  "name": "Trần Thị B",
  "email": "b@gmail.com",
  "age": 21,
  "gender": "Nữ"
}
```

**Mô tả**: Tạo sinh viên mới

**Response**: `201 Created`

#### 4. Cập Nhật Sinh Viên
```http
PUT /students/{id}
Content-Type: application/json

{
  "name": "Trần Thị B",
  "email": "b.updated@gmail.com",
  "age": 21,
  "gender": "Nữ"
}
```

**Mô tả**: Cập nhật thông tin sinh viên

**Response**: `200 OK`

#### 5. Xóa Sinh Viên
```http
DELETE /students/{id}
```

**Mô tả**: Xóa sinh viên khỏi hệ thống

**Example**: `DELETE /students/1`

**Response**: `200 OK`

#### 6. Tìm Kiếm Theo Tên
```http
GET /students/search?name=Nguyễn
```

**Mô tả**: Tìm sinh viên theo tên (không phân biệt chữ hoa/chữ thường)

**Response**:
```json
[
  {
    "id": 1,
    "name": "Nguyễn Văn A",
    "email": "a@gmail.com",
    "age": 20,
    "gender": "Nam",
    "canDelete": true
  }
]
```

#### 7. Tìm Kiếm Nâng Cao
```http
GET /students/advanced-search?name=Nguyễn&age=20&gender=Nam
```

**Mô tả**: Tìm kiếm theo nhiều tiêu chí

**Parameters**:
- `id` (optional): ID sinh viên
- `name` (optional): Tên sinh viên
- `age` (optional): Tuổi sinh viên
- `gender` (optional): Giới tính

**Response**: Danh sách sinh viên khớp tiêu chí

#### 8. Kiểm Tra Server
```http
GET /students/test
```

**Response**: `SERVER OK`

## 📚 API Documentation

Swagger UI: **http://localhost:8080/swagger-ui.html**

API Docs JSON: **http://localhost:8080/v3/api-docs**

## ⚙️ Cấu Hình

Chỉnh sửa `src/main/resources/application.properties`:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:sqlserver://localhost:1433;...databaseName=school
spring.datasource.username=sa
spring.datasource.password=123456

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/api-docs
```

## 🗄️ Database Schema

```sql
CREATE TABLE students (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    age INT,
    gender NVARCHAR(50),
    canDelete BIT DEFAULT 1
);
```

## 📝 Mô Hình Dữ Liệu

### Student
| Field | Type | Required | Constraints |
|-------|------|----------|-------------|
| id | Integer | No | Auto-generated |
| name | String | Yes | Min 1 char |
| email | String | Yes | Valid email, unique |
| age | Integer | No | 1-120 |
| gender | String | No | - |
| canDelete | Boolean | No | Default: true |

## 🛠️ Công Nghệ

- **Spring Boot 4.0.1** - Framework chính
- **Spring Data JPA** - ORM
- **Hibernate 7.2** - JPA Implementation
- **SQL Server** - Database
- **Thymeleaf** - Template Engine
- **SpringDoc OpenAPI 2.5.0** - Swagger/OpenAPI
- **Jakarta Validation** - Kiểm tra dữ liệu
- **Maven** - Build Tool
- **Java 21** - Language

## 📌 Lưu Ý Quan Trọng

1. **Database phải tồn tại** - Tạo database `school` trước khi chạy ứng dụng
2. **SQL Server phải chạy** - Đảm bảo SQL Server đang chạy trên localhost:1433
3. **Credentials** - Kiểm tra username/password trong application.properties
4. **Hibernate auto-update** - Tự động tạo table nếu chưa tồn tại

## 🧪 Kiểm Tra API

### Sử dụng cURL

```bash
# Lấy tất cả
curl http://localhost:8080/students

# Lấy theo ID
curl http://localhost:8080/students/1

# Thêm mới
curl -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"test@gmail.com","age":20,"gender":"Nam"}'

# Cập nhật
curl -X PUT http://localhost:8080/students/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Updated","email":"updated@gmail.com","age":21,"gender":"Nam"}'

# Xóa
curl -X DELETE http://localhost:8080/students/1

# Tìm kiếm
curl "http://localhost:8080/students/search?name=Nguyễn"

# Tìm nâng cao
curl "http://localhost:8080/students/advanced-search?age=20&gender=Nam"
```

### Sử dụng Postman

1. Mở Postman
2. Import từ: `http://localhost:8080/v3/api-docs`
3. Thực thi các request

## 🐛 Troubleshooting

### Lỗi Kết Nối Database
```
Error: Cannot get a connection, pool error Timeout waiting for idle object
```
**Giải pháp**: Kiểm tra SQL Server, credentials, database name

### Hibernate Dialect Warning
```
HHH90000025: SQLServerDialect does not need to be specified explicitly
```
**Giải pháp**: Bỏ `spring.jpa.properties.hibernate.dialect` khỏi properties (không bắt buộc)

### CORS Errors
Đã cấu hình `@CrossOrigin(origins = "*")` để cho phép tất cả domain

## 📞 Liên Hệ

- **Email**: support@schoolmanager.com
- **Website**: https://schoolmanager.com

## 📄 License

Apache License 2.0
