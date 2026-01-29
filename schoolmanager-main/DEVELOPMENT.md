# 🚀 Development Guide - School Manager API

## 📚 Cấu Trúc Dự Án

```
schoolmanager-main/
├── src/
│   ├── main/
│   │   ├── java/com/example/schoolmanager/
│   │   │   ├── SchoolmanagerApplication.java      (Entry point)
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java             (Swagger config)
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java
│   │   │   │   └── StudentController.java         (REST API)
│   │   │   ├── model/
│   │   │   │   └── Student.java                   (Entity)
│   │   │   ├── respository/
│   │   │   │   └── StudentRepository.java         (JPA Repository)
│   │   │   └── service/
│   │   │       └── StudentService.java            (Business Logic)
│   │   └── resources/
│   │       ├── application.properties             (Configuration)
│   │       └── templates/
│   │           └── student.html                   (Thymeleaf template)
│   └── test/
│       └── java/.../SchoolmanagerApplicationTests.java
├── pom.xml                                        (Maven config)
├── README.md                                      (Documentation)
├── TESTING.md                                     (API Testing guide)
└── SETUP_DATABASE.md                              (Database setup)
```

---

## 🛠️ Development Workflow

### 1. Setup Environment

```bash
# Clone dự án
cd schoolmanager-main

# Build
mvn clean install

# Run
mvn spring-boot:run
# hoặc
java -jar target/schoolmanager-0.0.1-SNAPSHOT.jar
```

### 2. IDE Setup

#### IntelliJ IDEA
1. File → Open → Chọn schoolmanager-main folder
2. Nó sẽ tự load Maven configuration
3. Project Structure → Kiểm tra JDK 21

#### VS Code
1. Install extensions:
   - Extension Pack for Java
   - Spring Boot Extension Pack
   - REST Client (optional)
2. Open folder: schoolmanager-main
3. Nó sẽ tự setup Java project

#### Eclipse
1. File → Open Projects from File System
2. Select schoolmanager-main
3. Eclipse sẽ convert thành Maven project

---

## 📝 Code Conventions

### Java Naming
- Classes: `PascalCase` (e.g., `StudentController`)
- Methods: `camelCase` (e.g., `getStudent()`)
- Constants: `UPPER_SNAKE_CASE` (e.g., `MAX_AGE`)
- Variables: `camelCase` (e.g., `studentName`)

### File Organization
```java
// 1. Package declaration
package com.example.schoolmanager.controller;

// 2. Imports (organized)
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

// 3. Class declaration
@RestController
@RequestMapping("/students")
public class StudentController {
    
    // 4. Fields
    @Autowired
    private StudentService service;
    
    // 5. Constructors
    
    // 6. Public methods
    @GetMapping
    public List<Student> getAll() { }
    
    // 7. Private methods
    private void validateStudent() { }
}
```

---

## 🔄 Adding New Features

### Example: Add Email Verification

#### 1. Model Changes
```java
// Student.java
@Entity
public class Student {
    // ... existing fields ...
    
    @Column(columnDefinition = "bit default 0")
    private Boolean emailVerified = false;
    
    // Getters & Setters
    public Boolean getEmailVerified() { return emailVerified; }
    public void setEmailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; }
}
```

#### 2. Repository Changes
```java
// StudentRepository.java
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    // Existing methods...
    
    // Tìm sinh viên chưa verify
    List<Student> findByEmailVerifiedFalse();
    
    // Tìm theo email
    Optional<Student> findByEmail(String email);
}
```

#### 3. Service Changes
```java
// StudentService.java
@Service
public class StudentService {
    // ... existing methods ...
    
    public List<Student> getUnverifiedStudents() {
        return repository.findByEmailVerifiedFalse();
    }
    
    public Student verifyEmail(Integer id) {
        Student student = repository.findById(id).orElse(null);
        if (student != null) {
            student.setEmailVerified(true);
            return repository.save(student);
        }
        return null;
    }
}
```

#### 4. Controller Changes
```java
// StudentController.java
@RestController
@RequestMapping("/students")
public class StudentController {
    // ... existing methods ...
    
    @GetMapping("/unverified")
    @Operation(summary = "Lấy danh sách sinh viên chưa verify email")
    public List<Student> getUnverified() {
        return service.getUnverifiedStudents();
    }
    
    @PostMapping("/{id}/verify-email")
    @Operation(summary = "Verify email cho sinh viên")
    public Student verifyEmail(@PathVariable int id) {
        return service.verifyEmail(id);
    }
}
```

---

## 🧪 Unit Testing

### Test Student Service

```java
// StudentServiceTest.java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StudentServiceTest {
    
    private StudentService service;
    
    @Mock
    private StudentRepository repository;
    
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new StudentService();
        service.repository = repository;
    }
    
    @Test
    void testGetAll() {
        // Arrange
        List<Student> mockStudents = Arrays.asList(
            new Student(1, "Test", "test@gmail.com", 20, "Nam")
        );
        when(repository.findAll()).thenReturn(mockStudents);
        
        // Act
        List<Student> result = service.getAll();
        
        // Assert
        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getName());
    }
}
```

### Test Student Controller

