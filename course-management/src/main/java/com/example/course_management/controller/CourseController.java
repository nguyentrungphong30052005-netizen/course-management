package com.example.course_management.controller;

import com.example.course_management.model.Course;
import com.example.course_management.repository.CategoryRepository;
import com.example.course_management.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String listCourses(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Double minFee,
            @RequestParam(required = false) Double maxFee,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            Model model) {

        if (minFee != null && maxFee != null && minFee > maxFee) {
            model.addAttribute("errorMessage", "Học phí tối thiểu không thể lớn hơn học phí tối đa!");
            return "courses/list";
        }

        var courses = courseService.searchAndFilter(keyword, categoryId, isActive, minFee, maxFee, sortField, sortDir);

        model.addAttribute("courses", courses);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("isActive", isActive);
        model.addAttribute("minFee", minFee);
        model.addAttribute("maxFee", maxFee);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        return "courses/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        if (course.getImages() == null || course.getImages().isEmpty()) {
            model.addAttribute("warningMessage", "Khóa học hiện chưa có ảnh nào.");
        }
        model.addAttribute("course", course);
        return "courses/detail";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("categories", categoryRepository.findAll());
        return "courses/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("course") Course course, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "courses/create";
        }
        try {
            courseService.saveCourse(course);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm mới khóa học thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("categories", categoryRepository.findAll());
            return "courses/create";
        }
        return "redirect:/courses";
    }

    @PostMapping("/{id}/images/add")
    public String addImage(@PathVariable Long id, @RequestParam String imageUrl, @RequestParam(defaultValue = "false") boolean isThumbnail, RedirectAttributes redirectAttributes) {
        try {
            courseService.addImageToCourse(id, imageUrl, isThumbnail);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm ảnh thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/courses/" + id;
    }

    @PostMapping("/images/{imageId}/set-thumbnail")
    public String setThumbnail(@PathVariable Long imageId, @RequestParam Long courseId, RedirectAttributes redirectAttributes) {
        try {
            courseService.setThumbnail(imageId);
            redirectAttributes.addFlashAttribute("successMessage", "Đổi ảnh đại diện thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/courses/" + courseId;
    }
}