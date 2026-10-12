package com.example.course_management.repository;

import com.example.course_management.model.CourseImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseImageRepository extends JpaRepository<CourseImage, Long> {
}