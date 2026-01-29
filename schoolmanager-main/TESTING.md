# 🧪 Hướng Dẫn Kiểm Tra API School Manager

## ✅ Các Bước Kiểm Tra

### 1️⃣ Kiểm Tra Server Đang Chạy

```bash
curl http://localhost:8080/students/test
```

**Kết quả dự kiến**: `SERVER OK`

---

### 2️⃣ Thêm Sinh Viên (POST)

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

**Kết quả dự kiến**:
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

---

### 3️⃣ Lấy Tất Cả Sinh Viên (GET)

```bash
curl http://localhost:8080/students
```

**Kết quả dự kiến**: Danh sách tất cả sinh viên

---

### 4️⃣ Lấy Sinh Viên Theo ID (GET)

```bash
curl http://localhost:8080/students/1
```

**Kết quả dự kiến**: Thông tin sinh viên ID 1

---

### 5️⃣ Cập Nhật Sinh Viên (PUT)

```bash
curl -X PUT http://localhost:8080/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Nguyễn Văn A (Updated)",
    "email": "a.updated@gmail.com",
    "age": 21,
    "gender": "Nam"
  }'
```

**Kết quả dự kiến**: Thông tin sinh viên đã cập nhật

---

### 6️⃣ Tìm Kiếm Theo Tên (GET)

```bash
curl "http://localhost:8080/students/search?name=Nguyễn"
```

**Kết quả dự kiến**: Danh sách sinh viên tên Nguyễn

---

### 7️⃣ Tìm Kiếm Nâng Cao (GET)

```bash
curl "http://localhost:8080/students/advanced-search?age=20&gender=Nam"
```

**Kết quả dự kiến**: Danh sách sinh viên tuổi 20, giới tính Nam

---

### 8️⃣ Xóa Sinh Viên (DELETE)

```bash
curl -X DELETE http://localhost:8080/students/1
```

**Kết quả dự kiến**: Sinh viên được xóa (response 200 OK)

---

## 🔗 Swagger UI

**URL**: http://localhost:8080/swagger-ui.html

Tại đây bạn có thể:
- Xem tài liệu API đầy đủ
- Kiểm tra các endpoints
- Thực thi requests trực tiếp
- Xem request/response examples

---

## 📋 Test Scenarios

### Scenario 1: Tạo Và Lấy Sinh Viên
1. Thêm sinh viên
2. Ghi nhận ID trả về
3. Lấy sinh viên theo ID đó
4. Kiểm tra dữ liệu match

### Scenario 2: Cập Nhật Và Xác Nhận
1. Thêm sinh viên
2. Cập nhật tên/email
3. Lấy lại để kiểm tra thay đổi
4. Xác nhận dữ liệu đã cập nhật

### Scenario 3: Tìm Kiếm
1. Thêm nhiều sinh viên
2. Tìm kiếm theo tên
3. Tìm kiếm nâng cao theo tuổi/giới tính
4. Kiểm tra kết quả

### Scenario 4: Xóa
1. Thêm sinh viên
2. Xóa sinh viên
3. Cố gắng lấy sinh viên đã xóa
4. Kiểm tra không còn tồn tại

---

## ⚠️ Validation Checks

### Email Validation
```bash
# ❌ Sai - Email không hợp lệ
curl -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"invalid-email","age":20,"gender":"Nam"}'
```

### Age Validation
```bash
# ❌ Sai - Tuổi không hợp lệ (>120)
curl -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"test@gmail.com","age":150,"gender":"Nam"}'
```

### Required Fields
```bash
# ❌ Sai - Tên trống
curl -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{"email":"test@gmail.com","age":20,"gender":"Nam"}'
```

---

## 🧪 Test Tools

### 1. Postman
- Import OpenAPI: `http://localhost:8080/v3/api-docs`
- Chạy requests với GUI

### 2. Insomnia
- Tương tự Postman
- Import từ API docs

### 3. cURL (Command Line)
- Đơn giản, không cần GUI
- Tốt cho scripting

### 4. Swagger UI
- Tích hợp sẵn trong ứng dụng
- Xem docs + test cùng lúc

---

## 📊 Expected Test Results

| Endpoint | Method | Status | Notes |
|----------|--------|--------|-------|
| /students/test | GET | 200 | Kiểm tra server |
| /students | GET | 200 | Lấy tất cả |
| /students/{id} | GET | 200 | Lấy 1 sinh viên |
| /students | POST | 201 | Tạo mới |
| /students/{id} | PUT | 200 | Cập nhật |
| /students/{id} | DELETE | 200 | Xóa |
| /students/search | GET | 200 | Tìm theo tên |
| /students/advanced-search | GET | 200 | Tìm nâng cao |

---

## 🐛 Common Issues & Solutions

### Issue: Connection Timeout
```
Error: Cannot connect to localhost:8080
```
**Solution**: Kiểm tra ứng dụng đang chạy
```bash
# Khởi động lại
java -jar target/schoolmanager-0.0.1-SNAPSHOT.jar
```

### Issue: Database Connection Failed
```
Error: Cannot get connection from pool
```
**Solution**: Kiểm tra SQL Server
```bash
# 1. SQL Server đang chạy?
# 2. Database 'school' tồn tại?
# 3. Username/password chính xác?
```

### Issue: Validation Error (400)
```json
{
  "message": "Email không hợp lệ"
}
```
**Solution**: Kiểm tra định dạng dữ liệu

---

## ✨ Best Practices

1. **Luôn sử dụng JSON headers**
   ```bash
   -H "Content-Type: application/json"
   ```

2. **Validate trước khi submit**
   - Email phải có @
   - Tuổi từ 1-120
   - Tên không được trống

3. **Kiểm tra response status**
   - 200 = OK
   - 201 = Created
   - 400 = Bad Request
   - 404 = Not Found
   - 500 = Server Error

4. **Sử dụng Swagger UI** để tìm hiểu API trước

---

## 📚 Additional Resources

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [JPA Documentation](https://jakarta.ee/specifications/persistence/)
- [Swagger/OpenAPI](https://swagger.io/)
- [REST API Best Practices](https://restfulapi.net/)
