package com.example.course_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "course_images")
@Data
public class CourseImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Đường dẫn ảnh không được để trống")
    @Column(columnDefinition = "TEXT") // Thêm dòng này để cho phép chứa chuỗi Base64 dài
    private String imageUrl;

    private Boolean isThumbnail = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}