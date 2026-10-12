package com.example.course_management.repository;

import com.example.course_management.model.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CourseCategory, Long> {
}