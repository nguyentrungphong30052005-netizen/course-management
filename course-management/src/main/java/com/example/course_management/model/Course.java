package com.example.course_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "courses", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "course_category_id"})
})
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên khóa học không được để trống")
    private String name;

    @NotNull(message = "Học phí không được để trống")
    @Min(value = 1, message = "TuitionFee phải lớn hơn 0")
    private Double tuitionFee;

    @NotNull(message = "Thời lượng không được để trống")
    @Min(value = 1, message = "Duration phải lớn hơn 0")
    private Integer duration;

    private Boolean isActive = true;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_category_id", nullable = false)
    @NotNull(message = "CourseCategoryId phải tồn tại")
    private CourseCategory category;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseImage> images;

    public String getDurationType() {
        return (this.duration != null && this.duration <= 30) ? "Ngắn hạn" : "Dài hạn";
    }
}