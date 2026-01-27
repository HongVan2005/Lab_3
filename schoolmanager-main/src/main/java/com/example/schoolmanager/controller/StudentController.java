package com.example.schoolmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService service;

    // Test nhanh server sống hay chết
    @GetMapping("/test")
    public String test() {
        return "SERVER OK";
    }

    // Lấy tất cả sinh viên
    @GetMapping
    public List<Student> getAll() {
        return service.getAll();
    }

    // Lấy sinh viên theo ID
    @GetMapping("/{id}")
    public Student getById(@PathVariable int id) {
        return service.getStudentById(id);
    }

    // Thêm sinh viên
    @PostMapping
    public Student add(@RequestBody Student student) {
        return service.addStudent(student);
    }

    // Cập nhật sinh viên
    @PutMapping("/{id}")
    public Student update(@PathVariable int id, @RequestBody Student student) {
        student.setId(id);
        return service.addStudent(student);
    }

    // Xóa sinh viên
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.deleteStudent(id);
    }

    // Tìm theo tên
    @GetMapping("/search")
    public List<Student> search(@RequestParam String name) {
        return service.findByName(name);
    }

    // 🔍 Tìm kiếm nâng cao
    @GetMapping("/advanced-search")
    public List<Student> advancedSearch(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String gender) {
        return service.advancedSearch(id, name, age, gender);
    }
}
