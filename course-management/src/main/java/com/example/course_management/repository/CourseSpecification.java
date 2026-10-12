package com.example.course_management.repository;

import com.example.course_management.model.Course;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class CourseSpecification {
    public static Specification<Course> filter(String keyword, Long categoryId, Boolean isActive, Double minFee, Double maxFee) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"));
            }
            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }
            if (isActive != null) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"), isActive));
            }
            if (minFee != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("tuitionFee"), minFee));
            }
            if (maxFee != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("tuitionFee"), maxFee));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}