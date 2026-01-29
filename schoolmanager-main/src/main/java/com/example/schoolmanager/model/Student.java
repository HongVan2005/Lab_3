package com.example.schoolmanager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
@Schema(description = "Thông tin sinh viên")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID sinh viên", example = "1")
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Tên sinh viên không được để trống")
    @Schema(description = "Tên sinh viên", example = "Nguyễn Văn A")
    private String name;

    @Column(nullable = false, unique = true)
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    @Schema(description = "Email sinh viên", example = "a@gmail.com")
    private String email;

    @Min(value = 1, message = "Tuổi phải lớn hơn 0")
    @Max(value = 120, message = "Tuổi không hợp lệ")
    @Schema(description = "Tuổi sinh viên", example = "20")
    private Integer age;

    @Schema(description = "Giới tính", example = "Nam")
    private String gender;

    @Column(columnDefinition = "bit default 1")
    private Boolean canDelete = true;  // true = được phép xóa, false = không được xóa

    // ===== Constructor =====
    public Student() {}

    public Student(Integer id, String name, String email, Integer age, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.canDelete = true;
    }

    // ===== Getter & Setter =====
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }
}
