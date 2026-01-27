package com.example.schoolmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schoolmanager.model.Student;
import com.example.schoolmanager.respository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public Student addStudent(Student student) {
        return repository.save(student);
    }

    public void deleteStudent(int id) {
        repository.deleteById(id);
    }

    public List<Student> getAll() {
        return repository.findAll();
    }

    public Student getStudentById(int id) {
        return repository.findById(id).orElse(null);
    }

    // 👉 BẮT BUỘC PHẢI CÓ
    public List<Student> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    // 🔍 Tìm kiếm nâng cao
    public List<Student> advancedSearch(Integer id, String name, Integer age, String gender) {
        return repository.findByAdvanced(id, name, age, gender);
    }
}
