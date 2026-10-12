package com.example.course_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "course_categories")
@Data
public class CourseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên loại khóa học không được để trống")
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Course> courses;
}