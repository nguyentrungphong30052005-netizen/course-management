package com.example.course_management.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public String handleCustomExceptions(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/custom-error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Đã xảy ra lỗi hệ thống: " + ex.getMessage());
        return "error/custom-error";
    }
}