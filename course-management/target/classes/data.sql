INSERT INTO course_categories (name, description) VALUES ('Lập trình Web', 'Các khóa học về phát triển Web Frontend và Backend');
INSERT INTO course_categories (name, description) VALUES ('Khoa học dữ liệu', 'Học máy, AI và phân tích dữ liệu');
INSERT INTO course_categories (name, description) VALUES ('Lập trình Di động', 'Phát triển ứng dụng Android và iOS');

INSERT INTO courses (name, tuition_fee, duration, is_active, description, course_category_id) 
VALUES ('ASP.NET Core MVC', 1500000, 25, true, 'Khóa học ASP.NET Core từ cơ bản đến nâng cao', 1);

INSERT INTO courses (name, tuition_fee, duration, is_active, description, course_category_id) 
VALUES ('ReactJS Cơ Bản', 1200000, 35, true, 'Xây dựng giao diện hiện đại với ReactJS', 1);

INSERT INTO courses (name, tuition_fee, duration, is_active, description, course_category_id) 
VALUES ('Machine Learning căn bản', 2500000, 45, true, 'Nhập môn học máy với Python', 2);

INSERT INTO courses (name, tuition_fee, duration, is_active, description, course_category_id) 
VALUES ('Lập trình Flutter', 1800000, 28, false, 'Phát triển ứng dụng đa nền tảng', 3);

INSERT INTO courses (name, tuition_fee, duration, is_active, description, course_category_id) 
VALUES ('Java Spring Boot', 2000000, 40, true, 'Xây dựng RESTful API chuyên nghiệp', 1);