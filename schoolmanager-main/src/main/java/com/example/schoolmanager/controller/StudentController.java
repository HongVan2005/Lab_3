package com.example.schoolmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.schoolmanager.model.Student;
import com.example.schoolmanager.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
@Tag(name = "Student Management", description = "API quản lý sinh viên")
public class StudentController {

    @Autowired
    private StudentService service;

    // Test nhanh server sống hay chết
    @GetMapping("/test")
    @Operation(summary = "Kiểm tra server", description = "Trả về thông báo khi server chạy bình thường")
    @ApiResponse(responseCode = "200", description = "Server đang hoạt động")
    public String test() {
        return "SERVER OK";
    }

    // Lấy tất cả sinh viên
    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả sinh viên", description = "Trả về danh sách tất cả sinh viên trong hệ thống")
    @ApiResponse(responseCode = "200", description = "Thành công", content = @Content(schema = @Schema(implementation = Student.class)))
    public List<Student> getAll() {
        return service.getAll();
    }

    // Lấy sinh viên theo ID
    @GetMapping("/{id}")
    @Operation(summary = "Lấy sinh viên theo ID", description = "Trả về thông tin chi tiết của một sinh viên dựa trên ID")
    @ApiResponse(responseCode = "200", description = "Thành công")
    @ApiResponse(responseCode = "404", description = "Sinh viên không tìm thấy")
    public Student getById(@PathVariable int id) {
        return service.getStudentById(id);
    }

    // Thêm sinh viên
    @PostMapping
    @Operation(summary = "Thêm sinh viên mới", description = "Tạo một sinh viên mới trong hệ thống")
    @ApiResponse(responseCode = "201", description = "Sinh viên được tạo thành công")
    @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ")
    public ResponseEntity<Student> add(@Valid @RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addStudent(student));
    }

    // Cập nhật sinh viên
    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật thông tin sinh viên", description = "Cập nhật thông tin của một sinh viên dựa trên ID")
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    @ApiResponse(responseCode = "404", description = "Sinh viên không tìm thấy")
    public Student update(@PathVariable int id, @Valid @RequestBody Student student) {
        student.setId(id);
        return service.addStudent(student);
    }

    // Xóa sinh viên
    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa sinh viên", description = "Xóa một sinh viên khỏi hệ thống dựa trên ID")
    @ApiResponse(responseCode = "200", description = "Xóa thành công")
    @ApiResponse(responseCode = "404", description = "Sinh viên không tìm thấy")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    // Tìm theo tên
    @GetMapping("/search")
    @Operation(summary = "Tìm sinh viên theo tên", description = "Tìm kiếm sinh viên dựa trên tên (không phân biệt chữ hoa chữ thường)")
    @ApiResponse(responseCode = "200", description = "Thành công")
    public List<Student> search(@RequestParam String name) {
        return service.findByName(name);
    }

    // 🔍 Tìm kiếm nâng cao
    @GetMapping("/advanced-search")
    @Operation(summary = "Tìm kiếm nâng cao", description = "Tìm kiếm sinh viên dựa trên nhiều tiêu chí: ID, tên, tuổi, giới tính")
    @ApiResponse(responseCode = "200", description = "Thành công")
    public List<Student> advancedSearch(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String gender) {
        return service.advancedSearch(id, name, age, gender);
    }
}
