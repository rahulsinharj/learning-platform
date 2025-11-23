package com.antigravity.learningplatform.repository;

import com.antigravity.learningplatform.entity.Course;
import com.antigravity.learningplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByInstructor(User instructor);
}
