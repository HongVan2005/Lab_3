package com.example.schoolmanager.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.schoolmanager.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    // 🔍 Tìm theo tên
    List<Student> findByNameContainingIgnoreCase(String name);

    // 🔍 Tìm đa trường: tên / email / giới tính
    @Query("""
        SELECT s FROM Student s
        WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :key, '%'))
           OR LOWER(s.email) LIKE LOWER(CONCAT('%', :key, '%'))
           OR LOWER(s.gender) LIKE LOWER(CONCAT('%', :key, '%'))
    """)
    List<Student> search(@Param("key") String key);

    // 🔍 Tìm kiếm nâng cao: theo id, tên, tuổi, giới tính
    @Query("""
        SELECT s FROM Student s
        WHERE (:id IS NULL OR s.id = :id)
           AND (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))
           AND (:age IS NULL OR s.age = :age)
           AND (:gender IS NULL OR LOWER(s.gender) = LOWER(:gender))
    """)
    List<Student> findByAdvanced(
        @Param("id") Integer id,
        @Param("name") String name,
        @Param("age") Integer age,
        @Param("gender") String gender
    );
}