```java
// StudentControllerTest.java
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private StudentService service;
    
    @Test
    void testGetAll() throws Exception {
        // Arrange
        List<Student> mockStudents = Arrays.asList(
            new Student(1, "Test", "test@gmail.com", 20, "Nam")
        );
        when(service.getAll()).thenReturn(mockStudents);
        
        // Act & Assert
        mockMvc.perform(get("/students"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Test"));
    }
}
```

---

## 🔍 Debugging

### Enable Debug Logging
```properties
# application-dev.properties
logging.level.root=INFO
logging.level.com.example.schoolmanager=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Run in Debug Mode
```bash
# Maven
mvn spring-boot:run -Dspring-boot.run.arguments="--debug"

# Java
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 \
     -jar target/schoolmanager-0.0.1-SNAPSHOT.jar
```

### Debug in IDE
1. Set breakpoint (click line number)
2. Run → Debug (F5 in VS Code, Shift+F9 in IntelliJ)
3. Navigate to endpoint
4. Pauses at breakpoint, inspect variables

---

## 🚀 Performance Optimization

### Database Optimization
```java
// Lazy Loading
@Entity
public class Student {
    @OneToMany(fetch = FetchType.LAZY)  // Not eager
    private List<Enrollment> enrollments;
}

// Batch fetch
@Query("SELECT s FROM Student s WHERE s.id IN :ids")
List<Student> findByIds(@Param("ids") List<Integer> ids);
```

### Caching
```java
@Service
public class StudentService {
    @Cacheable("students")
    public Student getStudentById(int id) {
        return repository.findById(id).orElse(null);
    }
    
    @CacheEvict(value = "students", key = "#id")
    public void deleteStudent(int id) {
        repository.deleteById(id);
    }
}
```

---

## 🔒 Security Best Practices

### Authentication (Future)
```java
// Role-based access
@RestController
@RequestMapping("/students")
public class StudentController {
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public List<Student> getAll() { }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable int id) { }
}
```

### Input Validation
```java
@Entity
public class Student {
    
    @NotBlank(message = "Tên không được trống")
    @Size(min = 2, max = 100)
    private String name;
    
    @Email(message = "Email không hợp lệ")
    private String email;
    
    @Min(1)
    @Max(120)
    private Integer age;
}
```

### HTTPS Configuration
```properties
# application.properties
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=password
server.ssl.key-store-type=PKCS12
```

---

## 📦 Building & Deployment

### Build JAR
```bash
mvn clean package
# Output: target/schoolmanager-0.0.1-SNAPSHOT.jar
```

### Build Docker Image
```dockerfile
# Dockerfile
FROM eclipse-temurin:21-jre-alpine
COPY target/schoolmanager-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

```bash
# Build
docker build -t schoolmanager:latest .

# Run
docker run -p 8080:8080 schoolmanager:latest
```

### Production Deployment
```bash
# Run with profile
java -jar schoolmanager.jar --spring.profiles.active=prod

# Set database URL
java -jar schoolmanager.jar \
  --spring.datasource.url=jdbc:sqlserver://prod-db:1433;databaseName=school \
  --spring.datasource.username=admin
```

---

## 🐛 Common Issues & Solutions

### Port Already in Use
```bash
# Find process using port 8080
netstat -ano | findstr :8080

# Kill process
taskkill /PID <PID> /F

# Or change port
java -jar app.jar --server.port=8081
```

### Database Locked
```sql
-- Kill database connections
KILL <session_id>;
```

### Dependency Issues
```bash
# Clear Maven cache
mvn clean dependency:purge-local-repository

# Rebuild
mvn clean install
```

---

## 📚 Learning Resources

### Spring Boot
- [Official Docs](https://spring.io/projects/spring-boot)
- [Baeldung Tutorials](https://www.baeldung.com/spring-boot)
- [Spring Academy](https://spring.academy/)

### JPA/Hibernate
- [JPA Tutorial](https://docs.oracle.com/cd/E17904_01/apirefs.shtml)
- [Hibernate Docs](https://hibernate.org/orm/documentation/)

### REST API
- [REST Best Practices](https://restfulapi.net/)
- [HTTP Status Codes](https://httpwg.org/specs/rfc9110.html)

### Testing
- [JUnit 5 Documentation](https://junit.org/junit5/)
- [Mockito Guide](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

---

## 🎯 Next Steps

1. **Add Authentication/Authorization**
   - Implement JWT tokens
   - Role-based access control

2. **Add File Upload**
   - Student profile pictures
   - Document management

3. **Add Email Notifications**
   - Verification emails
   - Status notifications

4. **Add Pagination**
   - Large result sets
   - Better performance

5. **Add API Versioning**
   - `/api/v1/students`
   - `/api/v2/students`

6. **Add Database Migration**
   - Liquibase or Flyway
   - Version control for schema

7. **Add Monitoring**
   - Actuator endpoints
   - Prometheus metrics
   - ELK Stack logging

---

## 💬 Questions?

- Check README.md for overview
- Check TESTING.md for API testing
- Check SETUP_DATABASE.md for database setup
- Read code comments
- Search Stack Overflow
- Ask on Spring Community Forum
