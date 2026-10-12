package com.example.course_management.service;

import com.example.course_management.model.Course;
import com.example.course_management.model.CourseCategory;
import com.example.course_management.model.CourseImage;
import com.example.course_management.repository.CategoryRepository;
import com.example.course_management.repository.CourseImageRepository;
import com.example.course_management.repository.CourseRepository;
import com.example.course_management.repository.CourseSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseImageRepository imageRepository;

    public List<Course> searchAndFilter(String keyword, Long categoryId, Boolean isActive, Double minFee, Double maxFee, String sortField, String sortDir) {
        Specification<Course> spec = CourseSpecification.filter(keyword, categoryId, isActive, minFee, maxFee);
        
        Sort sort = Sort.unsorted();
        if (sortField != null && !sortField.isEmpty()) {
            sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        }
        return courseRepository.findAll(spec, sort);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CourseId không tồn tại: " + id));
    }

    @Transactional
    public void saveCourse(Course course) {
        boolean isDuplicate;
        if (course.getId() == null) {
            isDuplicate = courseRepository.existsByNameAndCategoryId(course.getName(), course.getCategory().getId());
        } else {
            isDuplicate = courseRepository.existsByNameAndCategoryIdAndIdNot(course.getName(), course.getCategory().getId(), course.getId());
        }
        if (isDuplicate) {
            throw new IllegalArgumentException("Tên khóa học không được trùng trong cùng một loại khóa học.");
        }
        courseRepository.save(course);
    }

    @Transactional
    public void addImageToCourse(Long courseId, String imageUrl, boolean isThumbnail) {
        Course course = getCourseById(courseId);
        
        if (isThumbnail) {
            for (CourseImage img : course.getImages()) {
                img.setIsThumbnail(false);
                imageRepository.save(img);
            }
        } else if (course.getImages().isEmpty()) {
            isThumbnail = true;
        }

        CourseImage image = new CourseImage();
        image.setImageUrl(imageUrl);
        image.setIsThumbnail(isThumbnail);
        image.setCourse(course);
        imageRepository.save(image);
    }

    @Transactional
    public void setThumbnail(Long imageId) {
        CourseImage image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("CourseImageId không tồn tại"));
        
        Course course = image.getCourse();
        for (CourseImage img : course.getImages()) {
            img.setIsThumbnail(false);
            imageRepository.save(img);
        }
        
        image.setIsThumbnail(true);
        imageRepository.save(image);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        CourseCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("CourseCategoryId không tồn tại"));
        
        if (!category.getCourses().isEmpty()) {
            throw new IllegalStateException("Không được phép xóa loại khóa học này vì vẫn còn khóa học thuộc loại đó!");
        }
        categoryRepository.delete(category);
    }
}