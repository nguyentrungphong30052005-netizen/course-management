package com.example.course_management.repository;

import com.example.course_management.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    boolean existsByNameAndCategoryId(String name, Long categoryId);
    boolean existsByNameAndCategoryIdAndIdNot(String name, Long categoryId, Long id);
}